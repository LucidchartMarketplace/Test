package studyelephant.com.studyelephant.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.mikephil.charting.charts.HorizontalBarChart;
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

public class StudentFragment extends Fragment {

    private College college;

    private TextView undergraduate;

    private TextView men;
    private TextView women;

    private HorizontalBarChart white;
    private HorizontalBarChart black;
    private HorizontalBarChart hispanic;
    private HorizontalBarChart asian;
    private HorizontalBarChart non_resident;
    private HorizontalBarChart two_or_more;
    private HorizontalBarChart unknown;
    private HorizontalBarChart aian;
    private HorizontalBarChart nhpi;

    private TextView white_percentage;
    private TextView black_percentage;
    private TextView hispanic_percentage;
    private TextView asian_percentage;
    private TextView non_resident_percentage;
    private TextView two_or_more_percentage;
    private TextView unknown_percentage;
    private TextView aian_percentage;
    private TextView nhpi_percentage;

    public StudentFragment() {
        // Required empty public constructor
    }

    private void chartSetUp(HorizontalBarChart chart, float collegeStat, float max, int color) {
        List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0f, collegeStat));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(color);
        set.setDrawValues(false);
        BarData data = new BarData(set);
        data.setBarWidth(1f);

        chart.setData(data);
        chart.setMinimumHeight(40);
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
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        undergraduate = (TextView) view.findViewById(R.id.undergraduate);
        undergraduate.setText(NumberFormat.getNumberInstance().format(college.student_size));

        men = (TextView) view.findViewById(R.id.men);
        men.setText(NumberFormat.getPercentInstance().format(college.men));
        men.setTextColor(rgb(62,98,255));

        women = (TextView) view.findViewById(R.id.women);
        women.setText(NumberFormat.getPercentInstance().format(college.women));
        women.setTextColor(rgb(250,84,255));

        white= (HorizontalBarChart) view.findViewById(R.id.white);
        chartSetUp(white, college.diversity_white, 1f,Color.GREEN);
        white_percentage = (TextView) view.findViewById(R.id.white_percentage);
        white_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_white)+ " White");

        black = (HorizontalBarChart) view.findViewById(R.id.black);
        chartSetUp(black, college.diversity_black, 1f,Color.GREEN);
        black_percentage = (TextView) view.findViewById(R.id.black_percentage);
        black_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_black)+ " Black");

        hispanic = (HorizontalBarChart) view.findViewById(R.id.hispanic);
        chartSetUp(hispanic, college.diversity_hispanic, 1f,Color.GREEN);
        hispanic_percentage = (TextView) view.findViewById(R.id.hispanic_percentage);
        hispanic_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_hispanic)+ " Hispanic");

        asian = (HorizontalBarChart) view.findViewById(R.id.asian);
        chartSetUp(asian, college.diversity_asian, 1f,Color.GREEN);
        asian_percentage = (TextView) view.findViewById(R.id.asian_percentage);
        asian_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_asian)+ " Asian");

        non_resident = (HorizontalBarChart) view.findViewById(R.id.non_resident);
        chartSetUp(non_resident, college.diversity_non_resident, 1f,Color.GREEN);
        non_resident_percentage = (TextView) view.findViewById(R.id.non_resident_percentage);
        non_resident_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_non_resident)+ " Non-Resident alien");

        unknown = (HorizontalBarChart) view.findViewById(R.id.unknown);
        chartSetUp(unknown, college.diversity_unknown, 1f,Color.GREEN);
        unknown_percentage = (TextView) view.findViewById(R.id.unknown_percentage);
        unknown_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_unknown)+ " Unknown");

        two_or_more = (HorizontalBarChart) view.findViewById(R.id.two_or_more);
        chartSetUp(two_or_more, college.diversity_two, 1f,Color.GREEN);
        two_or_more_percentage = (TextView) view.findViewById(R.id.two_or_more_percentage);
        two_or_more_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_two)+ " Two or more races");

        aian = (HorizontalBarChart) view.findViewById(R.id.aian);
        chartSetUp(aian, college.diversity_aian, 1f,Color.GREEN);
        aian_percentage = (TextView) view.findViewById(R.id.aian_percentage);
        aian_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_aian)+ " American Native/Alaska Native");

        nhpi = (HorizontalBarChart) view.findViewById(R.id.nhpi);
        chartSetUp(nhpi, college.diversity_nhpi, 1f,Color.GREEN);
        nhpi_percentage = (TextView) view.findViewById(R.id.nhpi_percentage);
        nhpi_percentage.setText( NumberFormat.getPercentInstance().format(college.diversity_nhpi)+ " Native Hawaiian/Pacific Islander");
        return view;
    }
}