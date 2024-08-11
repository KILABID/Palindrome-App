package com.murad.palindrome.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.murad.palindrome.data.remote.api.ApiService
import com.murad.palindrome.data.remote.response.DataItem

class UserPagingSource(private val apiService: ApiService) : PagingSource<Int, DataItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getUserList(page = position, perPage = 4)
            val responseData = response.data ?: emptyList()

            Log.d("UserPagingSource", "Loading position: $position, data size: ${responseData.size}")

            val nextKey = if (responseData.isEmpty()) null else position + 1

            Page(
                data = responseData.filterNotNull(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
