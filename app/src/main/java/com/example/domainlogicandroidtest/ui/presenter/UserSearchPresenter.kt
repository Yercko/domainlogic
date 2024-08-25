package com.example.domainlogicandroidtest.ui.presenter

import com.example.domainlogicandroidtest.domain.model.User
import com.example.domainlogicandroidtest.domain.usecase.AsyncSearchUsers
import com.example.domainlogicandroidtest.platform.interactor.GetUsersInteractor
import com.example.domainlogicandroidtest.ui.model.UserView
import com.example.domainlogicandroidtest.ui.views.UserSearchView
import retrofit2.HttpException
import javax.inject.Inject
class UserSearchPresenter @Inject constructor(
    private val getUsersInteractor: GetUsersInteractor,
    private val mapper: UserPresentationMapper
) : AsyncSearchUsers.Listener {

    var view: UserSearchView? = null

    fun attachView(view: UserSearchView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun onSearchButtonClicked(username: String) {
        getUsersInteractor.execute(this, username)
    }

    fun validSizeTextInput(input: String): Boolean {
        return input.length in 4..20
    }

    fun onUserClicked(userView: UserView) {
        view?.navigateToUserDetail(userView)
    }

    override fun onUsersReceived(users: List<User>, isCached: Boolean) {
        val userViewModels = mapper.mapToViewModelList(users.take(10))
        view?.showUsers(userViewModels)
    }

    override fun onError(e: Exception) {
        val errorMessage = when (e) {
            is HttpException -> {
                when (e.code()) {
                    404 -> "Error 404: No se encontró la información."
                    500 -> "Error 500: Error interno del servidor."
                    else -> "Error HTTP ${e.code()}: ${e.message()}"
                }
            }
            else -> e.message ?: "Error desconocido"
        }
        view?.showError(errorMessage)
    }

    override fun onNoInternetAvailable() {
        view?.showError("No hay conexión a Internet.")
    }
}