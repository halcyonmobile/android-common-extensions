package com.halcyonmobile.android.common.extensions.navigation.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import java.util.EnumSet

class UnsafeNavigationIssueRegistry : IssueRegistry() {
    override val issues: List<Issue> get() = listOf(UnsafeNavigateCallIssue)

    override val api: Int = CURRENT_API

    class UnsafeNavigateCallPatternDetector : Detector(), Detector.UastScanner {

        override fun getApplicableUastTypes() = listOf(UCallExpression::class.java)

        override fun createUastHandler(context: JavaContext) = UnsafeNavigateCallHandler(context)

    }

    companion object {

        val UnsafeNavigateCallIssue = Issue.create(
            id = "UnsafeNavigationLintWarningIssue",
            briefDescription = "Unsafe navigate call on NavController",
            explanation = "NavController.navigate may crashes with Destination Unknown for this NavController when two navigate actions are called after each other. Example double tap.\n Consider using findSafeNavController.",
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.WARNING,
            implementation = Implementation(UnsafeNavigateCallPatternDetector::class.java, EnumSet.of(Scope.JAVA_FILE))
        )
    }
}