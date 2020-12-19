package com.yilmazvolkan.languagetandem.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {

    @Query("SELECT * FROM tandem_data")
    fun getAllTandems(): LiveData<List<DataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dataEntityList: List<DataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DataEntity)
}