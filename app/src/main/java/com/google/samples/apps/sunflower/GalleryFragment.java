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
import androidx.navigation.Navigation;
import androidx.paging.PagingData;

import com.google.samples.apps.sunflower.adapters.GalleryAdapter;
import com.google.samples.apps.sunflower.data.net.pojo.UnsplashPhoto;
import com.google.samples.apps.sunflower.databinding.FragmentGalleryBinding;
import com.google.samples.apps.sunflower.viewmodels.GalleryViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public final class GalleryFragment extends Fragment {
    private GalleryAdapter adapter ;
    private GalleryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGalleryBinding binding = FragmentGalleryBinding.inflate(inflater,container,false);
        if(getContext() == null){
            return binding.getRoot();
        }
        adapter = new GalleryAdapter();
        viewModel = new ViewModelProvider(requireActivity()).get(GalleryViewModel.class);
        binding.photoList.setAdapter(adapter);
        search(GalleryFragmentArgs.fromBundle(getArguments()).getPlantName());

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });


        return binding.getRoot();
    }



    private void search(String query ) {
            viewModel.searchPictures(query).observe(getViewLifecycleOwner(), new Observer<PagingData<UnsplashPhoto>>() {
                @Override
                public void onChanged(PagingData<UnsplashPhoto> unsplashPhotoPagingData) {
                    adapter.submitData(getLifecycle(),unsplashPhotoPagingData);
                }
            });


    }
}
