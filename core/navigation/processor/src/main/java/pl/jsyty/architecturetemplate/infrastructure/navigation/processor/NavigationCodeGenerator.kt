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
        // search for all classes that have BaseDirectableComposeFragment as ancestor
        val ancestor = reference.directSuperTypeReferences()
            .firstOrNull {
                it.asClassReference().fqName == FqNames.baseDirectableComposeFragment || it.asClassReference().fqName == FqNames.baseDirectableComposeDialogFragment
            } ?: return null

        // check for the direction type
        val directionType =
            ancestor.unwrappedTypes.single().resolveGenericTypeNameOrNull(reference)?.toString()
                ?: error("No Direction type can be inferred by generic type")

        // super class that marks directable fragment
        if (!reference.allSuperTypeClassReferences()
                .any {
                    it.fqName == FqNames.androidFragment
                }
        ) {
            throw AnvilCompilationException(message = "@BindDirection can only be used on Fragments inheritants")
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
                        .addAnnotation(ClassNames.injectAnnotation)
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec
                        .builder(ContributesMultibinding::class)
                        .addMember("%T::class", ClassNames.appScope)
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec
                        .builder(ClassNames.directionKey)
                        .addMember(
                            "%S",
                            directableFragment.directionCanonicalName
                        )
                        .build()
                )
                .addSuperinterface(ClassNames.fragmentFactory)
                .addFunction(
                    FunSpec.builder("create")
                        .addModifiers(KModifier.OVERRIDE)
                        .returns(ClassNames.androidFragment)
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
        val directionCanonicalName: String,
    )
}
