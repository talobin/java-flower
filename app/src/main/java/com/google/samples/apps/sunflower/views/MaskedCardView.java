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

package com.google.samples.apps.sunflower.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.material.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

/**
 * A Card view that clips the content of any shape, this should be done upstream in card,
 * working around it for now.
 */
public class MaskedCardView extends MaterialCardView {
    Context context;
    AttributeSet attrs = null;
    int defStyle = R.attr.materialCardViewStyle;

    public MaskedCardView(Context context) {
        this(context, null);
    }

    public MaskedCardView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialCardViewStyle);
    }

    public MaskedCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyleAttr;
        this.shapeAppearance = ShapeAppearanceModel.builder(
                this.context,
                this.attrs,
                this.defStyle,
                R.style.Widget_MaterialComponents_CardView
        ).build();

    }

    @SuppressLint("RestrictedApi")
    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
    private final Path path = new Path();
    private final ShapeAppearanceModel shapeAppearance;

    private final RectF rectF = new RectF(0f, 0f, 0f, 0f);


    @Override
    public void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        rectF.right = w;
        rectF.bottom = h;
        pathProvider.calculatePath(shapeAppearance, 1f, rectF, path);
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
