package com.halcyonmobile.android.common.extensions.navigation.lint

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.TextFormat
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UnsafeUnsafeNavigationIssueRegistryTest {

    @Test
    fun check_explanation_for_hamcrest_import_is_correct() {
        val output = UnsafeNavigationIssueRegistry().issues
            .joinToString(separator = "\n") { "- **${it.id}** - ${it.getExplanation(TextFormat.RAW)}" }

        assertThat(output.trimIndent()).isEqualTo("- **UnsafeNavigationLintWarningIssue** - NavController.navigate may crash with Destination Unknown for this NavController when two navigate actions are called after each other. Example double tap.\n Consider using findSafeNavController.")
    }

    @Test
    fun should_not_report_anything_if_no_navController_is_used() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                TestFiles.kotlin(
                    """
            package foo
            
            import foo.R
            class Example {
            
                fun foo() {
                    findSafeNavController().navigate(R.id.foo_to_bar)
                }
            
            }"""
                ).indented()
            )
            .issues(UnsafeNavigationIssueRegistry.UnsafeNavigateCallIssue)
            .run()
            .expectClean()
    }

    @Test
    fun should_report_warning_if_NavController_navigate_is_used_without_if_containing_current_destination() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                TestFiles.kotlin(
                    """
            package androidx.navigation
            
            class NavController {
                fun navigate(action: Int){
                    }
            }
            
            class Example {
            
                fun foo() {
                    findNavController().navigate(15)
                    if (1 == 2) {
                        findNavController().navigate(16)
                    }
                }
                
                fun findNavController() = NavController()
            
            }"""
                ).indented()
            )
            .issues(UnsafeNavigationIssueRegistry.UnsafeNavigateCallIssue)
            .run()
            .expect(
                """
          |src/androidx/navigation/NavController.kt:11: Warning: May cause IllegalArgument Exception: Destination is Unknown for this NavController on double tap or multiple view clicks at same time. 
          | Use findSafeNavController or surround with if statement [UnsafeNavigationLintWarningIssue]
          |        findNavController().navigate(15)
          |        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
          |src/androidx/navigation/NavController.kt:13: Warning: May cause IllegalArgument Exception: Destination is Unknown for this NavController on double tap or multiple view clicks at same time. 
          | Use findSafeNavController or surround with if statement [UnsafeNavigationLintWarningIssue]
          |            findNavController().navigate(16)
          |            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
          |0 errors, 2 warnings""".trimMargin()
            )
    }

    @Test
    fun should_not_report_warning_if_NavController_navigate_is_used_with_if() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                TestFiles.kotlin(
                    """
            package androidx.navigation
            
            class NavController {
                
                val currentDesination : Destination? get() = Destination()
            
                fun navigate(action: Int) {
                }
            }
            
            class Destination {
                val id: Int = 10
            }
            
            class Example {
            
                fun foo() {
                    if (findNavController().currentDestination?.id == 5) findNavController().navigate(15)
                    
                    if (findNavController().currentDestination?.id == 6) {
                        findNavController().navigate(16)
                        
                        if (1 == 2) {
                            findNavController().navigate(17)    
                        }
                    }
                }
                
                fun findNavController() = NavController()
            
            }"""
                ).indented()
            )
            .issues(UnsafeNavigationIssueRegistry.UnsafeNavigateCallIssue)
            .run()
            .expectClean()
    }
}