package com.jbhu

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbhuiyan.projects.vortoyelptest.R
import com.jbhuiyan.projects.vortoyelptest.businesslogic.Business
import com.jbhuiyan.projects.vortoyelptest.util.ListItemClickListener
import com.jbhuiyan.projects.vortoyelptest.util.UpdateLocation
import com.jbhuiyan.projects.vortoyelptest.util.replaceFragment
import com.jbhuiyan.projects.vortoyelptest.view.BusinessListAdapter
import com.jbhuiyan.projects.vortoyelptest.view.BusinessMapFragment
import com.jbhuiyan.projects.vortoyelptest.view.MainActivity
import com.jbhuiyan.projects.vortoyelptest.viewmodel.YelpBusinessViewModel
import kotlinx.android.synthetic.main.fragment_business_list.*


class BusinessListFragment : Fragment(R.layout.fragment_business_list),
    ListItemClickListener {
    lateinit var viewModel: YelpBusinessViewModel
    private var countriesAdapter: BusinessListAdapter = BusinessListAdapter(this, arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        instantiateTheViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        setupSearchMenu(menu)
    }

    private fun setupSearchMenu(menu: Menu) {
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView = SearchView((context as MainActivity).supportActionBar!!.themedContext)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        item.setActionView(searchView)
        searchView.setOnQueryTextListener(onQueryTextListener())
    }

    private fun onQueryTextListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.length > 0) {
                    viewModel.searchText = query
                    viewModel.location.value = (activity as UpdateLocation).getLastUpdatedLocation()
                    viewModel.refresh()
                    observeViewModel()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
    }

    private fun instantiateTheViewModel() {
        viewModel = ViewModelProviders.of(this).get(YelpBusinessViewModel::class.java)
        featureList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
            this.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    fun observeViewModel() {
        viewModel.yelpModel.observe(this, Observer { countries ->
            countries?.businesses.let { list ->
                featureList.visibility = View.VISIBLE
                list?.let { countriesAdapter.updateCountries(it) }
            }
        })

        viewModel.yelpLoadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    featureList.visibility = View.GONE
                }
            }
        })
    }

    override fun onItemClick(business: Business) {
        (activity as MainActivity).replaceFragment(BusinessMapFragment(business))
    }
}