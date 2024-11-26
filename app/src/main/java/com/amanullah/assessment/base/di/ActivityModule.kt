package com.amanullah.assessment.base.di

import android.app.Application
import android.content.Context
import com.amanullah.assessment.base.failure.manager.FailureManager
import com.amanullah.assessment.base.failure.manager.FailureManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideFailureManager(@ApplicationContext context: Context): FailureManager =
        FailureManagerImpl(context)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}