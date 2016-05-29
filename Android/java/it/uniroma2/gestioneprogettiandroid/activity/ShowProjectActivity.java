package it.uniroma2.gestioneprogettiandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.adapters.EmployeeOnProjectAdapter;
import it.uniroma2.gestioneprogettiandroid.domain.EmployeeOnProject;
import it.uniroma2.gestioneprogettiandroid.tasks.GetProjectDetailsTask;

public class ShowProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        new GetProjectDetailsTask(ShowProjectActivity.this).execute(extras.getInt("PROJECT_ID"));
    }
}
