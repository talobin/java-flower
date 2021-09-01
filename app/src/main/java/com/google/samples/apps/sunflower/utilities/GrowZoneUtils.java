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

package com.google.samples.apps.sunflower.utilities;
/**
 * A helper function to determine a Plant's growing zone for a given latitude.
 *
 * The numbers listed here are roughly based on the United States Department of Agriculture's
 * Plant Hardiness Zone Map (http://planthardiness.ars.usda.gov/), which helps determine which
 * plants are most likely to thrive at a location.
 *
 * If a given latitude falls on the border between two zone ranges, the larger zone range is chosen
 * (e.g. latitude 14.0 => zone 12).
 *
 * Negative latitude values are converted to positive with [Math.abs].
 *
 * For latitude values greater than max (90.0), zone 1 is returned.
 */
public final class GrowZoneUtils {
    public static final int getZoneForLatitude(double latitude) {
        double var2 = Math.abs(latitude);
        return var2 >= 0.0D && var2 <= 7.0D ? 13
                : (var2 >= 7.0D && var2 <= 14.0D ? 12
                : (var2 >= 14.0D && var2 <= 21.0D ? 11
                : (var2 >= 21.0D && var2 <= 28.0D ? 10
                : (var2 >= 28.0D && var2 <= 35.0D ? 9
                : (var2 >= 35.0D && var2 <= 42.0D ? 8
                : (var2 >= 42.0D && var2 <= 49.0D ? 7
                : (var2 >= 49.0D && var2 <= 56.0D ? 6
                : (var2 >= 56.0D && var2 <= 63.0D ? 5
                : (var2 >= 63.0D && var2 <= 70.0D ? 4
                : (var2 >= 70.0D && var2 <= 77.0D ? 3
                : (var2 >= 77.0D && var2 <= 84.0D ? 2
                : 1))))))))))); // Remaining latitudes are assigned to zone 1.
    }
}
