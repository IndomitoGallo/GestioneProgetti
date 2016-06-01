package it.uniroma2.gestioneprogettiandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.activity.ShowProjectActivity;
import it.uniroma2.gestioneprogettiandroid.server.IProjectServer;
import it.uniroma2.gestioneprogettiandroid.server.ISessionServer;
import it.uniroma2.gestioneprogettiandroid.domain.Project;

public class ProjectAdapter extends ArrayAdapter<Project> {
    private final ISessionServer sessionServer;
    private final IProjectServer projectServer;
    // Questo oggetto inietta un layout XML dentro una view
    private LayoutInflater layoutInflater;

    public ProjectAdapter(Activity activity, int resource, List<Project> objects) {
        super(activity, resource, objects);

        MainContext mainContext = ((MainContext) activity.getApplication());

        layoutInflater = LayoutInflater.from(activity);
        this.sessionServer = mainContext.getSessionServer();
        this.projectServer = mainContext.getProjectServer();
    }

    /* Questo metodo restituisce l'elemento della lista nella posizione specificata */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.project, null); // inietto un elemento della lista nella view
        final Project project = getItem(position);

        TextView projectView = (TextView) view.findViewById(R.id.project_projectName);
        Button showButton = (Button) view.findViewById(R.id.project_showButton);

        projectView.setText(project.getName());

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ShowProjectActivity.class);
                intent.putExtra("PROJECT_ID", project.getId());

                context.startActivity(intent);
            }
        });

        return view;
    }
}