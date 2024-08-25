package com.example.domainlogicandroidtest.ui.model


data class UserView (
    val login: String? = null,
    val id : Int,
    val avatarUrl: String? = null,
    val htmlUrl: String? = null) {
    fun hasThumbnail(): Boolean {
        return !avatarUrl.isNullOrEmpty()
    }
}
