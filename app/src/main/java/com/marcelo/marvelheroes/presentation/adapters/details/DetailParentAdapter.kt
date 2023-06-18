package com.marcelo.marvelheroes.presentation.adapters.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.marvelheroes.databinding.LayoutItemParentDetailBinding
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.presentation.adapters.details.DetailParentAdapter.Companion.DetailParentViewHolder

class DetailParentAdapter(
    private val detailParentList: List<DetailParentViewData>
) : RecyclerView.Adapter<DetailParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailParentViewHolder {
        val itemBinding = LayoutItemParentDetailBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return DetailParentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DetailParentViewHolder, position: Int) {
        holder.bind(detailParentList[position])
    }

    override fun getItemCount() = detailParentList.size

    companion object {

        class DetailParentViewHolder(
            private val itemBinding: LayoutItemParentDetailBinding
        ) : RecyclerView.ViewHolder(itemBinding.root) {

            private lateinit var childAdapter: DetailChildAdapter

            fun bind(detailParentData: DetailParentViewData) = with(itemBinding) {

                txtTitleCategory.text = detailParentData.categories
                childAdapter = DetailChildAdapter(detailParentData.detailChildList)
                rvChildDetails.apply {
                    setHasFixedSize(true)
                    adapter = childAdapter
                }
            }
        }
    }
}