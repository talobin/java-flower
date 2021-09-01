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

import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.samples.apps.sunflower.R;

public class PlantDetailBindingAdapters {
    @BindingAdapter("imageFromUrl")
    public static void bindImageFromUrl(ImageView view, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view);
        }
    }

    @BindingAdapter("isFabGone")
    public static void bindIsFabGone(FloatingActionButton view, Boolean isGone) {
        if (isGone == null || isGone) {
            view.hide();
        } else {
            view.show();
        }
    }

    @BindingAdapter("renderHtml")
    public static void bindReaderHtml(TextView view,@Nullable String description) {
        if (description == null) {
            view.setText("");
        } else {
            view.setText(HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT));
            view.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @BindingAdapter("wateringText")
    public static void bindWateringText(TextView textView, int wateringInterval) {
        Resources res = textView.getContext().getResources();
        String quantityString = res.getQuantityString(R.plurals.watering_needs_suffix, wateringInterval, wateringInterval);

        textView.setText(quantityString);
    }
}
