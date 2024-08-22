package com.example.core.data.mapper

import com.example.core.common.utils.ResponseMapper
import com.example.core.model.data.Meal
import com.example.core.model.data.RecipeModel
import com.example.core.model.response.MealResponse
import com.example.core.model.response.RecipeResponse

object RecipeMapper :
    ResponseMapper<List<RecipeModel>, List<RecipeResponse>> {
    override fun asResponse(domain: List<RecipeModel>): List<RecipeResponse> {
        return domain.map { recipe ->
            RecipeResponse(
                meals = recipe.meals.map {
                    MealResponse(
                        idMeal = it.idMeal,
                        strMeal = it.strMeal,
                        strCategory = it.strCategory,
                        strDrinkAlternate = it.strDrinkAlternate,
                        strMealThumb = it.strMealThumb,
                        strInstructions = it.strInstructions,
                        strImageSource = it.strImageSource,
                        strIngredient1 = it.strIngredient1,
                        strIngredient2 = it.strIngredient2,
                        strIngredient3 = it.strIngredient3,
                        strIngredient4 = it.strIngredient4,
                        strIngredient5 = it.strIngredient5,
                        strIngredient6 = it.strIngredient6,
                        strIngredient7 = it.strIngredient7,
                        strIngredient8 = it.strIngredient8,
                        strIngredient9 = it.strIngredient9,
                        strIngredient10 = it.strIngredient10,
                        strIngredient11 = it.strIngredient11,
                        strIngredient12 = it.strIngredient12,
                        strIngredient13 = it.strIngredient13,
                        strIngredient14 = it.strIngredient14,
                        strIngredient15 = it.strIngredient15,
                        strIngredient16 = it.strIngredient16,
                        strIngredient17 = it.strIngredient17,
                        strIngredient18 = it.strIngredient18,
                        strIngredient19 = it.strIngredient19,
                        strIngredient20 = it.strIngredient20,
                        strMeasure1 = it.strMeasure1,
                        strMeasure2 = it.strMeasure2,
                        strMeasure3 = it.strMeasure3,
                        strMeasure4 = it.strMeasure4,
                        strMeasure5 = it.strMeasure5,
                        strMeasure6 = it.strMeasure6,
                        strMeasure7 = it.strMeasure7,
                        strMeasure8 = it.strMeasure8,
                        strMeasure9 = it.strMeasure9,
                        strMeasure10 = it.strMeasure10,
                        strMeasure11 = it.strMeasure11,
                        strMeasure12 = it.strMeasure12,
                        strMeasure13 = it.strMeasure13,
                        strMeasure14 = it.strMeasure14,
                        strMeasure15 = it.strMeasure15,
                        strMeasure16 = it.strMeasure16,
                        strMeasure17 = it.strMeasure17,
                        strMeasure18 = it.strMeasure18,
                        strMeasure19 = it.strMeasure19,
                        strMeasure20 = it.strMeasure20,
                        strSource = it.strSource,
                        strYoutube = it.strYoutube,
                        strArea = it.strArea,
                        strTags = it.strTags,
                        strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                        dateModified = it.dateModified
                    )
                }
            )
        }
    }

    override fun asDomain(entity: List<RecipeResponse>): List<RecipeModel> {
        return entity.map {
            RecipeModel(
                meals = it.meals.map {
                    Meal(
                        idMeal = it.idMeal.orEmpty(),
                        strMeal = it.strMeal.orEmpty(),
                        strCategory = it.strCategory.orEmpty(),
                        strDrinkAlternate = it.strDrinkAlternate,
                        strMealThumb = it.strMealThumb.orEmpty(),
                        strInstructions = it.strInstructions.orEmpty(),
                        strImageSource = it.strImageSource.orEmpty(),
                        strIngredient1 = it.strIngredient1.orEmpty(),
                        strIngredient2 = it.strIngredient2.orEmpty(),
                        strIngredient3 = it.strIngredient3.orEmpty(),
                        strIngredient4 = it.strIngredient4.orEmpty(),
                        strIngredient5 = it.strIngredient5.orEmpty(),
                        strIngredient6 = it.strIngredient6.orEmpty(),
                        strIngredient7 = it.strIngredient7.orEmpty(),
                        strIngredient8 = it.strIngredient8.orEmpty(),
                        strIngredient9 = it.strIngredient9.orEmpty(),
                        strIngredient10 = it.strIngredient10.orEmpty(),
                        strIngredient11 = it.strIngredient11.orEmpty(),
                        strIngredient12 = it.strIngredient12.orEmpty(),
                        strIngredient13 = it.strIngredient13.orEmpty(),
                        strIngredient14 = it.strIngredient14.orEmpty(),
                        strIngredient15 = it.strIngredient15.orEmpty(),
                        strIngredient16 = it.strIngredient16.orEmpty(),
                        strIngredient17 = it.strIngredient17.orEmpty(),
                        strIngredient18 = it.strIngredient18.orEmpty(),
                        strIngredient19 = it.strIngredient19.orEmpty(),
                        strIngredient20 = it.strIngredient20.orEmpty(),
                        strMeasure1 = it.strMeasure1.orEmpty(),
                        strMeasure2 = it.strMeasure2.orEmpty(),
                        strMeasure3 = it.strMeasure3.orEmpty(),
                        strMeasure4 = it.strMeasure4.orEmpty(),
                        strMeasure5 = it.strMeasure5.orEmpty(),
                        strMeasure6 = it.strMeasure6.orEmpty(),
                        strMeasure7 = it.strMeasure7.orEmpty(),
                        strMeasure8 = it.strMeasure8.orEmpty(),
                        strMeasure9 = it.strMeasure9.orEmpty(),
                        strMeasure10 = it.strMeasure10.orEmpty(),
                        strMeasure11 = it.strMeasure11.orEmpty(),
                        strMeasure12 = it.strMeasure12.orEmpty(),
                        strMeasure13 = it.strMeasure13.orEmpty(),
                        strMeasure14 = it.strMeasure14.orEmpty(),
                        strMeasure15 = it.strMeasure15.orEmpty(),
                        strMeasure16 = it.strMeasure16.orEmpty(),
                        strMeasure17 = it.strMeasure17.orEmpty(),
                        strMeasure18 = it.strMeasure18.orEmpty(),
                        strMeasure19 = it.strMeasure19.orEmpty(),
                        strMeasure20 = it.strMeasure20.orEmpty(),
                        strSource = it.strSource.orEmpty(),
                        strYoutube = it.strYoutube.orEmpty(),
                        strArea = it.strArea.orEmpty(),
                        strTags = it.strTags.orEmpty(),
                        strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                        dateModified = it.dateModified
                    )
                }
            )
        }
    }
}

fun List<RecipeResponse>.toRecipeModel(): List<RecipeModel> {
    return RecipeMapper.asDomain(this)
}