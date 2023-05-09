package com.marcelo.marvelheroes.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.marvelheroes.databinding.LayoutrItemParentDetailBinding
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import com.marcelo.marvelheroes.presentation.adapters.DetailParentAdapter.Companion.DetailParentViewHolder

class DetailParentAdapter(
    private val detailParentList: List<DetailParentViewData>
) : RecyclerView.Adapter<DetailParentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailParentViewHolder {
        val itemBinding = LayoutrItemParentDetailBinding.inflate(
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
            private val itemBinding: LayoutrItemParentDetailBinding
        ) : RecyclerView.ViewHolder(itemBinding.root) {

            private lateinit var childAdapter: DetailChildAdapter

            fun bind(detailParentData: DetailParentViewData) = with(itemBinding) {

                txtTitleCategory.text = itemView.context.getString(detailParentData.categoriesResId)
                childAdapter = DetailChildAdapter(detailParentData.detailChildList)
                rvChildDetails.apply {
                    setHasFixedSize(true)
                    adapter = childAdapter
                }
            }
        }
    }
}