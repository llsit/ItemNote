package com.example.core.data.mapper

import com.example.core.common.utils.ResponseMapper
import com.example.core.model.data.RecipeCategoryModel
import com.example.core.model.response.Category

object RecipeCategoryMapper : ResponseMapper<List<RecipeCategoryModel>, List<Category>> {
    override fun asResponse(domain: List<RecipeCategoryModel>): List<Category> {
        return domain.map {
            Category(
                idCategory = it.idCategory,
                strCategory = it.strCategory,
                strCategoryThumb = it.strCategoryThumb,
                strCategoryDescription = it.strCategoryDescription
            )
        }
    }

    override fun asDomain(entity: List<Category>): List<RecipeCategoryModel> {
        return entity.map {
            RecipeCategoryModel(
                idCategory = it.idCategory,
                strCategory = it.strCategory,
                strCategoryThumb = it.strCategoryThumb,
                strCategoryDescription = it.strCategoryDescription
            )
        }
    }
}

fun List<Category>.toCategoryModel(): List<RecipeCategoryModel> {
    return RecipeCategoryMapper.asDomain(this)
}