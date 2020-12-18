package com.yilmazvolkan.languagetandem.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<DataEntity>)

    @Query("SELECT * from tandem_data")
    fun queryData(): Single<List<DataEntity>>
}