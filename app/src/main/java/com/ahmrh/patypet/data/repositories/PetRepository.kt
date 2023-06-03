package com.ahmrh.patypet.data.repositories

import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.retrofit.AuthApiService
import com.ahmrh.patypet.data.remote.retrofit.PetApiService

class PetRepository(
    private val apiService: PetApiService,
    private val pref: AppPreferences
) {
}