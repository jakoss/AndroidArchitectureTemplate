package pl.jsyty.architecturetemplate.infrastructure.navigation.processor

import com.google.auto.service.AutoService
import com.squareup.anvil.annotations.ContributesMultibinding
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.api.*
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.anvil.compiler.internal.reference.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import java.io.File

/**
 * Searches for any class that extends BaseDirectableComposeFragment or BaseDirectableComposeDialogFragment and generated binding code to this fragment
 */
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
            // let's take only classes
            .classAndInnerClassReferences(module)
            // let's search only for directable fragments and filter out everything else
            .mapNotNull(::isDirectableFragment)
            // let's generate DirectionFactory file for all directable fragments
            .map {
                val fileName = it.fragmentReference.shortName + "_DirectionBinding"
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

    /**
     * Checks given [reference] for proper directable base class and extracts the direction type from generic parameter
     *
     * @param reference Class we are analyzing
     * @return [DirectableFragment] if the class is properly setup for binding, null otherwise
     *
     * @throws AnvilCompilationException if the processor cannot extract Direction type from the [reference]
     */
    private fun isDirectableFragment(reference: ClassReference): DirectableFragment? {
        // search for all classes that have BaseDirectableComposeFragment as ancestor
        val ancestor = reference.directSuperTypeReferences()
            .firstOrNull {
                it.asClassReference().fqName == FqNames.baseDirectableComposeFragment || it.asClassReference().fqName == FqNames.baseDirectableComposeDialogFragment
            } ?: return null

        // check for the direction type
        val directionType =
            ancestor.unwrappedTypes.single().resolveGenericTypeNameOrNull(reference)?.toString()
                ?: throw AnvilCompilationException(message = "No Direction type can be inferred by generic type")

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

    /**
     * Generate code for fragment factory that will bind Direction to a Fragment
     *
     * @param fileName Name of a file and class
     * @param packageName Package of generated file
     * @param directableFragment Info about Fragment and Direction we want to bind
     */
    private fun generateCode(
        fileName: String,
        packageName: String,
        directableFragment: DirectableFragment
    ) = FileSpec.buildFile(packageName = packageName, fileName = fileName) {
        addType(
            // @ContributesMultibinding(ApplicationScope::class)
            // @DirectionKey("package.name.of.direction.DirectionName")
            // public FragmentName_DirectionBinding : DirectionBinding
            TypeSpec.classBuilder(fileName)
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
                .addSuperinterface(ClassNames.directionBinding)

                // fun bind(): String {
                //    return "com.test.path.to.FragmentName"
                //}
                .addFunction(
                    FunSpec.builder("bind")
                        .addModifiers(KModifier.OVERRIDE)
                        .returns(String::class)
                        .addStatement(
                            "return %S",
                            directableFragment.fragmentReference.asClassName().canonicalName
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
