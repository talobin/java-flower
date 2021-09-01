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

import androidx.paging.PagingSource;

import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.schedulers.Schedulers;
import kotlinx.coroutines.flow.Flow;

@Singleton
public class GardenPlantingRepository {
    private GardenPlantingDao gardenPlantingDao;

    @Inject
    public GardenPlantingRepository(GardenPlantingDao gardenPlantingDao) {
        this.gardenPlantingDao = gardenPlantingDao;
    }

    public void createGardenPlanting(String plantId) {
        GardenPlanting gardenPlanting = new GardenPlanting(plantId, Calendar.getInstance(),Calendar.getInstance());
        gardenPlantingDao.insertGardenPlanting(gardenPlanting).subscribeOn(Schedulers.io()).subscribe();
    }



    public void removeGardenPlanting(GardenPlanting gardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting);
    }

    public Flow<Boolean> isPlanted(String plantId){
        return gardenPlantingDao.isPlanted(plantId);
    }




    public Flow<List<PlantAndGardenPlantings>> getPlantedGardens(){
        return gardenPlantingDao.getPlantedGardens();
    }
}
