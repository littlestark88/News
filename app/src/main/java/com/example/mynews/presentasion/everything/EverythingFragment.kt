package com.example.mynews.presentasion.everything

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynews.databinding.FragmentEverythingBinding
import com.example.mynews.presentasion.loadingstate.LoadingStateAdapter
import com.example.mynews.presentasion.viewmodel.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class EverythingFragment : Fragment() {

    private var _binding: FragmentEverythingBinding? = null
    private val binding get() = _binding
    private val newsViewModel: NewsViewModel by inject()
    private val everythingAdapter: EverythingAdapter by lazy {
        EverythingAdapter(
            onClickListener = { data, optionCompat ->

            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEverythingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNewsRecycleList()
        newsViewModel.getEverything("bitcoin").observe(viewLifecycleOwner) {
            everythingAdapter.submitData(lifecycle, it)
        }
    }

    private fun showNewsRecycleList() {
        binding?.rvNewsEverything.apply {
            this?.layoutManager = LinearLayoutManager(requireActivity())
            this?.setHasFixedSize(true)
            this?.adapter = everythingAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    everythingAdapter.refresh()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}