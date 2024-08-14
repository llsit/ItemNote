package com.example.recipe.data.mapper

import com.example.recipe.data.model.Category
import com.example.recipe.data.model.CategoryModel
import com.example.recipe.utils.ResponseMapper

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