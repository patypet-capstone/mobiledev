package com.ahmrh.patypet.ui.screen.patypet.pet

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ahmrh.patypet.data.repositories.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val petRepository: PetRepository
): ViewModel() {

    private val _visiblePermissionDialogQueue = mutableStateListOf<String>()
    val visiblePermissionDialogQueue
        get() = _visiblePermissionDialogQueue

    private val _permission = mutableListOf<String>()
    val permission
        get() = _permission

    fun dismissDialog(){
        visiblePermissionDialogQueue.removeLast()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if(!isGranted){
            visiblePermissionDialogQueue.add(0, permission)
        } else {
            _permission.add(0, permission)
        }
    }

}