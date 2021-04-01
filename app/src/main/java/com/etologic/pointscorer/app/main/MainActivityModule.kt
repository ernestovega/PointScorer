package com.etologic.pointscorer.app.main

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    
    @Provides
    internal fun provideMainActivityViewModelFactory()
        : MainActivityViewModelFactory =
        MainActivityViewModelFactory()
}
