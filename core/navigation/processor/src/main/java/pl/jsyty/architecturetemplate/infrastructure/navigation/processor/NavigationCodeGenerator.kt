package pl.jsyty.architecturetemplate.infrastructure.navigation.processor

import com.google.auto.service.AutoService
import com.squareup.anvil.annotations.ContributesMultibinding
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.api.*
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.anvil.compiler.internal.reference.*
import com.squareup.kotlinpoet.*
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

@OptIn(ExperimentalAnvilApi::class)
@AutoService(CodeGenerator::class)
@Suppress("Unused")
class NavigationCodeGenerator : CodeGenerator {
    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>
    ): Collection<GeneratedFile> {
        return projectFiles
            .classAndInnerClassReferences(module)
            .map(::isDirectableFragment)
            .filterNotNull()
            .map {
                val fileName = it.fragmentReference.shortName + "NavigationInjector"
                val packageName = it.fragmentReference.packageFqName.asString()
                createGeneratedFile(
                    codeGenDir,
                    packageName = packageName,
                    fileName = fileName,
                    content = generateCode(
                        fileName = fileName,
                        packageName = packageName,
                        directableFragment = it,
                    )
                )
            }
            .toList()
    }

    override fun isApplicable(context: AnvilContext) = true

    private fun isDirectableFragment(reference: ClassReference): DirectableFragment? {
        // search for classes with @BindDirection annotation
        if (!reference.isAnnotatedWith(bindDirectionFqName)) return null

        // check for the argument and type of annotation
        val annotation = reference.annotations.first { it.fqName == bindDirectionFqName }
        val argument = annotation.arguments.first { it.resolvedName == "to" }
        val directionType = argument.value<ClassReference>()
        if (!directionType.directSuperTypeReferences()
                .any { it.asClassReference().fqName == directionFqName }
        ) {
            throw AnvilCompilationException(message = "@BindDirection has invalid type (not inheriting the Direction interface)")
        }

        // super class that marks directable fragment
        if (!reference.allSuperTypeClassReferences()
                .any {
                    it.fqName == baseDirectionComposeFragmentFqName || it.fqName == baseDirectionComposeDialogFragmentFqName
                }
        ) {
            throw AnvilCompilationException(message = "@BindDirection can only be used on BaseDirectionComposeFragment or BaseDirectionComposeDialogFragment inheritants")
        }
        return DirectableFragment(reference, directionType)
    }

    private fun generateCode(
        fileName: String,
        packageName: String,
        directableFragment: DirectableFragment
    ) = FileSpec.buildFile(packageName = packageName, fileName = fileName) {
        addType(
            TypeSpec.classBuilder(directableFragment.fragmentReference.shortName + "_DirectionFactory")
                .addModifiers(KModifier.PUBLIC)
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addAnnotation(injectAnnotationClassName)
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec
                        .builder(ContributesMultibinding::class)
                        .addMember("%T::class", appScopeClassName)
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec
                        .builder(directionKeyClassName)
                        .addMember(
                            "%S",
                            directableFragment.directionReference.asClassName().canonicalName
                        )
                        .build()
                )
                .addSuperinterface(fragmentFactoryClassName)
                .addFunction(
                    FunSpec.builder("create")
                        .addModifiers(KModifier.OVERRIDE)
                        .returns(androidFragmentClassName)
                        .addStatement(
                            "return %T()",
                            directableFragment.fragmentReference.asClassName()
                        )
                        .build()
                )
                .build()
        )
    }

    private data class DirectableFragment(
        val fragmentReference: ClassReference,
        val directionReference: ClassReference
    )
}
