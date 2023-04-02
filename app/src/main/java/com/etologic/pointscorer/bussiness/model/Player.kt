package com.etologic.pointscorer.bussiness.model

import android.net.Uri
import androidx.room.Entity

@Entity(tableName = "players", primaryKeys = ["id"])
data class Player(
    val id: Int,
    val name: String,
    val color: Int,
    val background: Uri?,
    val points: Int,
)