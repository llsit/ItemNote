package com.example.core.data.mapper

import com.example.core.database.entity.IngredientEntity
import com.example.core.database.entity.RecipeEntity
import com.example.core.database.entity.mapper.EntityMapper
import com.example.core.model.response.MealResponse

object RecipeInfoMapper : EntityMapper<MealResponse, RecipeEntity> {
    override fun asEntity(domain: MealResponse): RecipeEntity {
        return RecipeEntity(
            idMeal = domain.idMeal.orEmpty(),
            strMeal = domain.strMeal.orEmpty(),
            strMealThumb = domain.strMealThumb.orEmpty(),
            strCategory = domain.strCategory.orEmpty(),
            strArea = domain.strArea.orEmpty(),
            strInstructions = domain.strInstructions.orEmpty(),
            strYoutube = domain.strYoutube.orEmpty(),
            listIngredient = getIngredientList(domain),
        )
    }

    override fun asDomain(entity: RecipeEntity): MealResponse {
        return MealResponse(
            idMeal = entity.idMeal,
            strMeal = entity.strMeal,
            strMealThumb = entity.strMealThumb,
            strCategory = entity.strCategory,
            strArea = entity.strArea,
            strInstructions = entity.strInstructions,
            strYoutube = entity.strYoutube,
            strTags = "",
            strDrinkAlternate = "",
        )
    }

    private fun getIngredientList(response: MealResponse): List<IngredientEntity> {
        val mealList = response.toHashMap()
        return (1..20).mapNotNull { index ->
            val ingredientKey = "strIngredient$index"
            val measureKey = "strMeasure$index"
            val ingredient = mealList[ingredientKey]
            val measure = mealList[measureKey]

            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                IngredientEntity(ingredient, measure)
            } else {
                null
            }
        }
    }

    private fun MealResponse.toHashMap(): HashMap<String, String?> {
        return hashMapOf(
            "idMeal" to idMeal,
            "strMeal" to strMeal,
            "strDrinkAlternate" to strDrinkAlternate,
            "strCategory" to strCategory,
            "strArea" to strArea,
            "strInstructions" to strInstructions,
            "strMealThumb" to strMealThumb,
            "strTags" to strTags,
            "strYoutube" to strYoutube,
            "strIngredient1" to strIngredient1,
            "strIngredient2" to strIngredient2,
            "strIngredient3" to strIngredient3,
            "strIngredient4" to strIngredient4,
            "strIngredient5" to strIngredient5,
            "strIngredient6" to strIngredient6,
            "strIngredient7" to strIngredient7,
            "strIngredient8" to strIngredient8,
            "strIngredient9" to strIngredient9,
            "strIngredient10" to strIngredient10,
            "strIngredient11" to strIngredient11,
            "strIngredient12" to strIngredient12,
            "strIngredient13" to strIngredient13,
            "strIngredient14" to strIngredient14,
            "strIngredient15" to strIngredient15,
            "strIngredient16" to strIngredient16,
            "strIngredient17" to strIngredient17,
            "strIngredient18" to strIngredient18,
            "strIngredient19" to strIngredient19,
            "strIngredient20" to strIngredient20,
            "strMeasure1" to strMeasure1,
            "strMeasure2" to strMeasure2,
            "strMeasure3" to strMeasure3,
            "strMeasure4" to strMeasure4,
            "strMeasure5" to strMeasure5,
            "strMeasure6" to strMeasure6,
            "strMeasure7" to strMeasure7,
            "strMeasure8" to strMeasure8,
            "strMeasure9" to strMeasure9,
            "strMeasure10" to strMeasure10,
            "strMeasure11" to strMeasure11,
            "strMeasure12" to strMeasure12,
            "strMeasure13" to strMeasure13,
            "strMeasure14" to strMeasure14,
            "strMeasure15" to strMeasure15,
            "strMeasure16" to strMeasure16,
            "strMeasure17" to strMeasure17,
            "strMeasure18" to strMeasure18,
            "strMeasure19" to strMeasure19,
            "strMeasure20" to strMeasure20,
            "strSource" to strSource,
            "strImageSource" to strImageSource,
            "strCreativeCommonsConfirmed" to strCreativeCommonsConfirmed,
            "dateModified" to dateModified
        )
    }
}

fun MealResponse.asRecipeEntity(): RecipeEntity {
    return RecipeInfoMapper.asEntity(this)
}