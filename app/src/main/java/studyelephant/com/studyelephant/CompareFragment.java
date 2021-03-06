package studyelephant.com.studyelephant;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhihan on 10/24/2017.
 */

public class CompareFragment extends DialogFragment {

    private UniSearchTask searchTask = null;
    private ProgressBar mProgressBar;
    private List<String> collegeNames;
    private AutoCompleteTextView mAutoCompleteTextView;
    private Button compareButton;
    private String college;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        collegeNames = new ArrayList<String>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.college_names);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                collegeNames.add(jsonArray.getString(i));
            }
        }
        catch (Exception e) {
            String s = e.getMessage();
            String s2 = e.getStackTrace().toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_compare, container, false);
        getDialog().setTitle("Compare with another college");

        college = getArguments().getString("college");

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        mAutoCompleteTextView = (AutoCompleteTextView) rootView.findViewById(R.id.school_name_input);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item ,this.collegeNames);
        mAutoCompleteTextView.setAdapter(adapter);

        compareButton = (Button) rootView.findViewById(R.id.compare_button);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String school_name = mAutoCompleteTextView.getText().toString().replace(" ", "+");
                searchTask = new UniSearchTask(school_name);
                searchTask.execute((Void) null);
            }
        });

        return rootView;
    }
    public class UniSearchTask extends AsyncTask<Void, Void, String> {

        private final String schoolName;

        public UniSearchTask(String schoolName) {
            this.schoolName = schoolName;
        }

        private void writeString(String str, OutputStream os) throws IOException {
            OutputStreamWriter sw = new OutputStreamWriter(os);
            sw.write(str);
            sw.flush();
        }

        private String readString(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            InputStreamReader sr = new InputStreamReader(is);
            char[] buf = new char[1024];
            int len;
            while ((len = sr.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            //TODO handle error message
            super.onPostExecute(s);
            Context context = getContext();
            Intent intent = new Intent(context, SchoolInfoActivity.class);
            intent.putExtra("Data",s);
            intent.putExtra("college", college);
            context.startActivity(intent);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("https://api.data.gov/ed/collegescorecard/v1/schools?api_key=6KVhOQ5Ay1MyNwcgTtLkTKfUUMUOpwf17ie5qz0t&fields=school.name,school.city,school.state,school.ownership,school.school_url,school.degrees_awarded.highest,2015.student.size,2015.student.retention_rate.four_year.full_time,2015.cost.avg_net_price.public,2015.cost.avg_net_price.private,2015.cost.avg_net_price.overall,id,2015.completion.completion_rate_4yr_150nt,2015.completion.completion_cohort_4yr_150nt,2015.cost.net_price.private.by_income_level.0-30000,2015.cost.net_price.private.by_income_level.30001-48000,2015.cost.net_price.private.by_income_level.48001-75000,2015.cost.net_price.private.by_income_level.75001-110000,2015.cost.net_price.private.by_income_level.110001-plus,2015.cost.net_price.public.by_income_level.0-30000,2015.cost.net_price.public.by_income_level.30001-48000,2015.cost.net_price.public.by_income_level.48001-75000,2015.cost.net_price.public.by_income_level.75001-110000,2015.cost.net_price.public.by_income_level.110001-plus,2013.earnings.10_yrs_after_entry.median,2015.student.demographics.race_ethnicity.white,2015.student.demographics.race_ethnicity.black,2015.student.demographics.race_ethnicity.hispanic,2015.student.demographics.race_ethnicity.asian,2015.student.demographics.race_ethnicity.aian,2015.student.demographics.race_ethnicity.nhpi,2015.student.demographics.race_ethnicity.two_or_more,2015.student.demographics.race_ethnicity.non_resident_alien,2015.student.demographics.race_ethnicity.unknown,2015.student.demographics.men,2015.student.demographics.women,2015.admissions.sat_scores.25th_percentile.critical_reading,2015.admissions.sat_scores.75th_percentile.critical_reading,2015.admissions.sat_scores.25th_percentile.math,2015.admissions.sat_scores.75th_percentile.math,2015.admissions.sat_scores.25th_percentile.writing,2015.admissions.sat_scores.75th_percentile.writing,2015.admissions.sat_scores.average.by_ope_id,2015.admissions.admission_rate.overall&school.name=" + schoolName);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setConnectTimeout(5000);
                http.setDoInput(true);
                http.addRequestProperty("Content-Type", "application/json");
                http.connect();


                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream respBody = http.getInputStream();
                    String respData = readString(respBody);

                    System.out.println(respData);
                    return respData;
                }
                else {
                    String errorInfo = http.getResponseMessage();
                    InputStream respBody = http.getInputStream();
                    String respData = readString(respBody);
                    errorInfo += "\n" + respData;
                    return errorInfo;
                }
            }
            catch (Exception e) {
                System.out.println(e.getStackTrace());
                return e.getMessage();
            }
        }
    }
}
