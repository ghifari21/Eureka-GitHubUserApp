package com.gosty.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.gosty.githubuserapp.R
import com.gosty.githubuserapp.data.ui.SectionsPagerAdapter
import com.gosty.githubuserapp.databinding.ActivityDetailBinding
import com.gosty.githubuserapp.utils.ViewModelFactory
import com.gosty.githubuserapp.utils.Result
import com.kennyc.view.MultiStateView

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_DATA)

        initView(username)
    }

    private fun initView(username: String?) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel.getUserByUsername(username!!).observe(this) {
            when (it) {
                is Result.Loading -> {
                    binding.stateView.viewState = MultiStateView.ViewState.LOADING
                }

                is Result.Success -> {
                    binding.apply {
                        stateView.viewState = MultiStateView.ViewState.CONTENT
                        Glide.with(this@DetailActivity)
                            .load(it.data.avatar)
                            .placeholder(R.drawable.baseline_image_24)
                            .error(R.drawable.baseline_broken_image_24)
                            .centerCrop()
                            .into(imgAvatar)
                        tvName.text = it.data.name
                        tvUsername.text = it.data.login
                        tvFollowers.text =
                            resources.getString(R.string.followers, it.data.followers)
                        tvFollowing.text =
                            resources.getString(R.string.following, it.data.following)
                        tvCompany.text = it.data.company ?: resources.getString(R.string.data_null)
                        tvEmail.text =
                            it.data.email ?: resources.getString(R.string.data_null)
                        tvRepository.text =
                            resources.getString(R.string.pub_repos, it.data.publicRepos)
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

    companion object {
        const val EXTRA_DATA = "extra"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_followers,
            R.string.title_following
        )
    }
}