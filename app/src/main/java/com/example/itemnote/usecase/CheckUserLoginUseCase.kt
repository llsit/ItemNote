package com.example.itemnote.usecase

import com.example.itemnote.data.repository.AuthRepository
import javax.inject.Inject

interface CheckUserLoginUseCase {
    fun invoke(): Boolean
}

class CheckUserLoginUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : CheckUserLoginUseCase {
    override fun invoke(): Boolean {
        return repository.checkUserLogin()
    }

}