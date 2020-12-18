package com.yilmazvolkan.languagetandem.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tandem_data")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "topic")
    val topic: String,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "pictureUrl")
    val pictureUrl: String,
    @ColumnInfo(name = "natives")
    val natives: ArrayList<String>,
    @ColumnInfo(name = "learns")
    val learns: ArrayList<String>,
    @ColumnInfo(name = "referenceCnt")
    val referenceCnt: Int,
)