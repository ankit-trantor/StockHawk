package com.udacity.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.udacity.stockhawk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sarabjeet Singh on 09-03-2017.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }


}
