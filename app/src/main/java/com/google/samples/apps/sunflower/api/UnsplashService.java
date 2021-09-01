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

package com.google.samples.apps.sunflower.api;

import com.google.samples.apps.sunflower.data.net.pojo.UnsplashSearchResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashService {

    @GET("search/photos")
    public Single<UnsplashSearchResponse> searchPhotos(
            @Query("query") String query,
            @Query("page")int page ,
            @Query("per_page")int perPage,
            @Query("client_id") String clientId  );

}
