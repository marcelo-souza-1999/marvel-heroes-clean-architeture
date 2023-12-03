package com.marcelo.marvelheroes.domain.factories

import com.marcelo.marvelheroes.domain.model.DetailChildViewData
import com.marcelo.marvelheroes.domain.model.DetailParentViewData
import org.koin.core.annotation.Single

@Single
class DetailParentFactory {
    fun createDetailParent(
        category: String,
        childList: List<DetailChildViewData>
    ): DetailParentViewData {
        return DetailParentViewData(
            categories = category,
            detailChildList = childList
        )
    }
}