package com.myapp.newsapp.presentation.ui.fragment.update

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.usecase.user.GetUserInfoUseCase
import com.myapp.newsapp.domain.usecase.user.UpdateUserInfoUseCase
import com.myapp.newsapp.presentation.model.User
import com.myapp.newsapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserInfoViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
) : ViewModel() {

    val _updateUserInfoState = MutableLiveData<State>()
    val updateUserInfoState: LiveData<State> = _updateUserInfoState

    fun updateUserInfo(user: User, updatedInformation: HashMap<String, Any?>){
        viewModelScope.launch {
            Log.d("Update","Runnning")
            updateUserInfoUseCase(user,updatedInformation).collect(){ result->
                when(result){
                    is UserResource.Loading -> _updateUserInfoState.value = State(isLoading = true)
                    is UserResource.Failed ->_updateUserInfoState.value =  State(isFailed = true, message = result.message)
                    is UserResource.Success ->_updateUserInfoState.value =  State(isSuccess = true, message = result.data)
                }
            }
        }
    }
}