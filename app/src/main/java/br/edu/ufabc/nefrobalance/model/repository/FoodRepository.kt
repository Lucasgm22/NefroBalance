package br.edu.ufabc.nefrobalance.model.repository

import android.util.Log
import br.edu.ufabc.nefrobalance.model.entity.Food
import com.beust.klaxon.Klaxon
import java.io.InputStream

class FoodRepository {

    private var foods: List<Food>? = null

    fun getAllSortedByNameFilterQuery(query: String): List<Food> = validFoods().sortedBy { it.name }.filter { it.name.uppercase().contains(query.uppercase()) }

    fun loadData(inputStream: InputStream) {
        Log.d(this.javaClass.simpleName, "Loading data from inputStream")
        foods = Klaxon().parseArray(inputStream) ?: emptyList()
    }

    private fun validFoods(): List<Food> = foods ?: throw IllegalStateException("FoodRepository has not been initialized")

}