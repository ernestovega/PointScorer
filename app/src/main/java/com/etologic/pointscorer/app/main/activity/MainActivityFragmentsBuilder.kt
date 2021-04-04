package com.etologic.pointscorer.app.main.activity

import com.etologic.pointscorer.app.main.fragments.*
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentsBuilder {
    
    @ContributesAndroidInjector
    internal abstract fun provideMainFragmentFragmentFactory(): MenuFragment
    
    @ContributesAndroidInjector
    internal abstract fun providePlayerFragmentFragmentFactory(): PlayerFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideBOnePlayerFragmentFragmentFactory(): Game1PlayerFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideCTwoPlayersFragmentFragmentFactory(): Game2PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideDThreePlayersFragmentFragmentFactory(): Game3PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideEFourPlayersFragmentFragmentFactory(): Game4PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideFFivePlayersFragmentFragmentFactory(): Game5PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideGSixPlayersFragmentFragmentFactory(): Game6PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideHSevenPlayersFragmentFragmentFactory(): Game7PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideIEightPlayersFragmentFragmentFactory(): Game8PlayersFragment
    
}
