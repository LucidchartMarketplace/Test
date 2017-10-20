package studyelephant.com.studyelephant;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.List;

import studyelephant.com.studyelephant.model.College;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UniSearchTask searchTask = null;
    private ProgressBar mProgressBar;
    private List<String> collegeNames;
    private AutoCompleteTextView mAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        collegeNames = new ArrayList<String>();

        new CollegeNamesTask().execute();

        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.school_name_input);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_item ,this.collegeNames);
//        mAutoCompleteTextView.setAdapter(adapter);

        Button search_button = (Button) findViewById(R.id.search_start_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String school_name = mAutoCompleteTextView.getText().toString().trim().replace(" ","+");
                if (school_name.isEmpty() || school_name.length() == 0 || school_name.equals("") || school_name == null) {
                    //add things for invalid school name
                    Log.d("School name", "invalid");
                }else{
                    Log.d("school name is: " , school_name);
                    mProgressBar.setVisibility(View.VISIBLE);
                    searchSchool(school_name);
                }
            }
        });

    }

    public void searchSchool(String school_name){
        // fire to the server and get the information to create the next activity
        searchTask = new UniSearchTask(school_name);
        searchTask.execute((Void) null);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public class CollegeNamesTask extends AsyncTask<Void, Void, String> {

        int total = -1;
        int per_page = -1;
        int current_page = 0;
        int total_page = -1;

        public CollegeNamesTask() {

        }

        public CollegeNamesTask(int total, int per_page, int current_page, int total_page) {
            this.total = total;
            this.per_page = per_page;
            this.current_page = current_page;
            this.total_page = total_page;
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
        protected String doInBackground(Void... params) {
            //android.os.Debug.waitForDebugger();
            try {
                URL url = new URL("https://api.data.gov/ed/collegescorecard/v1/schools?api_key=6KVhOQ5Ay1MyNwcgTtLkTKfUUMUOpwf17ie5qz0t&fields=school.name&page=" + current_page);

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
                    return "error";
                }
            }
            catch (Exception e) {
                System.out.println(e.getStackTrace());
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (!s.equals("error")) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (total_page == -1) {
                        JSONObject metadata = jsonObject.getJSONObject("metadata");
                        this.total = metadata.getInt("total");
                        this.per_page = metadata.getInt("per_page");
                        this.current_page = metadata.getInt("page");
                        this.total_page = total / per_page;
                        if (total_page % per_page > 0)
                            total_page++;
                        total_page--;
                    }
                    JSONArray results = jsonObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        collegeNames.add(results.getJSONObject(i).getString("school.name"));
                    }

                    if (current_page < total_page) {
                        new CollegeNamesTask(total, per_page, ++current_page, total_page).execute();
                    }
                }
                catch (JSONException e) {

                }
            }
        }
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
            Context context = getApplication().getApplicationContext();
            Intent intent = new Intent(context, SchoolInfoActivity.class);
            intent.putExtra("Data",s);
            context.startActivity(intent);
        }

        @Override
        protected String doInBackground(Void... params) {
            //android.os.Debug.waitForDebugger();
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
