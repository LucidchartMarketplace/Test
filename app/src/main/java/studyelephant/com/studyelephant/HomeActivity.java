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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UniSearchTask searchTask = null;

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



        final EditText school_name_text = (EditText) findViewById(R.id.school_name_input);
        Button search_button = (Button) findViewById(R.id.search_start_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String school_name = school_name_text.getText().toString().trim();
                if (school_name.isEmpty() || school_name.length() == 0 || school_name.equals("") || school_name == null) {
                    //add things for invalid school name
                    Log.d("School name", "invalid");
                }else{
                    Log.d("school name is: " , school_name);
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
            super.onPostExecute(s);
            Context context = getApplication().getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, s, duration).show();
            System.out.println(s);
            Intent intent = new Intent(context, SchoolInfoActivity.class);
            context.startActivity(intent);
        }

        @Override
        protected String doInBackground(Void... params) {

            android.os.Debug.waitForDebugger();

            try {
                URL url = new URL("https://api.data.gov/ed/collegescorecard/v1/schools?api_key=Cq3vWyIcpAPjs9ri4s1bi0TeLuk2Sv77qvmKj7sI&fields=school.name,school.degrees_awarded.highest,school.school_url,school.city,school.state,school.degree_urbanization,2013.completion.completion_rate_4yr_150nt,2013.cost.attendance.academic_year,2013.earnings.10_yrs_after_entry.median");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                http.setConnectTimeout(5000);
                http.setRequestMethod("GET");
                http.setDoOutput(true);
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
