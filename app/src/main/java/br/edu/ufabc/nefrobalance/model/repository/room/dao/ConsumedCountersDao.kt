package br.edu.ufabc.nefrobalance.model.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.edu.ufabc.nefrobalance.model.repository.room.entity.ConsumedCountersRoom

@Dao
interface ConsumedCountersDao {

    @Query("SELECT * FROM consumedCounters WHERE id = (SELECT MAX(id) FROM consumedCounters)")
    fun getLatest(): ConsumedCountersRoom

    @Query("DELETE FROM consumedCounters WHERE id=:id")
    fun deleteById(id: Long)

    @Insert
    fun insert(counters: ConsumedCountersRoom)
}