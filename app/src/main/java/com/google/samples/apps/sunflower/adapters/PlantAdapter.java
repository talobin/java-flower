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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections;
import com.google.samples.apps.sunflower.api.UnsplashClient;
import com.google.samples.apps.sunflower.api.UnsplashService;
import com.google.samples.apps.sunflower.data.db.Plant;
import com.google.samples.apps.sunflower.databinding.ListItemPlantBinding;

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
public class PlantAdapter extends ListAdapter<Plant, RecyclerView.ViewHolder> {

    public PlantAdapter() {
        super(new PlantDiffCallback());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlantViewHolder(ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Plant plant = getItem(position);
        ((PlantViewHolder) holder).bind(plant);
    }


    private static class PlantViewHolder extends RecyclerView.ViewHolder {
        private ListItemPlantBinding binding;

        PlantViewHolder(@NonNull ListItemPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Plant plant = binding.getPlant();
                    if (plant != null) {
                        navigateToPlant(plant, v);

                    }
                }
            });
        }

        private void navigateToPlant(Plant plant, View view) {
            NavDirections direction =
                    HomeViewPagerFragmentDirections.Companion.actionViewPagerFragmentToPlantDetailFragment(plant.getPlantId());
            Navigation.findNavController(view).navigate(direction);
        }

        void bind(Plant item) {
            binding.setPlant(item);
            binding.executePendingBindings();
        }
    }

    static class PlantDiffCallback extends DiffUtil.ItemCallback<Plant> {

        @Override
        public boolean areItemsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem.getPlantId().equals(newItem.getPlantId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem == newItem;
        }
    }


}
