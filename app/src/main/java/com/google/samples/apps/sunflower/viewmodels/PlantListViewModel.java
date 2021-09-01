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
import androidx.arch.core.util.Function;
import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.google.samples.apps.sunflower.data.db.Plant;
import com.google.samples.apps.sunflower.data.db.PlantRepository;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * The ViewModel for [PlantListFragment].
 */
@HiltViewModel
public class PlantListViewModel extends ViewModel {
    private static final int  NO_GROW_ZONE = -1;
    private static final String  GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY";
    private final MutableLiveData<Integer> growZone;
    private final LiveData<List<Plant>> plants;
    @Inject
    public PlantListViewModel(@NonNull PlantRepository plantRepository,
                                SavedStateHandle savedStateHandle) {
        super();
        if(savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY)!=null){
            growZone = new MutableLiveData<>(savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY));
        }else{
            growZone =  new MutableLiveData<>(NO_GROW_ZONE);
        }

        plants = Transformations.switchMap(growZone, new Function<Integer, LiveData<List<Plant>>>() {
            @Override
            public LiveData<List<Plant>> apply(Integer input) {
                if (input == NO_GROW_ZONE) {
                    return FlowLiveDataConversions.asLiveData(plantRepository.getPlants());
                } else {
                    return FlowLiveDataConversions.asLiveData(plantRepository.getPlantsWithGrowZoneNumber(input));
                }
            }
        });
    }

    public LiveData<List<Plant>> getPlants() {
        return plants;
    }

    public void setGrowZoneNumber(int number){
        growZone.setValue(number);
    }

    public void clearGrowZoneNumber(){
        growZone.setValue(NO_GROW_ZONE);
    }

    public boolean isFiltered(){
        return growZone.getValue() != NO_GROW_ZONE;
    }
}
