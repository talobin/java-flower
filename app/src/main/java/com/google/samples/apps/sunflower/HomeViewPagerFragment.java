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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter;
import com.google.samples.apps.sunflower.databinding.FragmentViewPagerBinding;
import dagger.hilt.android.AndroidEntryPoint;
import static com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter.MY_GARDEN_PAGE_INDEX;
import static com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter.PLANT_LIST_PAGE_INDEX;

@AndroidEntryPoint
public final class HomeViewPagerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentViewPagerBinding binding = FragmentViewPagerBinding.inflate(inflater,container,false);
        TabLayout tabLayout = binding.tabs;
        ViewPager2 viewPager = binding.viewPager;

        viewPager.setAdapter(new SunflowerPagerAdapter(this));

        // Set the icon and text for each tab
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(getTabIcon(position));
                tab.setText( getTabTitle(position));
            }
        }).attach();


        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);

        return binding.getRoot();
    }

    private int getTabIcon(int position){
        switch (position) {
            case MY_GARDEN_PAGE_INDEX:
                return R.drawable.garden_tab_selector;
            case PLANT_LIST_PAGE_INDEX:
                return R.drawable.plant_list_tab_selector;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    private @Nullable String getTabTitle(int position) {
        switch (position) {
            case MY_GARDEN_PAGE_INDEX:
                return getString(R.string.my_garden_title);
            case PLANT_LIST_PAGE_INDEX:
                return getString(R.string.plant_list_title);
            default:
                return null;
        }
    }
}
