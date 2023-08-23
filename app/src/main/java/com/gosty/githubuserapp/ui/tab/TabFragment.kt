package com.gosty.githubuserapp.ui.tab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gosty.githubuserapp.R
import com.gosty.githubuserapp.data.models.UserModel
import com.gosty.githubuserapp.data.ui.UserListAdapter
import com.gosty.githubuserapp.databinding.FragmentTabBinding
import com.gosty.githubuserapp.ui.detail.DetailActivity
import com.gosty.githubuserapp.utils.Result
import com.gosty.githubuserapp.utils.ViewModelFactory
import com.kennyc.view.MultiStateView

class TabFragment : Fragment() {
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabName = arguments?.getString(ARG_TAB)
        val username = arguments?.getString(ARG_USERNAME)
        val viewModel: TabViewModel by viewModels {
            ViewModelFactory.getInstance()
        }
        val adapter = UserListAdapter()

        viewModel.getUserFollow(username!!, tabName!!).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    binding?.stateView?.viewState = MultiStateView.ViewState.LOADING
                }

                is Result.Success -> {
                    binding?.apply {
                        stateView.viewState = MultiStateView.ViewState.CONTENT
                        val content = stateView.getView(MultiStateView.ViewState.CONTENT)
                        if (content != null) {
                            val layoutManager = LinearLayoutManager(activity)
                            rvUsers.adapter = adapter
                            rvUsers.layoutManager = layoutManager
                            rvUsers.setHasFixedSize(true)
                            val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
                            rvUsers.addItemDecoration(itemDecoration)
                        }
                    }
                    adapter.submitList(it.data)

                    adapter.setOnItemClickCallback(
                        object : UserListAdapter.OnItemClickCallback {
                            override fun onItemClicked(user: UserModel) {
                                val intent = Intent(activity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.EXTRA_DATA, user.login)
                                startActivity(intent)
                            }
                        }
                    )
                }

                is Result.Empty -> {
                    binding?.stateView?.viewState = MultiStateView.ViewState.EMPTY
                }

                is Result.Error -> {
                    binding?.stateView?.viewState = MultiStateView.ViewState.ERROR
                    val errorView = binding?.stateView?.getView(MultiStateView.ViewState.ERROR)
                    if (errorView != null) {
                        val btn: Button = errorView.findViewById(R.id.btn_retry)
                        btn.setOnClickListener {
                            activity?.finish()
                            startActivity(activity?.intent!!)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val ARG_USERNAME = "username"
        const val TAB_FOLLOWERS = "followers"
        const val TAB_FOLLOWING = "following"
    }
}