package studyelephant.com.studyelephant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SchoolInfoActivity extends AppCompatActivity {

    private View annual_cost_view = findViewById(R.id.edit_cost);
    private View earning_view = findViewById(R.id.earning_edit);
    private View grad_rate_view = findViewById(R.id.grad_edit);
    private View web_view = findViewById(R.id.web_link_edit);
    private View admin_rate_view = findViewById(R.id.admin_rate_edit);
    private View sat_edit = findViewById(R.id.sat_avg_edit);
    private View num_students_view = findViewById(R.id.num_student_edit);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);
    }
}
