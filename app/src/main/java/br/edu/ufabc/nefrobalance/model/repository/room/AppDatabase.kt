package br.edu.ufabc.nefrobalance.model.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ufabc.nefrobalance.model.repository.room.dao.ConsumedCountersDao
import br.edu.ufabc.nefrobalance.model.repository.room.dao.FoodDao
import br.edu.ufabc.nefrobalance.model.repository.room.entity.ConsumedCountersRoom
import br.edu.ufabc.nefrobalance.model.repository.room.entity.FoodRoom

@Database(entities = [FoodRoom::class, ConsumedCountersRoom::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun consumedCounterDao(): ConsumedCountersDao

}
