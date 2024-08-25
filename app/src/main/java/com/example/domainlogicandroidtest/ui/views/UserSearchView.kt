package com.example.domainlogicandroidtest.ui.views

import com.example.domainlogicandroidtest.ui.model.UserView


interface UserSearchView {
    fun showUsers(users: List<UserView>)
    fun showError(message: String)
    fun navigateToUserDetail(user: UserView)
}