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

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import io.reactivex.Completable;
import io.reactivex.Single;
import kotlinx.coroutines.flow.Flow;
import java.util.List;

/**
 * The Data Access Object for the [GardenPlanting] class.
 */
@Dao
public interface GardenPlantingDao {
    @Query("SELECT * FROM garden_plantings")
    Flow<List<GardenPlanting>> getGardenPlantings();

    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    Flow<Boolean> isPlanted(String plantId);

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    Flow<List<PlantAndGardenPlantings>> getPlantedGardens();

    @Insert
    Single<Long> insertGardenPlanting(GardenPlanting gardenPlanting );


    @Delete
    Completable deleteGardenPlanting(GardenPlanting gardenPlanting);

}
