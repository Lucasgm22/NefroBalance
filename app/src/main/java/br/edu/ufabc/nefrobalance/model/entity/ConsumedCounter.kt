package br.edu.ufabc.nefrobalance.model.entity

data class ConsumedCounter(
    val liquid: Double,
    val phosphor: Double,
    val sodium: Double,
    val potassium: Double
) {
    fun incrementCounters(increments: ConsumedCounter) = ConsumedCounter(
        liquid = liquid.plus(increments.liquid),
        phosphor = phosphor.plus(increments.phosphor),
        sodium = sodium.plus(increments.sodium),
        potassium = potassium.plus(increments.potassium)
    )
}