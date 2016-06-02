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

import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.activity.ShowProjectActivity;
import it.uniroma2.gestioneprogettiandroid.domain.Project;

/**
 * Questa classe si occupa di popolare la ListView dei progetti del pm loggato.
 * Viene quindi aggiunto per ogni elemento il nome del progetto e il pulsante
 * di visualizzazione del singolo progetto.
 */
public class ProjectAdapter extends ArrayAdapter<Project> {
    private LayoutInflater layoutInflater;

    public ProjectAdapter(Activity activity, int resource, List<Project> objects) {
        super(activity, resource, objects);
        layoutInflater = LayoutInflater.from(activity);
    }

    /**
     * Per ogni progetto che viene passato a questo adapter viene generata
     * una view che rappresenta la riga con il nome del progetto e
     * un pulsante "visualizza".
     * Inoltre, per ogni pulsante viene registrato un listener.
     * Una volta cliccato uno dei pulsanti, il rispettivo listener lancerà 
     * l’activity ShowProjectActivity passandogli come parametro extra l’id
     * del progetto.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.project, null);
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
