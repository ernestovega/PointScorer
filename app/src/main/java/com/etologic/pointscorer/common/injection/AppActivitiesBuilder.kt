package com.etologic.pointscorer.common.injection

import com.etologic.pointscorer.app.main.activity.MainActivity
import com.etologic.pointscorer.app.main.activity.MainActivityFragmentsBuilder
import com.etologic.pointscorer.app.main.activity.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AppActivitiesBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainActivityFragmentsBuilder::class])
    internal abstract fun bindMainActivity(): MainActivity

}
