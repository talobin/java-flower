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

package com.google.samples.apps.sunflower.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.samples.apps.sunflower.GardenFragment;
import com.google.samples.apps.sunflower.PlantListFragment;

import java.util.HashMap;
import java.util.Map;

public class SunflowerPagerAdapter extends FragmentStateAdapter {
    public static final int MY_GARDEN_PAGE_INDEX = 1;
    public static final int PLANT_LIST_PAGE_INDEX = 0;
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private Map<Integer,Fragment> tabFragmentsCreators;

    public SunflowerPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        tabFragmentsCreators = new HashMap<>();
        tabFragmentsCreators.put(MY_GARDEN_PAGE_INDEX,new GardenFragment());
        tabFragmentsCreators.put(PLANT_LIST_PAGE_INDEX,new PlantListFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position>=getItemCount()){
            throw new IndexOutOfBoundsException();
        }
        return tabFragmentsCreators.get(position);
    }

    @Override
    public int getItemCount() {
        return tabFragmentsCreators.size();
    }
}
