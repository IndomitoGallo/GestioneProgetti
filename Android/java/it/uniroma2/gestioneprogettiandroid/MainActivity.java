package it.uniroma2.gestioneprogettiandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.gestioneprogettiandroid.domain.Project;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
          @TODO
          La lista seguente va sostituita con quella che andremo a ritirare tramite le API REST
         */
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(0, "Progetto1"));
        projects.add(new Project(1, "Progetto2"));
        projects.add(new Project(2, "Progetto3"));

        ListView listView = (ListView) findViewById(R.id.main_listView); // prendo la listView definita in activity_main

        /* Applico il nostro adapter che andrà a popolare magicamente la listView (solo dio sa come),
           usando, per ciascuna riga, il layout dichiarato in project.xml.
         */
        listView.setAdapter(new ProjectAdapter(this, R.layout.project, projects));
    }

    private class ProjectAdapter extends ArrayAdapter<Project> {
        // Questo oggetto inietta un layout XML dentro una view
        private LayoutInflater layoutInflater;

        public ProjectAdapter(Context context, int resource, List<Project> objects) {
            super(context, resource, objects);

            // inizializzo sto coso
            layoutInflater = LayoutInflater.from(context);
        }

        /* Questo metodo restituisce l'elemento della lista nella posizione specificata */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.project, null); // inietto un elemento della lista nella view
            Project project = getItem(position);

            TextView projectView = (TextView) view.findViewById(R.id.project_projectName);
            Button showButton = (Button) view.findViewById(R.id.project_showButton);

            showButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* @TODO
                     È da capire come passare i risultati alla nuova activity
                     */
                    startActivity(new Intent(MainActivity.this, ShowProjectActivity.class));
                }
            });

            projectView.setText(project.getName());

            return view;
        }
    }


}
