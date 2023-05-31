package com.ahmrh.patypet.data.repositories

import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.retrofit.ApiService

class PetRepository(
    private val apiService: ApiService,
    private val pref: AppPreferences
) {
}