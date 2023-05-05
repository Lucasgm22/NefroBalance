package br.edu.ufabc.nefrobalance.model.repository

import android.app.Application
import androidx.room.Room
import androidx.room.withTransaction
import br.edu.ufabc.nefrobalance.model.entity.ConsumedCounter
import br.edu.ufabc.nefrobalance.model.entity.Food
import br.edu.ufabc.nefrobalance.model.repository.room.AppDatabase
import br.edu.ufabc.nefrobalance.model.repository.room.entity.ConsumedCountersRoom
import br.edu.ufabc.nefrobalance.model.repository.room.entity.FoodRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(application: Application) {


    private val db: AppDatabase by lazy {
        Room.databaseBuilder(application, AppDatabase::class.java, "foods")
            .createFromAsset("database/foods.db")
            .build()
    }

    suspend fun getFoodById(id: Long): FoodRoom = withContext(Dispatchers.IO) {
        db.foodDao().getById(id)
    }

    suspend fun getAllFoodsSortedByNameFilterQuery(query: String): List<Food> = withContext(Dispatchers.IO) {
        db.foodDao().getAllSortedByNameFilterQuery("%$query%").map { it.toFood() }
    }

    suspend fun getAllFoods(): List<Food> = withContext(Dispatchers.IO) {
        db.foodDao().getAll().map { it.toFood() }
    }

    suspend fun getLatestConsumedCounter() = withContext(Dispatchers.IO) {
        db.consumedCounterDao().getLatest().toConsumedCounter()
    }

    suspend fun updateConsumedCounter(id: Long = 1L, counter: ConsumedCounter) = withContext(Dispatchers.IO) {
        db.withTransaction {
            db.consumedCounterDao().deleteById(id)
            db.consumedCounterDao().insert(ConsumedCountersRoom.fromConsumedCounters(id, counter))
        }
    }
}