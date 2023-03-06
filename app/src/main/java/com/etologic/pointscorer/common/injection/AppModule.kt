package com.etologic.pointscorer.common.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(pointsScorerApplication: PointsScorerApplication): Context =
        pointsScorerApplication

}
