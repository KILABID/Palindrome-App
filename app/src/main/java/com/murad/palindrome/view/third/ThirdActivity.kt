package com.murad.palindrome.view.third

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.murad.palindrome.adapter.UserAdapter
import com.murad.palindrome.data.paging.LoadingStateAdapter
import com.murad.palindrome.databinding.ActivityThirdBinding
import com.murad.palindrome.view.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private val viewModel: ThirdViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.setNavigationOnClickListener {
            finish()
        }

        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.user.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }


    private fun setupRecyclerView() {
        adapter = UserAdapter { dataItem ->
            val intent = Intent().apply {
                putExtra("SELECTED_USER_EMAIL", dataItem.email)
                putExtra("SELECTED_USER_FIRST_NAME", dataItem.firstName)
                putExtra("SELECTED_USER_LAST_NAME", dataItem.lastName)
                putExtra("SELECTED_USER_AVATAR", dataItem.avatar)
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        val footerAdapter = LoadingStateAdapter { adapter.retry() }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter.withLoadStateFooter(footerAdapter)

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }


}