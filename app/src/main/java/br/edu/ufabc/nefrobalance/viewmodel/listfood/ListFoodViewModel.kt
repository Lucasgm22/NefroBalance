package br.edu.ufabc.nefrobalance.viewmodel.listfood

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.edu.ufabc.nefrobalance.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFoodViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val foodsFile = "foods.json"
    }

    val isDataReady = MutableLiveData(false)


    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(javaClass.simpleName, "PARSING FILE")
            application.resources.assets.open(foodsFile).use {
                getApplication<App>().foodRepository.loadData(it)
            }
            Log.d(javaClass.simpleName, "FINISHED PARSING FILE")
            isDataReady.postValue(true)
        }
    }

    fun getByQuery(query: String) = getApplication<App>().foodRepository.getAllSortedByNameFilterQuery(query)
}