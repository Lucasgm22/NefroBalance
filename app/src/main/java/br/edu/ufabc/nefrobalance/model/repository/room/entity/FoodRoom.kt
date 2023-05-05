package br.edu.ufabc.nefrobalance.model.repository.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.ufabc.nefrobalance.model.entity.Food

@Entity(tableName = "food")
data class FoodRoom (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val moisture: Double,
    val phosphor: Double,
    val sodium: Double,
    val potassium: Double,
    val isLiquid: Boolean,
    val density: Double?
) {
    fun toFood() = Food(
        id = id,
        name = name,
        isLiquid = isLiquid
    )
}