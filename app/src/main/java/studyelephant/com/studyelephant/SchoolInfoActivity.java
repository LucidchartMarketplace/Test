package studyelephant.com.studyelephant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SchoolInfoActivity extends AppCompatActivity {

    private String Data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);
        final TextView school_name_view = (TextView) findViewById(R.id.school_name);
        final TextView annual_cost_view = (TextView) findViewById(R.id.edit_cost);
        final TextView earning_view = (TextView) findViewById(R.id.earning_edit);
        final TextView grad_rate_view = (TextView) findViewById(R.id.grad_edit);
        final TextView web_view = (TextView) findViewById(R.id.web_link_edit);
        final TextView admin_rate_view = (TextView) findViewById(R.id.admin_rate_edit);
        final TextView sat_edit = (TextView) findViewById(R.id.sat_avg_edit);
        final TextView num_students_view = (TextView) findViewById(R.id.num_student_edit);
        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("Data") != null) {
            Data = bundle.getString("Data");
            try {
                JSONObject jsonObject = new JSONObject(Data);
                JSONObject metadata = jsonObject.getJSONObject("metadata");
                JSONArray results = jsonObject.getJSONArray("results");
                JSONObject college = results.getJSONObject(0);
                String school_url = college.getString("school.school_url");
                school_name_view.setText(college.getString("school.name"));
                college.getInt("school.degrees_awarded.highest");
                web_view.setText(college.getString("school.school_url"));
                college.getString("school.city");
                college.getString("school.state");
                double grad_rate = college.getDouble("2013.completion.completion_rate_4yr_150nt")*100;
                grad_rate_view.setText(Double.toString(grad_rate));
                annual_cost_view.setText(college.getString("2013.cost.attendance.academic_year"));
                earning_view.setText(college.getString("2013.earnings.10_yrs_after_entry.median"));
                sat_edit.setText(college.getString("2013.admissions.sat_scores.average.overall"));
                double admin_rate = college.getDouble("2013.admissions.admission_rate.overall") * 100;
                admin_rate_view.setText(Double.toString(admin_rate));
                num_students_view.setText(college.getString("2013.student.size"));
            }
            catch (JSONException e) {

            }
        }
    }
}
