package br.edu.ufabc.nefrobalance.view.fragment.listfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import br.edu.ufabc.nefrobalance.databinding.FragmentListFoodBinding
import br.edu.ufabc.nefrobalance.viewmodel.listfood.ListFoodViewModel

class FragmentListFood: Fragment() {
    private lateinit var binding: FragmentListFoodBinding
    private val listFoodViewModel by activityViewModels<ListFoodViewModel>()
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

        listFoodViewModel.isDataReady.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressIndicator.visibility = View.INVISIBLE

                binding.recyclerViewList.apply {
                        adapter = FoodAdapter(listFoodViewModel.getByQuery(args.query), this@FragmentListFood)
                }
            } else {
                binding.progressIndicator.visibility = View.VISIBLE
            }
        }
    }
}