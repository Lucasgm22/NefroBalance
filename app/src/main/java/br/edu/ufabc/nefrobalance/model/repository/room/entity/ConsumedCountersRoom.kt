package br.edu.ufabc.nefrobalance.model.repository.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.ufabc.nefrobalance.model.entity.ConsumedCounter

@Entity(tableName = "consumedCounters")
data class ConsumedCountersRoom(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val liquid: Double,
    val phosphor: Double,
    val sodium: Double,
    val potassium: Double
) {
    fun toConsumedCounter() = ConsumedCounter(
        liquid = liquid,
        phosphor = phosphor,
        sodium = sodium,
        potassium = potassium
    )

    companion object {
        fun fromConsumedCounters(id: Long, counters: ConsumedCounter): ConsumedCountersRoom =
            ConsumedCountersRoom(
                id = id,
                liquid = counters.liquid,
                phosphor = counters.phosphor,
                sodium = counters.sodium,
                potassium = counters.potassium
            )
    }
}
