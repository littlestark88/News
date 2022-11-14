package com.example.mynews.presentasion.source

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynews.databinding.FragmentSourceBinding
import com.example.mynews.domain.model.response.source.SourceList
import com.example.mynews.presentasion.loadingstate.LoadingStateAdapter
import com.example.mynews.presentasion.viewmodel.NewsViewModel
import com.example.mynews.utils.Const
import org.koin.android.ext.android.inject

class SourceFragment : Fragment() {

    private var _binding: FragmentSourceBinding? = null
    private val binding get() = _binding
    private val newsViewModel: NewsViewModel by inject()
    private val sourceAdapter: SourceAdapter by lazy {
        SourceAdapter(
            onClickListener = { data ->
                intentDetailSource(data)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSourceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNewsRecycleList()
        newsViewModel.getSource("business").observe(viewLifecycleOwner) {
            sourceAdapter.submitData(lifecycle, it)
        }
    }

    private fun showNewsRecycleList() {
        binding?.rvNewsSource.apply {
            this?.layoutManager = LinearLayoutManager(requireActivity())
            this?.setHasFixedSize(true)
            this?.adapter = sourceAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    sourceAdapter.refresh()
                }
            )
        }
    }

    private fun intentDetailSource(data: SourceList) {
        val intent = Intent(requireActivity(), SourceDetailActivity::class.java)
        intent.putExtra(Const.DATA_SOURCE, data)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}