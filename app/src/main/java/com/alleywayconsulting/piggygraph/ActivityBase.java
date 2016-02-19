package com.alleywayconsulting.piggygraph;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Michael Lake on 2/17/16.
 * Copyright (c) Alleyway Consulting, LLC
 */
public abstract class ActivityBase extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
