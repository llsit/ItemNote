package com.example.core.model.response

import kotlinx.serialization.SerialName

data class RecipeResponse(
    @SerialName("meals") var meals: List<MealResponse> = listOf()
)

data class MealResponse(
    @SerialName("idMeal") var idMeal: String? = null,
    @SerialName("strMeal") var strMeal: String? = null,
    @SerialName("strDrinkAlternate") var strDrinkAlternate: String? = null,
    @SerialName("strCategory") var strCategory: String? = null,
    @SerialName("strArea") var strArea: String? = null,
    @SerialName("strInstructions") var strInstructions: String? = null,
    @SerialName("strMealThumb") var strMealThumb: String? = null,
    @SerialName("strTags") var strTags: String? = null,
    @SerialName("strYoutube") var strYoutube: String? = null,
    @SerialName("strIngredient1") var strIngredient1: String? = null,
    @SerialName("strIngredient2") var strIngredient2: String? = null,
    @SerialName("strIngredient3") var strIngredient3: String? = null,
    @SerialName("strIngredient4") var strIngredient4: String? = null,
    @SerialName("strIngredient5") var strIngredient5: String? = null,
    @SerialName("strIngredient6") var strIngredient6: String? = null,
    @SerialName("strIngredient7") var strIngredient7: String? = null,
    @SerialName("strIngredient8") var strIngredient8: String? = null,
    @SerialName("strIngredient9") var strIngredient9: String? = null,
    @SerialName("strIngredient10") var strIngredient10: String? = null,
    @SerialName("strIngredient11") var strIngredient11: String? = null,
    @SerialName("strIngredient12") var strIngredient12: String? = null,
    @SerialName("strIngredient13") var strIngredient13: String? = null,
    @SerialName("strIngredient14") var strIngredient14: String? = null,
    @SerialName("strIngredient15") var strIngredient15: String? = null,
    @SerialName("strIngredient16") var strIngredient16: String? = null,
    @SerialName("strIngredient17") var strIngredient17: String? = null,
    @SerialName("strIngredient18") var strIngredient18: String? = null,
    @SerialName("strIngredient19") var strIngredient19: String? = null,
    @SerialName("strIngredient20") var strIngredient20: String? = null,
    @SerialName("strMeasure1") var strMeasure1: String? = null,
    @SerialName("strMeasure2") var strMeasure2: String? = null,
    @SerialName("strMeasure3") var strMeasure3: String? = null,
    @SerialName("strMeasure4") var strMeasure4: String? = null,
    @SerialName("strMeasure5") var strMeasure5: String? = null,
    @SerialName("strMeasure6") var strMeasure6: String? = null,
    @SerialName("strMeasure7") var strMeasure7: String? = null,
    @SerialName("strMeasure8") var strMeasure8: String? = null,
    @SerialName("strMeasure9") var strMeasure9: String? = null,
    @SerialName("strMeasure10") var strMeasure10: String? = null,
    @SerialName("strMeasure11") var strMeasure11: String? = null,
    @SerialName("strMeasure12") var strMeasure12: String? = null,
    @SerialName("strMeasure13") var strMeasure13: String? = null,
    @SerialName("strMeasure14") var strMeasure14: String? = null,
    @SerialName("strMeasure15") var strMeasure15: String? = null,
    @SerialName("strMeasure16") var strMeasure16: String? = null,
    @SerialName("strMeasure17") var strMeasure17: String? = null,
    @SerialName("strMeasure18") var strMeasure18: String? = null,
    @SerialName("strMeasure19") var strMeasure19: String? = null,
    @SerialName("strMeasure20") var strMeasure20: String? = null,
    @SerialName("strSource") var strSource: String? = null,
    @SerialName("strImageSource") var strImageSource: String? = null,
    @SerialName("strCreativeCommonsConfirmed") var strCreativeCommonsConfirmed: String? = null,
    @SerialName("dateModified") var dateModified: String? = null
)

