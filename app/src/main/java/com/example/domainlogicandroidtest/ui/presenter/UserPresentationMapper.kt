package com.example.domainlogicandroidtest.ui.presenter

import com.example.domainlogicandroidtest.domain.model.User
import com.example.domainlogicandroidtest.ui.model.UserView


class UserPresentationMapper {
    fun mapToUserView(user: User): UserView {
        return UserView(
            login = user.login,
            id = user.id,
            avatarUrl = user.avatarUrl,
            htmlUrl = user.htmlUrl
        )
    }

    fun mapToViewModelList(userViews: List<User>): List<UserView> {
        return userViews.map { mapToUserView(it) }
    }

}