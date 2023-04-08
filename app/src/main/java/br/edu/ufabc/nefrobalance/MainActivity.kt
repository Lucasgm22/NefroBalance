package br.edu.ufabc.nefrobalance

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.edu.ufabc.nefrobalance.databinding.ActivityMainBinding
import br.edu.ufabc.nefrobalance.view.fragment.listfood.FragmentListFoodDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = getNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)

        menu?.findItem(R.id.action_search).let { item ->
            (item?.actionView as SearchView).apply {
                queryHint = context.getString(R.string.action_search_query_hint)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(tagString: String?): Boolean {
                        tagString.let {
                            Log.d(javaClass.simpleName, "Search for: $tagString")
                            getNavController().navigate(FragmentListFoodDirections.showList(query = tagString?: ""))
                        }
                        onActionViewCollapsed()
                        return true
                    }

                    override fun onQueryTextChange(p0: String?) = false
                })
            }
        }

        menu?.findItem(R.id.action_list)?.setOnMenuItemClickListener { _ ->
            Log.d(javaClass.simpleName, "USER CLICKED TO LIST ALL")
            getNavController().navigate(FragmentListFoodDirections.showList())
            true
        }
        return true
    }

    private fun getNavHostFragment() = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment

    private fun getNavController() = getNavHostFragment().navController

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}