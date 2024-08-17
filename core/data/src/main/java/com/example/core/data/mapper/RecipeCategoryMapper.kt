package com.example.core.data.mapper

import com.example.core.data.utils.ResponseMapper
import com.example.core.model.data.CategoryModel
import com.example.core.model.response.Category

object RecipeCategoryMapper : ResponseMapper<List<CategoryModel>, List<Category>> {
    override fun asResponse(domain: List<CategoryModel>): List<Category> {
        return domain.map {
            Category(
                idCategory = it.idCategory,
                strCategory = it.strCategory,
                strCategoryThumb = it.strCategoryThumb,
                strCategoryDescription = it.strCategoryDescription
            )
        }
    }

    override fun asDomain(entity: List<Category>): List<CategoryModel> {
        return entity.map {
            CategoryModel(
                idCategory = it.idCategory,
                strCategory = it.strCategory,
                strCategoryThumb = it.strCategoryThumb,
                strCategoryDescription = it.strCategoryDescription
            )
        }
    }
}

fun List<Category>.toCategoryModel(): List<CategoryModel> {
    return RecipeCategoryMapper.asDomain(this)
}