package com.abdullah996.registration.di

import com.abdullah996.registration.data.registration.repo.RegistrationRepoImpl
import com.abdullah996.registration.domain.registration.repo.RegistrationRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun provideUsersRepository(impl: RegistrationRepoImpl): RegistrationRepo
}
