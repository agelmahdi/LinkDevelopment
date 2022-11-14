package com.agelmahdi.linkdevelopment.presentation.fragment


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agelmahdi.linkdevelopment.R
import com.agelmahdi.linkdevelopment.databinding.FragmentHomeBinding
import com.agelmahdi.linkdevelopment.databinding.NavDrawerLayoutBinding
import com.agelmahdi.linkdevelopment.presentation.MainActivity
import com.agelmahdi.linkdevelopment.presentation.adapter.ArticlesAdapter
import com.agelmahdi.linkdevelopment.presentation.adapter.NavAdapter
import com.agelmahdi.linkdevelopment.presentation.viewModel.HomeViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var navDrawerLayoutBinding: NavDrawerLayoutBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    @Inject
    lateinit var navAdapter: NavAdapter

    private var rootView: View? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupNavDrawer()
        setNavAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = (activity as MainActivity).viewModel

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rootView = binding.root

        collectFlowData()

        return rootView as View
    }

    private fun collectFlowData() {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            viewModel.stateFlow.collectLatest { items ->
                if (!items.isError) {
                    articlesAdapter.differ.submitList(items.articles)
                }
            }
        }
    }

    private fun setupNavDrawer() {

        drawerLayout = binding.drawerLayout
        navView = binding.navView

        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        navHostFragment.findNavController().run {
            binding.appBarMain.toolbar.setupWithNavController(this, AppBarConfiguration(graph))
            binding.appBarMain.toolbar.inflateMenu(R.menu.main)
        }

        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            binding.appBarMain.toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setupWithNavController(navHostFragment.findNavController())
        navView.bringToFront()
        setNavAdapter()
    }

    private fun setNavAdapter() {
        navDrawerLayoutBinding = NavDrawerLayoutBinding.inflate(layoutInflater)
        navView.addView(navDrawerLayoutBinding.root)
        navDrawerLayoutBinding.rvNav.apply {
            adapter = navAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        navAdapter.setOnItemClickListener { navData ->
            navAdapter.update(navData)
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(requireActivity(), navData.title, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {

        articlesAdapter = ArticlesAdapter()
        binding.appBarMain.rvArticles.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        articlesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_nav_home_to_detailsFragment, bundle
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}