package com.halcyonmobile.android.common.extensions.navigation.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UIfExpression
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.util.isMethodCall

class UnsafeNavigateCallHandler(private val javaContext: JavaContext) : UElementHandler() {

    override fun visitCallExpression(node: UCallExpression) {
        if (!node.isMethodCall()) return
        if (node.methodName != "navigate") return
        if ("androidx.navigation.NavController" != node.receiver?.getExpressionType()?.canonicalText) return
        if (node.findAllSurroundingIfExpressions().map { it.asSourceString() }.any { it.contains("currentDestination?.id") || it.contains("estination.getId()") }) return
        javaContext.report(
            issue = UnsafeNavigationIssueRegistry.UnsafeNavigateCallIssue,
            scope = node,
            location = javaContext.getLocation(node),
            message = "May cause IllegalArgument Exception: Destination is Unknown for this NavController on double tap or multiple view clicks at same time. \n Use findSafeNavController or surround with if statement",
            quickfixData = AggregatedLintFix
        )
    }

    companion object {

        private val SurroundInIfLintFix = LintFix.create()
            .name("Surround in If check")
            .replace()
            .pattern("([^.]*.navigate.*)")
            .with("if (findNavController().currentDestination?.id == _) {\n\\k<0>\n}")
            .shortenNames()
            .reformat(true)
            .build()

        private val SafeNavControllerLintFix = LintFix.create()
            .name("Use findSafeNavController")
            .replace()
            .pattern("([^.]*.navigate)")
            .with("findSafeNavController().navigate")
            .shortenNames()
            .reformat(true)
            .build()

        private val AggregatedLintFix = LintFix.create()
            .alternatives()
            .add(SafeNavControllerLintFix)
            .add(SurroundInIfLintFix)
            .build()

        private fun UElement?.findAllSurroundingIfExpressions(): Sequence<UIfExpression> =
            Sequence {
                object : Iterator<UIfExpression> {

                    private var currentElement = this@findAllSurroundingIfExpressions

                    override fun hasNext(): Boolean {
                        var currentElement = currentElement
                        while (currentElement != null && currentElement !is UMethod && currentElement !is UIfExpression) {
                            currentElement = currentElement.uastParent
                        }
                        this.currentElement = currentElement
                        return currentElement is UIfExpression
                    }

                    override fun next(): UIfExpression =
                        (currentElement as UIfExpression).also { currentElement = currentElement?.uastParent }

                }
            }
    }
}