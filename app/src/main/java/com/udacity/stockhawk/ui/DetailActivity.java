package com.udacity.stockhawk.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.DateAxisValueFormatter;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sarabjeet Singh on 09-03-2017.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.stock_name_textView)
    TextView nameTextView;
    @BindView(R.id.symbol_textView)
    TextView symbolTextView;
    @BindView(R.id.price_textView)
    TextView priceTextView;
    @BindView(R.id.change_textView)
    TextView changeTextView;
    private String[] STOCK_DATA_COLUMN = {Contract.Quote.COLUMN_PRICE, Contract.Quote.COLUMN_NAME,
            Contract.Quote.COLUMN_ABSOLUTE_CHANGE,
            Contract.Quote.COLUMN_PERCENTAGE_CHANGE, Contract.Quote.COLUMN_HISTORY};
    private String stockHistory;
    private String name;
    private String price;
    private String absChange;
    private String percentChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String symbol = getIntent().getStringExtra(getString(R.string.intent_extra_symbol));

        if (null != toolbar) {
            this.setSupportActionBar(toolbar);
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Cursor cursor = getContentResolver().query(Contract.Quote.makeUriForStock(symbol),
                STOCK_DATA_COLUMN,
                null,
                null,
                null);

        try {
            if (cursor.moveToFirst()) {
                price = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_PRICE));
                name = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_NAME));
                absChange = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
                percentChange = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE));
                stockHistory = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
            }
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String symbolType = "NASDAQ: " + symbol;

        nameTextView.setText(name);
        nameTextView.setContentDescription(name);
        symbolTextView.setText(symbolType);
        symbolTextView.setContentDescription(symbol);
        priceTextView.setText(price);
        priceTextView.setContentDescription(price);
        if (Float.parseFloat(absChange) > 0) {
            changeTextView.setText("+" + absChange + "(+" + percentChange + "%)");
            changeTextView.setTextColor(ContextCompat.getColor(this, R.color.material_green_700));
        } else {
            changeTextView.setText(absChange + "(" + percentChange + "%)");
            changeTextView.setTextColor(ContextCompat.getColor(this, R.color.material_red_700));
        }

        changeTextView.setContentDescription(getString(R.string.price_change_content_desc));
        List<Entry> entries = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(stockHistory, ",\n");
        float dateInterval = 7f;
        while (tokens.hasMoreTokens() && dateInterval >= 1) {

            Long dateInMillis = Long.parseLong(tokens.nextToken());
            Date date = new Date(dateInMillis);
            float value = Float.parseFloat(tokens.nextToken());
            dateList.add(date);
            Entry entry = new Entry(dateInterval, value);
            entries.add(entry);
            dateInterval--;
        }
        List<Entry> entryList = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            entryList.add(entries.get(i));
        }

        LineDataSet dataSet = new LineDataSet(entryList, "Date");
        LineData data = new LineData(dataSet);
        chart.setData(data);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DateAxisValueFormatter(dateList));
        chart.animateX(1000, Easing.EasingOption.EaseInBack);
        chart.invalidate();
        xAxis.setLabelRotationAngle(315);
    }
}


