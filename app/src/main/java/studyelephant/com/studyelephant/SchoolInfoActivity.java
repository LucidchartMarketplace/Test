package studyelephant.com.studyelephant;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import studyelephant.com.studyelephant.asynctask.CollegeScoreCardTask;
import studyelephant.com.studyelephant.model.College;

import static android.graphics.Color.rgb;

public class SchoolInfoActivity extends AppCompatActivity {

    private String Data;
    private RecyclerView rv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);
        rv = (RecyclerView)findViewById(R.id.rv);
        Bundle bundle = getIntent().getExtras();

        List<College> colleges = new ArrayList<>();

        if (bundle.getString("Data") != null) {
            Data = bundle.getString("Data");
            try {
                JSONObject jsonObject = new JSONObject(Data);
                JSONObject metadata = jsonObject.getJSONObject("metadata");
                JSONArray results = jsonObject.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    colleges.add(new College(results.getJSONObject(i)));
                }
            }
            catch (JSONException e) {
                System.out.println(e.getMessage());
            }
        }

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        RVAdapter adapter = new RVAdapter(colleges);
        rv.setAdapter(adapter);
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CollegeViewHolder> {
        List<College> colleges;

        public RVAdapter(List<College> colleges) {
            this.colleges = colleges;
        }

        public class CollegeViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
            CardView cv;
            TextView name;
            TextView location;
            TextView student_size;
            TextView cost_of_attendance;
            TextView graduation_rate;
            TextView salary_after_attending;
            TextView cost_of_attendance_;
            TextView graduation_rate_;
            TextView salary_after_attending_;
            BarChart completion_chart;
            BarChart annual_cost_chart;
            BarChart earning_chart;

            public CollegeViewHolder(View itemView) {
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.cv);
                name = (TextView) itemView.findViewById(R.id.name);
                location = (TextView) itemView.findViewById(R.id.location);
                student_size = (TextView) itemView.findViewById(R.id.student_size);
                cost_of_attendance = (TextView) itemView.findViewById(R.id.cost_of_attendance);
                graduation_rate = (TextView) itemView.findViewById(R.id.graduation_rate);
                salary_after_attending = (TextView) itemView.findViewById(R.id.salary_after_attending);
                cost_of_attendance_ = (TextView) itemView.findViewById(R.id.cost_of_attendance_);
                graduation_rate_ = (TextView) itemView.findViewById(R.id.graduation_rate_);
                salary_after_attending_ = (TextView) itemView.findViewById(R.id.salary_after_attending_);
                completion_chart = (BarChart) itemView.findViewById(R.id.completion_chart);
                annual_cost_chart = (BarChart) itemView.findViewById(R.id.annual_cost_chart);
                earning_chart = (BarChart) itemView.findViewById(R.id.earning_chart);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                Context context = getApplication().getApplicationContext();
                Intent intent = new Intent(context, CollegeDetailActivity.class);
                intent.putExtra("Data",colleges.get(getAdapterPosition()).toString());
                context.startActivity(intent);
//                getAdapterPosition();
//                String school_name = ((TextView)view.findViewById(R.id.name)).getText().toString().replace(" ", "+");
//                new CollegeDetailTask("school.name,school.city,school.state,2015.student.size,2015.cost.avg_net_price.private,2015.cost.avg_net_price.public,2015.cost.avg_net_price.overall," +
//                        ""
//                        , "school.name=" + school_name).execute();
            }
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
            chart.setMinimumHeight(150);
            chart.setExtraTopOffset(80f);
            chart.setExtraBottomOffset(20f);
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
        public CollegeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_college_info, parent, false);
            CollegeViewHolder cvh = new CollegeViewHolder(v);
            return cvh;
        }

        @Override
        public void onBindViewHolder(CollegeViewHolder holder, int position) {
            College college = colleges.get(position);
            holder.name.setText(college.name);
            holder.location.setText(college.city + " " + college.state);
            holder.student_size.setText(NumberFormat.getNumberInstance().format(college.student_size) + " undergraduates");
            holder.cost_of_attendance.setText("Cost of Attendance");
            holder.graduation_rate.setText("Graduation Rate");
            holder.salary_after_attending.setText("Salary After Attending");
            NumberFormat format = NumberFormat.getCurrencyInstance();
            NumberFormat percentageFormat = NumberFormat.getPercentInstance();
            holder.cost_of_attendance_.setText(format.format(college.annual_cost));
            holder.graduation_rate_.setText(percentageFormat.format(college.completion_rate));
            holder.salary_after_attending_.setText(format.format(college.earning));
            chartSetUp(holder.completion_chart, (float) college.completion_rate, 0.67f, 1, rgb(57,132,182));
            chartSetUp(holder.annual_cost_chart, (float) college.annual_cost, 19600f, 80000, rgb(133,203,207));
            chartSetUp(holder.earning_chart, (float) college.earning, 43000f, 100000, rgb(29,46,129));
        }

        @Override
        public int getItemCount() {
            return colleges.size();
        }
    }
}
