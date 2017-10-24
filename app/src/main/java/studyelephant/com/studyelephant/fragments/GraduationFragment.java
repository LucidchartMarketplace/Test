package studyelephant.com.studyelephant.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import studyelephant.com.studyelephant.R;
import studyelephant.com.studyelephant.model.College;

import static android.graphics.Color.rgb;

/**
 * Created by Zhihan on 10/17/2017.
 */

public class GraduationFragment extends Fragment {

    private College college;
    private BarChart retention_rate_chart;
    private TextView retention;

    public GraduationFragment() {
        // Required empty public constructor
    }

    private void chartSetUp(BarChart chart, float collegeStat, float nationalAverage, float max, int color) {
        List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0f, collegeStat));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(color);
        set.setDrawValues(false);
        BarData data = new BarData(set);
        data.setBarWidth(1f);

        LimitLine ll = new LimitLine(nationalAverage, "");
        ll.setLineWidth(2f);
        ll.setLineColor(rgb(0, 0, 0));

        chart.setData(data);
        chart.setMinimumHeight(120);
        chart.setExtraTopOffset(80f);
        chart.setExtraBottomOffset(10f);
        chart.setDrawBorders(true);
        chart.setTouchEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setEnabled(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(max);
        chart.getAxisLeft().setDrawTopYLabelEntry(false);
        chart.getAxisLeft().addLimitLine(ll);
        chart.invalidate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras != null) {
            Gson gson = new Gson();
            college = gson.fromJson(extras.getString("Data"), College.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graduation, container, false);

        this.retention_rate_chart = (BarChart) view.findViewById(R.id.earning_);

        chartSetUp(retention_rate_chart, (float) college.earning, 34100f, 100000, rgb(29,46,129));

        this.retention = (TextView) view.findViewById(R.id.earning);
        this.retention.setText("$" + NumberFormat.getNumberInstance().format(college.earning));
        return view;
    }
}