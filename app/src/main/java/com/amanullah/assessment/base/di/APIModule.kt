package com.amanullah.assessment.base.di

import com.amanullah.assessment.data.remote.api.APIService
import com.amanullah.assessment.data.remote.repositoryimpl.APIRepositoryImpl
import com.amanullah.assessment.domain.repository.APIRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object APIModule {
    @Provides
    fun provideRepository(apiService: APIService): APIRepository =
        APIRepositoryImpl(apiService)
}