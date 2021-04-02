package com.etologic.pointscorer.app.main.activity

import com.etologic.pointscorer.app.main.fragments.*
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentsBuilder {
    
    @ContributesAndroidInjector
    internal abstract fun provideMainFragmentFragmentFactory(): AMenuFragment
    
    @ContributesAndroidInjector
    internal abstract fun providePlayerFragmentFragmentFactory(): PlayerFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideBOnePlayerFragmentFragmentFactory(): BOnePlayerFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideCTwoPlayersFragmentFragmentFactory(): CTwoPlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideDThreePlayersFragmentFragmentFactory(): DThreePlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideEFourPlayersFragmentFragmentFactory(): EFourPlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideFFivePlayersFragmentFragmentFactory(): FFivePlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideGSixPlayersFragmentFragmentFactory(): GSixPlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideHSevenPlayersFragmentFragmentFactory(): HSevenPlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideIEightPlayersFragmentFragmentFactory(): IEightPlayersFragment
    
}
