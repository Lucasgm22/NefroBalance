package br.edu.ufabc.nefrobalance.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import br.edu.ufabc.nefrobalance.model.entity.ConsumedCounter
import br.edu.ufabc.nefrobalance.model.entity.Food
import br.edu.ufabc.nefrobalance.model.repository.Repository
import br.edu.ufabc.nefrobalance.model.repository.room.entity.FoodRoom

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)

    val consumedCounter by lazy {
        MutableLiveData(ConsumedCounter(0.0,0.0,0.0,0.0))
    }

    val incrementCounters by lazy {
        MutableLiveData<ConsumedCounter?>()
    }

    sealed class Status {
        /**
         * The error class.
         * @property e the exception
         */
        class Failure(val e: Exception): Status()

        /**
         * The success class.
         */
        class Success(val result: Result) : Status()

        object Loading : Status()
    }

    sealed class Result {
        /**
         * Result type that holds a list of foods.
         * @property value the list of foods
         */
        data class FoodList (
            val value: List<Food>
        ): Result()

        data class Counter (
            val value: ConsumedCounter
        ): Result()

        data class FoodResult(
            val value: FoodRoom
        ): Result()
        object EmptyResult : Result()
    }

    fun getByQuery(query: String) = liveData {
        try {
            emit(Status.Loading)
            if (query.isEmpty()) {
                emit(Status.Success(Result.FoodList(repository.getAllFoods())))
            } else {
                emit(Status.Success(Result.FoodList(repository.getAllFoodsSortedByNameFilterQuery(query))))
            }
        } catch (e: Exception) {
            emit(Status.Failure(Exception("Failed to fetch food list", e)))
        }
    }

    fun getFoodById(id: Long) = liveData {
        try {
            emit(Status.Success(Result.FoodResult(repository.getFoodById(id))))
        } catch (e: Exception) {
            emit(Status.Failure(Exception("Failed get food information", e)))
        }
    }

    fun getLatestConsumedCounter() = liveData {
        try {
            emit(Status.Success(Result.Counter(repository.getLatestConsumedCounter())))
        } catch (e: Exception) {
            emit(Status.Failure(Exception("Failed to counter from database", e)))
        }
    }

    fun resetCountersInRepository() = liveData {
        try {
            val counter = ConsumedCounter(
                0.0, 0.0, 0.0, 0.0
            )
            repository.updateConsumedCounter(counter = counter)
            emit(Status.Success(Result.Counter(counter)))
        } catch (e: Exception) {
            emit(Status.Failure(Exception("Failed to add consumption", e)))
        }
    }

    fun incrementCountersInRepository() = liveData {
        try {
            incrementCounters.value?.let { increments ->
                consumedCounter.value?.let { repository.updateConsumedCounter(counter = increments.incrementCounters(it)) }
                emit(Status.Success(Result.EmptyResult))
            } ?: throw Exception("Increments were not set")
        } catch (e: Exception) {
            emit(Status.Failure(Exception("Failed to add consumption", e)))
        }
    }
}