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

package com.google.samples.apps.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.google.samples.apps.sunflower.data.net.UnsplashRepository;
import com.google.samples.apps.sunflower.data.net.pojo.UnsplashPhoto;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GalleryViewModel extends ViewModel {
    private @Nullable
    UnsplashRepository repository;
    LiveData<PagingData<UnsplashPhoto>> currentSearchResult;
    @Inject
    public GalleryViewModel(UnsplashRepository repository) {
        this.currentSearchResult = null;
        this.repository = repository;
    }

    public LiveData<PagingData<UnsplashPhoto>> searchPictures(@NonNull String queryString)  {
        //TODO: Figure out the caching ex:cachedIn(viewModel.viewModelScope)
        LiveData<PagingData<UnsplashPhoto>>  newResult =
                FlowLiveDataConversions.asLiveData(repository.getSearchResultStream(queryString));
        currentSearchResult = newResult;
        return newResult;
    }
}
