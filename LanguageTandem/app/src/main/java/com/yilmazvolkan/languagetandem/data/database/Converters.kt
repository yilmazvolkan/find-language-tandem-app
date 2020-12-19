package com.yilmazvolkan.languagetandem.data.database

import androidx.room.TypeConverter

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toList(strings: String): List<String> = strings.split(",").toList()

        @TypeConverter
        @JvmStatic
        fun toString(strings: List<String>): String = strings.joinToString()
    }
}