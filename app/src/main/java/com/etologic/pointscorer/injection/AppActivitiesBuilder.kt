package com.etologic.pointscorer.injection

import com.etologic.pointscorer.app.main.MainActivity
import com.etologic.pointscorer.app.main.MainActivityFragmentsBuilder
import com.etologic.pointscorer.app.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AppActivitiesBuilder {
    
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainActivityFragmentsBuilder::class])
    internal abstract fun bindMainActivity(): MainActivity
    
}
