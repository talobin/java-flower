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

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_YEAR;

@Entity(tableName = "plants")
public class Plant {
    @NonNull @PrimaryKey @ColumnInfo(name = "id")
    String plantId;
    String name;
    String description;
    int growZoneNumber;
    int wateringInterval = 7; // how often the plant should be watered, in days
    String imageUrl = "";

    @Ignore
    public Plant(String plantId, String name, String description, int growZoneNumber, int wateringInterval) {
        this.plantId = plantId;
        this.name = name;
        this.description = description;
        this.growZoneNumber = growZoneNumber;
        this.wateringInterval = wateringInterval;
    }

    public Plant(String plantId, String name, String description, int growZoneNumber) {
        this.plantId = plantId;
        this.name = name;
        this.description = description;
        this.growZoneNumber = growZoneNumber;
    }

    public String getPlantId() {
        return plantId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getGrowZoneNumber() {
        return growZoneNumber;
    }

    public int getWateringInterval() {
        return wateringInterval;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     */
    public boolean shouldBeWatered(Calendar since,Calendar lastWateringDate){
        lastWateringDate.add(DAY_OF_YEAR,wateringInterval);
        return since.after(lastWateringDate);
    }


    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
