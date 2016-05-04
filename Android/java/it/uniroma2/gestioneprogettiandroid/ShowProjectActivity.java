package it.uniroma2.gestioneprogettiandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.gestioneprogettiandroid.domain.Dipendente;

public class ShowProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);

        /*
         @TODO
          I seguenti campi vanno sostituiti con quelli ritirati tramite le API REST
         */
        ((TextView) findViewById(R.id.showProject_projectName)).setText("[Nome progetto]");
        ((TextView) findViewById(R.id.showProject_description)).setText("[Descrizione]");
        ((TextView) findViewById(R.id.showProject_budget)).setText("[Budget]");
        ((TextView) findViewById(R.id.showProject_status)).setText("[Status]");
        ((TextView) findViewById(R.id.showProject_cost)).setText("[Costo]");

        /*
          @TODO
          La lista seguente va sostituita con quella che andremo a ritirare tramite le API REST
         */
        List<Dipendente> dipendenti = new ArrayList<>();
        dipendenti.add(new Dipendente("Dipendente1", 4));
        dipendenti.add(new Dipendente("Dipendente2", 5));
        dipendenti.add(new Dipendente("Dipendente3", 7));

        ListView listView = (ListView) findViewById(R.id.showProject_dipendenti); // prendo la listView definita in activity_main

        /* Applico il nostro adapter che andrà a popolare magicamente la listView (solo dio sa come),
           usando, per ciascuna riga, il layout dichiarato in project.xml.
         */
        listView.setAdapter(new DipendenteAdapter(this, R.layout.dipendente, dipendenti));
    }

    private class DipendenteAdapter extends ArrayAdapter<Dipendente> {
        // Questo oggetto inietta un layout XML dentro una view
        private LayoutInflater layoutInflater;

        public DipendenteAdapter(Context context, int resource, List<Dipendente> objects) {
            super(context, resource, objects);

            // inizializzo sto coso
            layoutInflater = LayoutInflater.from(context);
        }

        /* Questo metodo restituisce l'elemento della lista nella posizione specificata */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.dipendente, null); // inietto un elemento della lista nella view
            Dipendente dipendente = getItem(position);

            TextView name = (TextView) view.findViewById(R.id.dipendente_name);
            TextView hours = (TextView) view.findViewById(R.id.dipendente_hours);

            name.setText(dipendente.getName());
            hours.setText(Integer.toString(dipendente.getHours())+ " ore");

            return view;
        }
    }
}
