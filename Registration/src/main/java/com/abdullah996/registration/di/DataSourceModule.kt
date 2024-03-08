package com.abdullah996.registration.di

import com.abdullah996.registration.data.registration.datasource.RegistrationDataSource
import com.abdullah996.registration.data.registration.datasource.RegistrationLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun provideLoginDataSource(impl: RegistrationLocalDataSource): RegistrationDataSource
}
