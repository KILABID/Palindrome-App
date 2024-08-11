package com.murad.palindrome.view.third

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.murad.palindrome.data.remote.response.DataItem
import com.murad.palindrome.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ThirdViewModel(userRepository: UserRepository) : ViewModel() {
    val user: Flow<PagingData<DataItem>> =
        userRepository.getUserList().cachedIn(viewModelScope)
}