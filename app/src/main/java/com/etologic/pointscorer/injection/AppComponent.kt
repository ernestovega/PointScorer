package com.etologic.pointscorer.injection

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        AppActivitiesBuilder::class]
)
interface AppComponent : AndroidInjector<PointsScorerApplication> {
    
    @Suppress("DEPRECATION")//https://stackoverflow.com/questions/36521302/dagger-2-2-component-builder-module-method-deprecated
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PointsScorerApplication>()
}
