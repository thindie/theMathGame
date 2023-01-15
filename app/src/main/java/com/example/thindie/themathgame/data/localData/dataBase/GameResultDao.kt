package com.example.thindie.themathgame.data.localData.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface GameResultDao {
    @Query("SELECT * FROM results")
    fun getAll() : Flow<GameResultDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addResult(resultDbModel: GameResultDbModel)
}