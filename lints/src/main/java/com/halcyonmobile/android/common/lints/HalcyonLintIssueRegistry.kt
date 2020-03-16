package com.halcyonmobile.android.common.lints

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.halcyonmobile.android.common.extensions.navigation.lint.UnsafeNavigationIssueRegistry

class HalcyonLintIssueRegistry : IssueRegistry() {

    private val delegates = listOf(UnsafeNavigationIssueRegistry())

    override val issues: List<Issue> get() = delegates.getIssues()

    override val api: Int = CURRENT_API


    companion object {
        fun List<IssueRegistry>.getApi() : Int {
            var minApi : Int? = null
            for (issueRegistry in this){
                if (minApi == null || minApi > issueRegistry.api){
                    minApi = issueRegistry.api
                }
            }
            return minApi ?: CURRENT_API
        }

        fun List<IssueRegistry>.getIssues() : List<Issue> {
            val issues = mutableListOf<Issue>()
            for (issueRegistry in this){
                issues.addAll(issueRegistry.issues)
            }
            return issues
        }
    }
}