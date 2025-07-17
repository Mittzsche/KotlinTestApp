package com.test.testkompetensi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.testkompetensi.adapter.UserAdapter
import com.test.testkompetensi.api.RetrofitClient
import com.test.testkompetensi.databinding.ActivityThirdScreenBinding
import kotlinx.coroutines.launch

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var userAdapter: UserAdapter
    private var currentPage = 1
    private val perPage = 10
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSwipeRefresh()
        fetchUsers(currentPage)
    }

    private fun setupToolbar() {
        binding.thirdScreenToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Back to Activity
        }
    }

    private fun setupRecyclerView() {
        // Initialize adapter with lambda for onItemClick
        userAdapter = UserAdapter { selectedUser ->
            // When an item is clicked, send the username back to SecondScreen
            val resultIntent = Intent()
            resultIntent.putExtra("SELECTED_USER_NAME", "${selectedUser.firstName} ${selectedUser.lastName}")
            setResult(RESULT_OK, resultIntent)
            finish() // Close ThirdScreen and return to SecondScreen
        }

        binding.recycleViewUsers.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreen)
            adapter = userAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && !isLastPage) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= perPage) {
                            currentPage++
                            fetchUsers(currentPage)
                        }
                    }
                }
            })
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Reset state to reload from first page
            currentPage = 1
            isLastPage = false
            isLoading = false
            userAdapter.submitList(emptyList()) // Empty the list before reloading
            fetchUsers(currentPage)
        }
    }

    private fun fetchUsers(page: Int) {
        if (isLoading) return

        isLoading = true
        binding.progressBar.visibility = View.VISIBLE // show progress bar
        binding.emptyStateText.visibility = View.GONE // hide empty state

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getUsers(page, perPage)
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.let {
                        if (it.data.isNotEmpty()) {
                            if (page == 1) {
                                userAdapter.submitList(it.data) // for first page, set list
                            } else {
                                userAdapter.addUsers(it.data) // for the next page, add it
                            }
                            isLastPage = (it.page == it.totalPages)
                            binding.emptyStateText.visibility = View.GONE
                        } else {
                            // If data is empty, check whether this is the first page or not
                            if (page == 1) {
                                binding.emptyStateText.visibility = View.VISIBLE
                            }
                            isLastPage = true // No more data
                        }
                    }
                } else {
                    Toast.makeText(this@ThirdScreen, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    if (userAdapter.itemCount == 0) { // If there is no data at all
                        binding.emptyStateText.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@ThirdScreen, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
                if (userAdapter.itemCount == 0) { // If there is no data at all
                    binding.emptyStateText.visibility = View.VISIBLE
                }
            } finally {
                isLoading = false
                binding.progressBar.visibility = View.GONE // hide progress bar
                binding.swipeRefreshLayout.isRefreshing = false // stop refresh animation
            }
        }
    }
}