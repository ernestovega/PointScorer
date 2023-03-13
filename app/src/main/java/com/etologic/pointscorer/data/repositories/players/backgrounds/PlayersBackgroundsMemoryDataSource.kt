package com.etologic.pointscorer.data.repositories.players.backgrounds

import android.net.Uri
import com.etologic.pointscorer.data.repositories.base.BaseMapMemoryDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersBackgroundsMemoryDataSource @Inject constructor() : BaseMapMemoryDataSource<Int, Uri>()
