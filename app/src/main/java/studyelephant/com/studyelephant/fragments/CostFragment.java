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

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import studyelephant.com.studyelephant.R;
import studyelephant.com.studyelephant.model.College;

import static android.graphics.Color.rgb;

/**
 * Created by Zhihan on 10/17/2017.
 */

public class CostFragment extends Fragment{

    private College college;
    private BarChart chart;
    private TextView cost;
    private TextView $0_300000;
    private TextView $30001_48000;
    private TextView $48001_75000;
    private TextView $75001_110000;
    private TextView $110001_plus;

    public CostFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_cost, container, false);

        this.chart = (BarChart) view.findViewById(R.id.annual_cost_chart);
        this.cost = (TextView)  view.findViewById(R.id.cost);
        this.cost.setText("$" + NumberFormat.getNumberInstance().format(college.annual_cost));

        this.$0_300000 = (TextView) view.findViewById(R.id.$0_30000);
        this.$0_300000.setText("$" + NumberFormat.getNumberInstance().format(college.cost_0_30000));
        this.$30001_48000 = (TextView) view.findViewById(R.id.$30001_48000);
        this.$30001_48000.setText("$" + NumberFormat.getNumberInstance().format(college.cost_30001_48000));
        this.$48001_75000 = (TextView) view.findViewById(R.id.$48001_75000);
        this.$48001_75000.setText("$" + NumberFormat.getNumberInstance().format(college.cost_48001_75000));
        this.$75001_110000 = (TextView) view.findViewById(R.id.$75001_110000);
        this.$75001_110000.setText("$" + NumberFormat.getNumberInstance().format(college.cost_75001_110000));
        this.$110001_plus = (TextView) view.findViewById(R.id.$110000_plus);
        this.$110001_plus.setText("$" + NumberFormat.getNumberInstance().format(college.cost_1100001_plus));

        List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0f, (float) college.annual_cost));

        BarDataSet set = new BarDataSet(entries, "");
        set.setColor(rgb(133,203,207));
        set.setDrawValues(false);
        BarData data = new BarData(set);
        data.setBarWidth(1f);

        LimitLine ll = new LimitLine(16300f, "");
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
        chart.getAxisLeft().setAxisMaximum(80000);
        chart.getAxisLeft().setDrawTopYLabelEntry(false);
        chart.getAxisLeft().addLimitLine(ll);
        chart.invalidate();

        return view;
    }
}
