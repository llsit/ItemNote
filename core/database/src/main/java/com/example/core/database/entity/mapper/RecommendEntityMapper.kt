package com.example.core.database.entity.mapper

import com.example.core.database.entity.RecommendEntity
import com.example.core.model.response.MealResponse

object RecommendEntityMapper : EntityMapper<List<MealResponse>, List<RecommendEntity>> {
    override fun asEntity(domain: List<MealResponse>): List<RecommendEntity> {
        return domain.map {
            RecommendEntity(
                idMeal = it.idMeal.orEmpty(),
                strMeal = it.strMeal.orEmpty(),
                strDrinkAlternate = it.strDrinkAlternate.orEmpty(),
                strCategory = it.strCategory.orEmpty(),
                strArea = it.strArea.orEmpty(),
                strInstructions = it.strInstructions.orEmpty(),
                strMealThumb = it.strMealThumb.orEmpty(),
                strTags = it.strTags.orEmpty(),
                strYoutube = it.strYoutube.orEmpty(),
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
                strImageSource = it.strImageSource.orEmpty(),
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed.orEmpty(),
                dateModified = it.dateModified.orEmpty()
            )
        }
    }

    override fun asDomain(entity: List<RecommendEntity>): List<MealResponse> {
        return entity.map {
            MealResponse(
                idMeal = it.idMeal.orEmpty(),
                strMeal = it.strMeal.orEmpty(),
                strDrinkAlternate = it.strDrinkAlternate.orEmpty(),
                strCategory = it.strCategory.orEmpty(),
                strArea = it.strArea.orEmpty(),
                strInstructions = it.strInstructions.orEmpty(),
                strMealThumb = it.strMealThumb.orEmpty(),
                strTags = it.strTags.orEmpty(),
                strYoutube = it.strYoutube.orEmpty(),
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
                strImageSource = it.strImageSource.orEmpty(),
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed.orEmpty(),
                dateModified = it.dateModified.orEmpty(),
            )
        }
    }
}

fun List<MealResponse>.toRecommendEntity(): List<RecommendEntity> {
    return RecommendEntityMapper.asEntity(this)
}

fun List<RecommendEntity>.toMealResponse(): List<MealResponse> {
    return RecommendEntityMapper.asDomain(this)
}
