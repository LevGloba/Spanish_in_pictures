package com.example.spanish.di

import com.example.spanish.di.model.ChangeStringToolBar
import com.example.spanish.domain.ChangeStringToolBarImp
import com.example.spanish.domain.SaveResultImpl
import com.example.spanish.domain.TakeDataFromFireStoreImpl
import com.example.spanish.domain.TakeRulseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ProvideModule {

    @Singleton
    @Binds
    abstract fun provideTakeDataFromFireStore(takeDataFromFireStoreImpl: TakeDataFromFireStoreImpl): TakeDataFromFireStore

    @Singleton
    @Binds
    abstract fun provideTakeRulse(takeRulseImpl: TakeRulseImpl): TakeRulse

    @Singleton
    @Binds
    abstract fun provideChangeStringToolBar(changeStringToolBarImp: ChangeStringToolBarImp): ChangeStringToolBar

    @Singleton
    @Binds
    abstract fun provideSaveResult(saveResultImpl: SaveResultImpl): SaveResult
}