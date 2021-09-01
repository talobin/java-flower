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

package com.google.samples.apps.sunflower;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.samples.apps.sunflower.adapters.PlantAdapter;
import com.google.samples.apps.sunflower.data.db.Plant;
import com.google.samples.apps.sunflower.databinding.FragmentPlantListBinding;
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class PlantListFragment extends Fragment {

    private PlantListViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPlantListBinding binding = FragmentPlantListBinding.inflate(inflater,container,false);
        if(getContext()== null){
            return binding.getRoot();
        }

        PlantAdapter adapter= new PlantAdapter();
        viewModel = new ViewModelProvider(requireActivity()).get(PlantListViewModel.class);
        binding.plantList.setAdapter(adapter);
        subscribeUi(adapter);

        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plant_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter_zone) {
            updateData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateData() {
            if (viewModel.isFiltered()) {
                viewModel.clearGrowZoneNumber();
            } else {
                viewModel.setGrowZoneNumber(9);
            }
    }

    private void subscribeUi(PlantAdapter adapter) {
        viewModel.getPlants().observe(getViewLifecycleOwner(), new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> result) {
                adapter.submitList(result);
            }
        });
    }
}
