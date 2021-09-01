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
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter;
import com.google.samples.apps.sunflower.data.db.PlantAndGardenPlantings;
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

import static com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter.PLANT_LIST_PAGE_INDEX;

@AndroidEntryPoint
public final class GardenFragment extends Fragment {
    private GardenPlantingListViewModel viewModel ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGardenBinding binding = FragmentGardenBinding.inflate(inflater,container,false);
        GardenPlantingAdapter adapter = new GardenPlantingAdapter();
        viewModel = new ViewModelProvider((requireActivity())).get(GardenPlantingListViewModel.class);
        binding.gardenList.setAdapter(adapter);

        binding.addPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPlantListPage();
            }
        });

        subscribeUi(adapter,binding);
        return binding.getRoot();
    }

    private void subscribeUi(GardenPlantingAdapter adapter ,FragmentGardenBinding binding) {
        viewModel.getPlantAndGardenPlantings().observe(getViewLifecycleOwner(), new Observer<List<PlantAndGardenPlantings>>() {
            @Override
            public void onChanged(List<PlantAndGardenPlantings> result) {
                binding.setHasPlantings(!(result == null || result.isEmpty()));
                adapter.submitList(result);
            }
        });
    }

    // TODO: convert to data binding if applicable
    private void navigateToPlantListPage() {
        ((ViewPager2)requireActivity().findViewById(R.id.view_pager)).setCurrentItem(PLANT_LIST_PAGE_INDEX);

    }
}
