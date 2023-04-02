package com.etologic.pointscorer.data.database

import android.net.Uri
import androidx.room.TypeConverter

class RoomTypeConverters {

    @TypeConverter
    fun fromString(value: String?): Uri? =
        value?.let { Uri.parse(it) }

    @TypeConverter
    fun toString(uri: Uri?): String? =
        uri?.toString()

}