package com.etologic.pointscorer.injection

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PointsScorerApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out PointsScorerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}
