package com.example.mytodolist.util.analytics

enum class AnalyticsEvent(val key: String) {
    TASK_ADDED("task_added"),
    TASK_EDITED("task_edited"),
    TASK_COMPLETED("task_completed")
}