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
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections;
import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.data.db.PlantAndGardenPlantings;
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding;
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel;

public class GardenPlantingAdapter extends ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder> {

    public GardenPlantingAdapter() {
        super(new GardenPlantDiffCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_garden_planting, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantAndGardenPlantings plantings = getItem(position);
        holder.itemView.setTag(plantings);
        holder.bind(plantings);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemGardenPlantingBinding binding;

        public ViewHolder(@NonNull ListItemGardenPlantingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlantAndGardenPlantingsViewModel viewModel = binding.getViewModel();
                    if(viewModel!=null&& viewModel.getPlantId()!=null){
                        navigateToPlant(viewModel.getPlantId(), v);
                    }
                }
            });
        }

        private void navigateToPlant(String plantId,View view) {
            NavDirections direction = HomeViewPagerFragmentDirections
                    .Companion.actionViewPagerFragmentToPlantDetailFragment(plantId);
            Navigation.findNavController(view).navigate(direction);
        }

        void bind(PlantAndGardenPlantings plantings) {
            this.binding.setViewModel(new PlantAndGardenPlantingsViewModel(plantings));
            this.binding.executePendingBindings();
        }
    }

    private static class GardenPlantDiffCallback extends DiffUtil.ItemCallback<PlantAndGardenPlantings> {

        @Override
        public boolean areItemsTheSame(@NonNull PlantAndGardenPlantings oldItem,
                                       @NonNull PlantAndGardenPlantings newItem) {
            return oldItem.getPlant().getPlantId().equals(newItem.getPlant().getPlantId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlantAndGardenPlantings oldItem,
                                          @NonNull PlantAndGardenPlantings newItem) {
            return oldItem == newItem;
        }
    }
}
