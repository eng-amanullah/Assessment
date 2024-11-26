package com.amanullah.assessment.base.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object APIModule {
    /* @Provides
     fun provideRepository(apiService: APIService): Repository =
         RepositoryImpl(apiService)*/
}