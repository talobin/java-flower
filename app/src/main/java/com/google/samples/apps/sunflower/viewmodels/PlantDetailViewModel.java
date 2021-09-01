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

import android.util.Log;

import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.BuildConfig;
import com.google.samples.apps.sunflower.data.db.GardenPlantingRepository;
import com.google.samples.apps.sunflower.data.db.Plant;
import com.google.samples.apps.sunflower.data.db.PlantRepository;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * The ViewModel used in [PlantDetailFragment].
 */
@HiltViewModel
public class PlantDetailViewModel extends ViewModel {
    private static final String PLANT_ID_SAVED_STATE_KEY = "plantId";
    private  GardenPlantingRepository gardenPlantingRepository;
    private String plantId;
    public LiveData<Boolean> isPlanted;
    private LiveData<Plant> plant;
    @Inject
    public PlantDetailViewModel(SavedStateHandle savedStateHandle, PlantRepository plantRepository, GardenPlantingRepository gardenPlantingRepository){
        super();
        plantId = savedStateHandle.get(PLANT_ID_SAVED_STATE_KEY);
        Log.d("HAI",plantId);
        Log.d("HAI",savedStateHandle.toString());
         this.gardenPlantingRepository = gardenPlantingRepository;
         isPlanted = FlowLiveDataConversions.asLiveData(gardenPlantingRepository.isPlanted(plantId));
         plant = FlowLiveDataConversions.asLiveData(plantRepository.getPlant(plantId));
    }

    public void addPlantToGarden() {
            this.gardenPlantingRepository.createGardenPlanting(plantId);

    }

    public boolean  hasValidUnsplashKey() {
        return BuildConfig.UNSPLASH_ACCESS_KEY != "null";
    }

    public LiveData<Plant> getPlant(){
        return this.plant;
    }





}
