package com.etologic.pointscorer.app.main.activity

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    
    @Provides
    internal fun provideMainActivityViewModelFactory()
        : MainActivityViewModelFactory =
        MainActivityViewModelFactory()
}
