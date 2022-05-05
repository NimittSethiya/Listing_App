package com.example.listingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.listingapp.*
import com.example.listingapp.`interface`.NetworkService
import com.example.listingapp.adapters.UsersViewAdapter
import com.example.listingapp.databinding.UsersListBinding
import com.example.listingapp.model.UsersRepository
import com.example.listingapp.viewModel.MainViewModelFactory
import com.example.listingapp.viewModel.UsersViewModel


class UsersList : Fragment(){

    lateinit var viewModel: UsersViewModel
    private val adapter = UsersViewAdapter()
    lateinit var binding: UsersListBinding
    var page:Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = UsersListBinding.inflate(inflater, container,false)

        val networkService = NetworkService.getInstance()
        val usersRepository = UsersRepository(networkService)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        binding.usersList.layoutManager = staggeredGridLayoutManager

        binding.usersList.adapter = adapter

        viewModel = ViewModelProvider(requireActivity(),MainViewModelFactory(usersRepository)).get(UsersViewModel ::class.java)
        viewModel.usersList.observe(this.viewLifecycleOwner, {
            adapter.setUsers(it)
        })

        viewModel.errorMessage.observe(this.viewLifecycleOwner) {
            Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show()
        }

        binding.usersList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount: Int = recyclerView.getLayoutManager()!!.getChildCount()
                    val totalItemCount: Int = recyclerView.getLayoutManager()!!.getItemCount()
                    val pastVisiblesItems =
                        (recyclerView.getLayoutManager() as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
                    if ((visibleItemCount + pastVisiblesItems[0] )>= totalItemCount ) {
                        page++;
                        // mocking network delay for API call
                        viewModel.getRandomUsers(page = page)
                    }
                }
            }
        })

        return binding.root
    }
}