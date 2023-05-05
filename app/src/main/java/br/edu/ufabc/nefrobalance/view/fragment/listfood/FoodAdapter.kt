package br.edu.ufabc.nefrobalance.view.fragment.listfood

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufabc.nefrobalance.databinding.FragmentListFoodItemBinding
import br.edu.ufabc.nefrobalance.model.entity.Food
import br.edu.ufabc.nefrobalance.viewmodel.MainViewModel

class FoodAdapter(private val foods: List<Food>, private val fragment: FragmentListFood) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemBinding: FragmentListFoodItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val title = itemBinding.textListItemTitle

        init {
            itemBinding.root.setOnClickListener {
                val food = foods[bindingAdapterPosition]
                fragment.findNavController().navigate(FragmentListFoodDirections.commitFood(foodId = food.id, foodName = food.name, isLiquid = food.isLiquid))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
        FoodViewHolder(FragmentListFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int  {
        return foods.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]

        holder.title.text = food.name
    }

    override fun getItemId(position: Int): Long = foods[position].id

}
