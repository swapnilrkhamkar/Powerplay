package com.assignment.powerplay.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.powerplay.R
import com.assignment.powerplay.adapter.BeerAdapter
import com.assignment.powerplay.model.Beer
import com.assignment.powerplay.network.Error
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_description.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: BeerViewModel
    private lateinit var beerAdapter: BeerAdapter
    private lateinit var beerArrayList: ArrayList<Beer>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val PAGE_START = 1

    private var isLoading = false
    private var pageIndex = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BeerViewModel::class.java)
        // TODO: Use the ViewModel

        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvBeers.layoutManager = linearLayoutManager

        viewModel.getBeers(pageIndex).observe(this, Observer { beers ->

            Log.e("JBHBHBH", "KNBHH " + beers)
            if (beers != null) {
                beerArrayList = beers.data!!;

                if (beerArrayList != null) {
                    progressBar.visibility = View.GONE
                    progressPagination.visibility = View.GONE
                    isLoading = false
                    beerAdapter = BeerAdapter(
                        beerArrayList
                    )
                    rvBeers.adapter = beerAdapter
                    beerAdapter.notifyDataSetChanged()

                    beerAdapter.onItemClick = { beer ->

                        val bottomSheetDialog = BottomSheetDialog(requireContext())
                        bottomSheetDialog.setContentView(R.layout.bottom_sheet_description)
                        bottomSheetDialog.show()

                        bottomSheetDialog.txtDesc.text = beer.description

                        bottomSheetDialog.btnClose.setOnClickListener {
                            bottomSheetDialog.dismiss()
                        }

                        false
                    }
                } else {
                    progressBar.visibility = View.GONE
                    isLoading = false
                    progressPagination.visibility = View.GONE
                    if (beers.message != null)
                        Error(requireContext(), beers.message).showError()
                }
            }
        })

        rvBeers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount: Int = linearLayoutManager.getChildCount()
                val totalItemCount: Int = linearLayoutManager.getItemCount()
                val firstVisibleItemPosition: Int =
                    linearLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        isLoading = true

                        pageIndex++
                        loadMoreItems(pageIndex)
                    }
                }
            }
        })

    }

    private fun loadMoreItems(pageIndex: Int) {
        progressPagination.visibility = View.VISIBLE
        viewModel.getBeers(pageIndex).observe(this, Observer { beers ->

            Log.e("JBHBHBH", "KNBHH " + beers)
            if (beers != null) {
                beerArrayList = beers.data!!;

                if (beerArrayList != null) {
                    progressBar.visibility = View.GONE
                    progressPagination.visibility = View.GONE
                    isLoading = false
                    beerAdapter = BeerAdapter(
                        beerArrayList
                    )
                    rvBeers.adapter = beerAdapter
                    beerAdapter.notifyDataSetChanged()

                    beerAdapter.onItemClick = { beer ->

                        val bottomSheetDialog = BottomSheetDialog(requireContext())
                        bottomSheetDialog.setContentView(R.layout.bottom_sheet_description)
                        bottomSheetDialog.show()

                        bottomSheetDialog.txtDesc.text = beer.description

                        bottomSheetDialog.btnClose.setOnClickListener {
                            bottomSheetDialog.dismiss()
                        }

                        false
                    }
                } else {
                    progressBar.visibility = View.GONE
                    isLoading = false
                    progressPagination.visibility = View.GONE
                    if (beers.message != null)
                        Error(requireContext(), beers.message).showError()
                }
            }
        })
    }

}