package com.marcelo.marvelheroes.presentation.adapters.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelo.marvelheroes.databinding.LayoutItemChildDetailsBinding
import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.extensions.loadImage
import com.marcelo.marvelheroes.presentation.adapters.details.DetailChildAdapter.Companion.DetailChildViewHolder

class DetailChildAdapter(
    private val detailChildList: List<DetailChildViewData>
) : RecyclerView.Adapter<DetailChildViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailChildViewHolder {
        val itemBinding = LayoutItemChildDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return DetailChildViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DetailChildViewHolder, position: Int) =
        holder.bind(detailChildList[position])

    override fun getItemCount() = detailChildList.size

    companion object {

        class DetailChildViewHolder(
            private val itemBinding: LayoutItemChildDetailsBinding
        ) : RecyclerView.ViewHolder(itemBinding.root) {

            fun bind(detailChildData: DetailChildViewData) = with(itemBinding) {
                imgItemCategory.loadImage(detailChildData.imageUrl)
            }
        }
    }
}