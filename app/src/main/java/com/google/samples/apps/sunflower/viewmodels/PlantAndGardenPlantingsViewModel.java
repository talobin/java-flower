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

import com.google.samples.apps.sunflower.data.db.GardenPlanting;
import com.google.samples.apps.sunflower.data.db.Plant;
import com.google.samples.apps.sunflower.data.db.PlantAndGardenPlantings;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PlantAndGardenPlantingsViewModel {
    private  final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
    private Plant plant;
    private GardenPlanting gardenPlanting;
    public PlantAndGardenPlantingsViewModel(PlantAndGardenPlantings plantings) {
        plant = plantings.getPlant();
        if(plant == null){
            throw new NullPointerException("Plant cannot be null");
        }
        gardenPlanting = plantings.getGardenPlantings().get(0);

    }

    public int getWateringInterval() {
        return plant.getWateringInterval();
    }

    public String getWaterDateString() {
        return dateFormat.format(gardenPlanting.getLastWateringDate().getTime());
    }

    public String getImageUrl(){
        return plant.getImageUrl();
    }

    public String getPlantName(){
        return plant.getName();
    }

    public String getPlantDateString(){
        return dateFormat.format(gardenPlanting.getPlantDate().getTime());
    }

    public String getPlantId(){
        return plant.getPlantId();
    }
}
