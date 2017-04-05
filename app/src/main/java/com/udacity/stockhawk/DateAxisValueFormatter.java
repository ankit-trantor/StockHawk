package com.udacity.stockhawk;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sarabjeet on 28/3/17.
 */

public class DateAxisValueFormatter implements IAxisValueFormatter {

    List<Date> dateList;

    public DateAxisValueFormatter(List<Date> dateList) {
        this.dateList = dateList;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dateList.get(7 - (int) value));
    }
}
