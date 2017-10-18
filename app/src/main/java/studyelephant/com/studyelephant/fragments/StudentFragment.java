package studyelephant.com.studyelephant.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import studyelephant.com.studyelephant.R;
import studyelephant.com.studyelephant.model.College;

/**
 * Created by Zhihan on 10/17/2017.
 */

public class StudentFragment extends Fragment {

    private College college;

    public StudentFragment() {
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
        return inflater.inflate(R.layout.fragment_student, container, false);
    }
}