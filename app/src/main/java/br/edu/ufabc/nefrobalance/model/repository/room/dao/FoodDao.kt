package br.edu.ufabc.nefrobalance.model.repository.room.dao

import androidx.room.Dao
import androidx.room.Query
import br.edu.ufabc.nefrobalance.model.repository.room.entity.FoodRoom

@Dao
interface FoodDao {

    @Query("SELECT * FROM food ORDER BY name")
    fun getAll(): List<FoodRoom>
    @Query("SELECT * FROM food WHERE id = :id")
    fun getById(id: Long): FoodRoom

    @Query("SELECT * FROM food WHERE name LIKE :query ORDER BY name")
    fun getAllSortedByNameFilterQuery(query: String): List<FoodRoom>
}