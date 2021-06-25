package com.example.practiceapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practiceapp.model.UserInfo
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val repository: MainRepository
): PagingSource<Int, UserInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserInfo> {
        try {

            val currentLoadingPageKey = params.key ?: 1

            val response = repository.fetchUsersInfo(
                page = currentLoadingPageKey,
                onStart = {},
                onComplete = {},
                onError = {}
            )
            val responseData = response.first()

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}