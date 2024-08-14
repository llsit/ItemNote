package com.example.recipe.utils

interface ResponseMapper<Domain, Response> {

    fun asResponse(domain: Domain): Response

    fun asDomain(entity: Response): Domain
}
