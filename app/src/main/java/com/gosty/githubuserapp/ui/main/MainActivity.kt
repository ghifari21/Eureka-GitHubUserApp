package com.gosty.githubuserapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gosty.githubuserapp.R
import com.gosty.githubuserapp.data.models.UserModel
import com.gosty.githubuserapp.data.ui.UserListAdapter
import com.gosty.githubuserapp.databinding.ActivityMainBinding
import com.gosty.githubuserapp.ui.detail.DetailActivity
import com.gosty.githubuserapp.utils.Result
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        viewModel.getUsers().observe(this@MainActivity) {
            when (it) {
                is Result.Loading -> {
                    binding.stateView.viewState = MultiStateView.ViewState.LOADING
                }

                is Result.Success -> {
                    binding.apply {
                        stateView.viewState = MultiStateView.ViewState.CONTENT
                        val content = stateView.getView(MultiStateView.ViewState.CONTENT)
                        if (content != null) {
                            val layoutManager = LinearLayoutManager(this@MainActivity)
                            rvUsers.adapter = adapter
                            rvUsers.layoutManager = layoutManager
                            rvUsers.setHasFixedSize(true)
                            val itemDecoration = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
                            rvUsers.addItemDecoration(itemDecoration)
                        }
                    }
                    adapter.submitList(it.data)

                    adapter.setOnItemClickCallback(
                        object : UserListAdapter.OnItemClickCallback {
                            override fun onItemClicked(user: UserModel) {
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.EXTRA_DATA, user.login)
                                startActivity(intent)
                            }
                        }
                    )

                    binding.btnError.setOnClickListener {
                        throw RuntimeException("TEST ERROR")

                    }
                }

                is Result.Empty -> {
                    binding.stateView.viewState = MultiStateView.ViewState.EMPTY
                }

                is Result.Error -> {
                    binding.stateView.viewState = MultiStateView.ViewState.ERROR
                    val errorView = binding.stateView.getView(MultiStateView.ViewState.ERROR)
                    if (errorView != null) {
                        val btn: Button = errorView.findViewById(R.id.btn_retry)
                        btn.setOnClickListener {
                            finish()
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}