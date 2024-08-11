package com.murad.palindrome.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.murad.palindrome.data.paging.UserPagingSource
import com.murad.palindrome.data.remote.api.ApiService
import com.murad.palindrome.data.remote.response.DataItem
import kotlinx.coroutines.flow.Flow


class UserRepository(
    private val apiService: ApiService
) {
    fun getUserList(): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).flow
    }
}