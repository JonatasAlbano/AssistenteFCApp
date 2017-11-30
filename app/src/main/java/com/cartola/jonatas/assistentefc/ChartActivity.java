package com.cartola.jonatas.assistentefc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // in this example, a LineChart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart);

        Description description = new Description();
        description.setText("Médias do jogador");
        chart.setDescription(description);
        List<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(1, 25));
        entries.add(new Entry(2, 23));
        entries.add(new Entry(3, 22));
        entries.add(new Entry(4, 30));

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setDrawFilled(true);

        final HashMap<Integer, String> labels = new HashMap<>();
        labels.put(1,"Label 1");
        labels.put(2,"Label 2");
        labels.put(3,"Label 3");
        labels.put(4,"Label 4");

        LineData lineData = new LineData(dataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int)value);
            }
        });
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

}
