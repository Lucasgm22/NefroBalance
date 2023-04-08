package br.edu.ufabc.nefrobalance

import android.app.Application
import br.edu.ufabc.nefrobalance.model.repository.FoodRepository

class App: Application() {
    val foodRepository = FoodRepository()
}