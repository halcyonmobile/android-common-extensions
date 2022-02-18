package com.halcyonmobile.android.common.lints

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.halcyonmobile.android.common.extensions.navigation.lint.UnsafeNavigationIssueRegistry

@Suppress("UnstableApiUsage")
class HalcyonLintIssueRegistry : IssueRegistry() {

    private val delegates = listOf(UnsafeNavigationIssueRegistry())

    override val issues: List<Issue> get() = delegates.getIssues()

    override val api: Int = CURRENT_API

    override val vendor: Vendor = Vendor(
        vendorName = "Halcyon-Android",
        feedbackUrl = "https://github.com/halcyonmobile/android-common-extensions/issues",
        contact = "https://github.com/halcyonmobile/android-common-extensions",
        identifier = "Halcyon-Common-Extensions"
    )


    companion object {
        fun List<IssueRegistry>.getApi(): Int {
            var minApi: Int? = null
            for (issueRegistry in this) {
                if (minApi == null || minApi > issueRegistry.api) {
                    minApi = issueRegistry.api
                }
            }
            return minApi ?: CURRENT_API
        }

        fun List<IssueRegistry>.getIssues(): List<Issue> {
            val issues = mutableListOf<Issue>()
            for (issueRegistry in this) {
                issues.addAll(issueRegistry.issues)
            }
            return issues
        }
    }
}