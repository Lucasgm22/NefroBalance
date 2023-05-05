package br.edu.ufabc.nefrobalance.view.fragment.listfood

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import br.edu.ufabc.nefrobalance.R
import br.edu.ufabc.nefrobalance.databinding.FragmentListFoodBinding
import br.edu.ufabc.nefrobalance.model.entity.Food
import br.edu.ufabc.nefrobalance.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FragmentListFood: Fragment() {
    private lateinit var binding: FragmentListFoodBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val args by navArgs<FragmentListFoodArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListFoodBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        mainViewModel.getByQuery(args.query).observe(viewLifecycleOwner) { status ->
            when(status) {
                is MainViewModel.Status.Loading -> binding.progressIndicator.visibility = View.VISIBLE
                is MainViewModel.Status.Failure ->  {
                    Log.e("VIEW", "Failed to fetch foods", status.e)
                    Snackbar.make(binding.root,
                        getString(R.string.failed_to_list_foods), Snackbar.LENGTH_LONG).show()
                    binding.progressIndicator.visibility = View.INVISIBLE
                }
                is MainViewModel.Status.Success -> {
                    val foods: List<Food> = (status.result as MainViewModel.Result.FoodList).value
                    binding.recyclerViewList.adapter = FoodAdapter(foods, this@FragmentListFood)
                    binding.progressIndicator.visibility = View.INVISIBLE
                }
            }
        }
    }
}
