package com.etologic.pointscorer.app.main.activity

import com.etologic.pointscorer.app.main.fragments.*
import com.etologic.pointscorer.app.main.dialogs.finish_menu.FinishMenuDialogFragment
import com.etologic.pointscorer.app.main.fragments.main_menu.MainMenuFragment
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import com.etologic.pointscorer.app.main.dialogs.payer_settings_menu.PlayerSettingsMenuDialogFragment
import com.etologic.pointscorer.app.main.dialogs.restore_all_points_dialog.RestoreAllPointsDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentsBuilder {
    
    @ContributesAndroidInjector
    internal abstract fun provideMainFragmentFactory(): MainMenuFragment
    
    @ContributesAndroidInjector
    internal abstract fun providePlayerFragmentFactory(): PlayerFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideBOnePlayerFragmentFactory(): Game1PlayerFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideCTwoPlayersFragmentFactory(): Game2PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideDThreePlayersFragmentFactory(): Game3PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideEFourPlayersFragmentFactory(): Game4PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideFFivePlayersFragmentFactory(): Game5PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideGSixPlayersFragmentFactory(): Game6PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideHSevenPlayersFragmentFactory(): Game7PlayersFragment
    
    @ContributesAndroidInjector
    internal abstract fun provideIEightPlayersFragmentFactory(): Game8PlayersFragment

    @ContributesAndroidInjector
    internal abstract fun providePlayerSettingsDialogFragmentFactory(): PlayerSettingsMenuDialogFragment

    @ContributesAndroidInjector
    internal abstract fun provideFinishMenuDialogFragmentFactory(): FinishMenuDialogFragment

    @ContributesAndroidInjector
    internal abstract fun provideRestoreAllPointsDialogFragmentFactory(): RestoreAllPointsDialogFragment
    
}
