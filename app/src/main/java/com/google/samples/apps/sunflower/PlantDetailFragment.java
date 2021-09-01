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

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ShareCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.samples.apps.sunflower.data.db.Plant;
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * A fragment representing a single Plant detail screen.
 */
@AndroidEntryPoint
public final class PlantDetailFragment extends Fragment {
    private PlantDetailViewModel plantDetailViewModel;
    private boolean isToolbarShown = false;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPlantDetailBinding binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_plant_detail,container, false);
        if(getContext() == null){
            return binding.getRoot();
        }
        plantDetailViewModel = new ViewModelProvider(PlantDetailFragment.this).get(PlantDetailViewModel.class);
        binding.setViewModel(plantDetailViewModel);
        binding.setLifecycleOwner(this);
        binding.setCallback(new Callback() {
            @Override
            public void add(@Nullable Plant plant) {
                if (plant!=null){
                    hideAppBarFab(binding.fab);
                    plantDetailViewModel.addPlantToGarden();
                    Snackbar.make(binding.getRoot(), R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        binding.galleryNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGallery();
            }
        });

        // scroll change listener begins at Y = 0 when image is fully collapsed
        binding.plantDetailScrollview.setOnScrollChangeListener(
                new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        // User scrolled past image to height of toolbar and the title text is
                        // underneath the toolbar, so the toolbar should be shown.
                        boolean shouldShowToolbar = scrollY > binding.toolbar.getHeight();

                        // The new state of the toolbar differs from the previous state; update
                        // appbar and toolbar attributes.
                        if (isToolbarShown != shouldShowToolbar) {
                            isToolbarShown = shouldShowToolbar;

                            // Use shadow animator to add elevation if toolbar is shown
                            binding.appbar.setActivated(shouldShowToolbar);

                            // Show the plant name if toolbar is shown
                            binding.toolbarLayout.setTitleEnabled(shouldShowToolbar);
                        }
                    }
                }
        );

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_share){
                    createShareIntent();
                    return true;
                }
                return false;
            }
        });

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    private void navigateToGallery() {
        Plant plant = plantDetailViewModel.getPlant().getValue();
        if (plant!=null){
            NavDirections direction = PlantDetailFragmentDirections.Companion.actionPlantDetailFragmentToGalleryFragment(plant.getName());
            NavHostFragment.findNavController(this).navigate(direction);
        }
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    private void createShareIntent() {
        Plant plant = plantDetailViewModel.getPlant().getValue();
        String shareText  = (plant == null)? null : getString(R.string.share_text_plant, plant.getName());

        Intent shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
                .setText(shareText)
                .setType("text/plain")
                .createChooserIntent()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(shareIntent);
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private void hideAppBarFab(FloatingActionButton fab) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        FloatingActionButton.Behavior behavior = (FloatingActionButton.Behavior) params.getBehavior();
        behavior.setAutoHideEnabled(false);
        fab.hide();
    }

    public interface Callback {
        void add(@Nullable Plant plant);
    }
}
