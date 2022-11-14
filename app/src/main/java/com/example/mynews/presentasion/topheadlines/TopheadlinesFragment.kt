package com.example.mynews.presentasion.topheadlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynews.databinding.FragmentTopheadlinesBinding
import com.example.mynews.presentasion.loadingstate.LoadingStateAdapter
import com.example.mynews.presentasion.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject

class TopheadlinesFragment : Fragment() {

    private var _binding: FragmentTopheadlinesBinding? = null
    private val binding get() = _binding
    private val newsViewModel: NewsViewModel by inject()
    private val topHeadlinesAdapter: TopHeadlinesAdapter by lazy {
        TopHeadlinesAdapter(
            onClickListener = { data, optionCompat ->

            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopheadlinesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNewsRecycleList()
        newsViewModel.getTopHeadlines("id").observe(viewLifecycleOwner) {
            topHeadlinesAdapter.submitData(lifecycle, it)
        }
    }

    private fun showNewsRecycleList() {
        binding?.rvTopHeadlines.apply {
            this?.layoutManager = LinearLayoutManager(requireActivity())
            this?.setHasFixedSize(true)
            this?.adapter = topHeadlinesAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    topHeadlinesAdapter.refresh()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}