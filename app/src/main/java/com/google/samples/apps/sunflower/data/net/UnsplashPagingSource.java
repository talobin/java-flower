/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data.net;

import androidx.annotation.NonNull;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;
import com.google.samples.apps.sunflower.BuildConfig;
import com.google.samples.apps.sunflower.api.UnsplashService;
import com.google.samples.apps.sunflower.data.net.pojo.UnsplashPhoto;
import com.google.samples.apps.sunflower.data.net.pojo.UnsplashSearchResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UnsplashPagingSource extends RxPagingSource<Integer, UnsplashPhoto> {
    private static final int UNSPLASH_STARTING_PAGE_INDEX = 1;

    @NonNull
    private UnsplashService service;
    @NonNull
    private String query;
    @NonNull
    PagingSource.LoadResult result;

    public UnsplashPagingSource(@NonNull UnsplashService service, @NonNull String query) {
        this.service = service;
        this.query = query;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, UnsplashPhoto>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        // Start refresh at page 1 if undefined.
        Integer page = loadParams.getKey();
        if (page == null) {
            page = UNSPLASH_STARTING_PAGE_INDEX;
        }

        Integer finalPage = page;
        return service.searchPhotos(query, page, loadParams.getLoadSize(), BuildConfig.UNSPLASH_ACCESS_KEY)
                .subscribeOn(Schedulers.io()).map(unsplashSearchResponse -> toLoadResult(unsplashSearchResponse, finalPage)).onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, UnsplashPhoto> toLoadResult(
            @NonNull UnsplashSearchResponse response,Integer page) {

        return new LoadResult.Page(response.getResults(),
                page == UNSPLASH_STARTING_PAGE_INDEX?null: page-1,
                page  == response.getTotalPages()?null:page+1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, UnsplashPhoto> state) {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, UnsplashPhoto> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}
