package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Sarabjeet Singh on 09-03-2017.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart chart;
    private String[] STOCK_DATA_COLUMN = {Contract.Quote.COLUMN_HISTORY};
    private String stockHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String symbol = getIntent().getStringExtra("Symbol");

        Cursor cursor = getContentResolver().query(Contract.Quote.makeUriForStock(symbol),
                STOCK_DATA_COLUMN,
                null,
                null,
                null);

        try {
            if (cursor.moveToFirst()) {
                stockHistory = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
                Timber.d(stockHistory);
            }
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        List<Entry> entries = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(stockHistory, ",\n");
        float dateInterval = 10f;
        while (tokens.hasMoreTokens() && dateInterval >= 1) {

            Long dateInMillis = Long.parseLong(tokens.nextToken());
            Date date = new Date(dateInMillis);
            float value = Float.parseFloat(tokens.nextToken());

            Entry entry = new Entry(dateInterval, value);
            entries.add(entry);
            dateInterval--;
        }
        List<Entry> entryList = new ArrayList<>();
        for (int i = 9; i >= 0; i--) {
            entryList.add(entries.get(i));
        }

        LineDataSet dataSet = new LineDataSet(entryList, "Date");
        LineData data = new LineData(dataSet);
        chart.setData(data);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.animateX(1000, Easing.EasingOption.EaseInBack);
        chart.invalidate();
        //TODO: Add labels for dates and style the chart
    }
}


