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

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.data.net.pojo.UnsplashPhoto;
import com.google.samples.apps.sunflower.databinding.ListItemPhotoBinding;

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
public class GalleryAdapter extends PagingDataAdapter<UnsplashPhoto, GalleryAdapter.GalleryViewHolder> {


    public GalleryAdapter() {
        super(new GalleryAdapter.GalleryDiffCallback());
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryViewHolder(
                ListItemPhotoBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        UnsplashPhoto photo = getItem(position);
        if (photo != null) {
            holder.bind(photo);
        }
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        private ListItemPhotoBinding binding;

        GalleryViewHolder(@NonNull ListItemPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UnsplashPhoto photo = binding.getPhoto();
                    if (photo != null) {
                        Uri uri = Uri.parse(photo.getUser().getAttributionUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }


        void bind(UnsplashPhoto item) {
            binding.setPhoto(item);
            binding.executePendingBindings();
        }
    }

    static class GalleryDiffCallback extends DiffUtil.ItemCallback<UnsplashPhoto> {

        @Override
        public boolean areItemsTheSame(@NonNull UnsplashPhoto oldItem, @NonNull UnsplashPhoto newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull UnsplashPhoto oldItem, @NonNull UnsplashPhoto newItem) {
            return oldItem == newItem;
        }
    }


}
