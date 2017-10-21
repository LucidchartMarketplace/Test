package studyelephant.com.studyelephant.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import studyelephant.com.studyelephant.R;
import studyelephant.com.studyelephant.model.College;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.rgb;

/**
 * Created by Zhihan on 10/17/2017.
 */

public class StudentFragment extends Fragment {

    private College college;
    private HorizontalBarChart diversity;

    public StudentFragment() {
        // Required empty public constructor
    }


    private BarDataSet getDataSet() {

        ArrayList<BarEntry> entries = new ArrayList();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries,"hi");
        return dataset;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        return labels;
    }

    private PieData getData() {
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(college.diversity_white, "White"));
        entries.add(new PieEntry(college.diversity_black, "Black"));
        entries.add(new PieEntry(college.diversity_asian, "Asian"));
        entries.add(new PieEntry(college.diversity_non_resident, "Non-resident alien"));
        entries.add(new PieEntry(college.diversity_two, "Two or more races"));
        entries.add(new PieEntry(college.diversity_unknown, "Unknown"));
        entries.add(new PieEntry(college.diversity_aian, "American Indian/Alaska Native"));
        entries.add(new PieEntry(college.diversity_nhpi, "Native Hawaiian/Pacific Islander"));

        PieDataSet dataSet = new PieDataSet(entries, "Race/Ethnicity");
        dataSet.setValueTextColor(Color.BLACK);
        PieData data = new PieData(dataSet);

        data.setValueTextColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        return data;
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
        // Inflate the layout for this fragment
        diversity = (HorizontalBarChart) view.findViewById(R.id.diversity);
        BarData data = new BarData(getDataSet());
        diversity.setData(data);
        diversity.animateXY(2000, 2000);
        diversity.invalidate();
        diversity.invalidate();
        return view;
    }
}