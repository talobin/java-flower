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

import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.samples.apps.sunflower.data.db.GardenPlantingRepository;
import com.google.samples.apps.sunflower.data.db.PlantAndGardenPlantings;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GardenPlantingListViewModel extends ViewModel {
    private LiveData<List<PlantAndGardenPlantings>> plantAndGardenPlantings;

    @Inject
    public GardenPlantingListViewModel(GardenPlantingRepository gardenPlantingRepository) {
        plantAndGardenPlantings = FlowLiveDataConversions.asLiveData(gardenPlantingRepository.getPlantedGardens());
    }

    public LiveData<List<PlantAndGardenPlantings>> getPlantAndGardenPlantings() {
        return plantAndGardenPlantings;
    }
}
