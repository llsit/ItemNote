package com.example.core.data.mapper

import com.example.core.model.response.Category
import com.example.core.model.data.CategoryModel
import com.example.core.data.utils.ResponseMapper


object RecipeCategoryMapper : ResponseMapper<List<com.example.core.model.data.CategoryModel>, List<com.example.core.model.response.Category>> {
    override fun asResponse(domain: List<com.example.core.model.data.CategoryModel>): List<com.example.core.model.response.Category> {
        return domain.map {
            com.example.core.model.response.Category(
                idCategory = it.idCategory,
                strCategory = it.strCategory,
                strCategoryThumb = it.strCategoryThumb,
                strCategoryDescription = it.strCategoryDescription
            )
        }
    }

    override fun asDomain(entity: List<com.example.core.model.response.Category>): List<com.example.core.model.data.CategoryModel> {
        return entity.map {
            com.example.core.model.data.CategoryModel(
                idCategory = it.idCategory,
                strCategory = it.strCategory,
                strCategoryThumb = it.strCategoryThumb,
                strCategoryDescription = it.strCategoryDescription
            )
        }
    }
}

fun List<com.example.core.model.response.Category>.toCategoryModel(): List<com.example.core.model.data.CategoryModel> {
    return RecipeCategoryMapper.asDomain(this)
}