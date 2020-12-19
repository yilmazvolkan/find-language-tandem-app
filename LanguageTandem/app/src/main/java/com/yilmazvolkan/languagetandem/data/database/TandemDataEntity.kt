package com.yilmazvolkan.languagetandem.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tandem_data")
data class TandemDataEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")  //page number + json index string concat
    val id: String,
    @ColumnInfo(name = "topic")
    val topic: String,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "pictureUrl")
    val pictureUrl: String,
    @ColumnInfo(name = "natives")
    val natives: List<String>,
    @ColumnInfo(name = "learns")
    val learns: List<String>,
    @ColumnInfo(name = "referenceCnt")
    val referenceCnt: Int
) {
    fun isNew(): Boolean = (referenceCnt == 0)

    fun getLearnsString() = learns.joinToString().toUpperCase(Locale.ROOT)

    fun getNativeString() = natives.joinToString().toUpperCase(Locale.ROOT)
}

