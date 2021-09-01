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

package com.google.samples.apps.sunflower.data.db;

import javax.inject.Inject;
import javax.inject.Singleton;

import kotlinx.coroutines.flow.Flow;

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [PlantDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
public class PlantRepository {

    private final PlantDao plantDao;

    @Inject
    public PlantRepository(PlantDao plantDao) {
        this.plantDao = plantDao;
    }

    public Flow getPlants(){
        return plantDao.getPlants();
    }

    public Flow getPlant(String plantId){
        return plantDao.getPlant(plantId);
    }


    public Flow getPlantsWithGrowZoneNumber(int growZoneNumber){
        return plantDao.getPlantsWithGrowZoneNumber(growZoneNumber);
    }


}
