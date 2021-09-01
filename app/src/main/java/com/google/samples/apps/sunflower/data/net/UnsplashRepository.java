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

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingSource;
import com.google.samples.apps.sunflower.api.UnsplashService;
import com.google.samples.apps.sunflower.data.net.pojo.UnsplashPhoto;
import javax.inject.Inject;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;

public class UnsplashRepository {
    private final int NETWORK_PAGE_SIZE = 25;
    private final UnsplashService service;

    @Inject
    public UnsplashRepository(UnsplashService service) {
        this.service = service;
    }

    public Flow<PagingData<UnsplashPhoto>> getSearchResultStream(String query) {
        return new Pager<Integer, UnsplashPhoto>(new PagingConfig(NETWORK_PAGE_SIZE, NETWORK_PAGE_SIZE, false),
                new Function0<PagingSource<Integer, UnsplashPhoto>>() {
                    @Override
                    public PagingSource<Integer, UnsplashPhoto> invoke() {
                        return new UnsplashPagingSource(service, query);
                    }
                }).getFlow();
    }


}
