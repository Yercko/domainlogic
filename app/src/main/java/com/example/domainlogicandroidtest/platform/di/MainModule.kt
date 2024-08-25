package com.example.domainlogicandroidtest.platform.di

import com.example.domainlogicandroidtest.data.api.GetUsersApiImpl
import com.example.domainlogicandroidtest.domain.repository.UserRepository
import com.example.domainlogicandroidtest.domain.usecase.AsyncSearchUsers
import com.example.domainlogicandroidtest.platform.interactor.GetUsersInteractor
import com.example.domainlogicandroidtest.platform.interactor.MainThread
import com.example.domainlogicandroidtest.platform.interactor.impl.MainThreadImpl
import com.example.domainlogicandroidtest.platform.interactor.impl.ThreadExecutor
import com.example.domainlogicandroidtest.ui.presenter.UserPresentationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideGetUsersApiImpl(): AsyncSearchUsers {
        return GetUsersApiImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiDataSource: AsyncSearchUsers): UserRepository {
        return UserRepository(apiDataSource)
    }

    @Provides
    @Singleton
    fun provideExecutor(): ThreadExecutor {
        return ThreadExecutor()
    }

    @Provides
    @Singleton
    fun provideUserPresentationMapper(): UserPresentationMapper {
        return UserPresentationMapper()
    }

    @Provides
    @Singleton
    fun provideMainThread(): MainThread {
        return MainThreadImpl()
    }

    @Provides
    @Singleton
    fun provideGetUsersInteractor(
        userRepository: UserRepository,
        executor: ThreadExecutor,
        mainThread: MainThread
    ): GetUsersInteractor {
        return GetUsersInteractor(userRepository, executor, mainThread)
    }
}
