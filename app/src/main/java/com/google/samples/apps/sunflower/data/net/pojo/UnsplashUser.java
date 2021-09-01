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

package com.google.samples.apps.sunflower.data.net.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Data class that represents a user from Unsplash.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below. For a full list of fields, consult the API documentation
 * [here](https://unsplash.com/documentation#get-a-users-public-profile).
 */
public class UnsplashUser {
    @SerializedName("name") private String name;
    @SerializedName("username") private String username;


    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAttributionUrl(){
        return String.format("https://unsplash.com/%s?utm_source=sunflower&utm_medium=referral",username);
    }
}
