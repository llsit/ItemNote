package com.example.core.domain.usecase.authen

import com.example.core.data.repository.AuthRepository
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