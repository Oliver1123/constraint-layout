// Copyright 2016 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.googleio

import android.app.Activity
import android.os.Bundle

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet

import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_done.*
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator


class MainActivity : AppCompatActivity() {
    private var showComponents = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_done)

        upload.setOnClickListener {
//            favoriteIconAnimation(showComponents)
            triggerComponentsAnimation(showComponents)
            showComponents = !showComponents
        }

    }

    private fun favoriteIconAnimation(show: Boolean) {
        val set = ConstraintSet()

        // copy constraints settings from current ConstraintLayout to set
        set.clone(activity_main_done)

        // change constraints settings
        set.clear(R.id.favorite, ConstraintSet.RIGHT)
        set.clear(R.id.favorite, ConstraintSet.LEFT)
        if (show) {
            set.connect(R.id.favorite, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        } else {
            set.connect(R.id.favorite, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        }
        // enable animation
        TransitionManager.beginDelayedTransition(activity_main_done)

        // apply constraints settings from set to current ConstraintLayout
        set.applyTo(activity_main_done)
    }

    private fun triggerComponentsAnimation(showComponents: Boolean){
        val constraint = findViewById<ConstraintLayout>(R.id.activity_main_done)
        val constraintSet = ConstraintSet()
        val layoutID = if (showComponents)  R.layout.activity_main_done else R.layout.activity_main_done_ext
        constraintSet.clone(this, layoutID)

        val transition = ChangeBounds()
        transition.interpolator = OvershootInterpolator(1.0f)
        transition.duration = 750

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }
}
