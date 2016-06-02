package it.uniroma2.gestioneprogettiandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.tasks.GetProjectDetailsTask;

/**
 * Activity dell'applicazione che rappresenta la finestra di
 * visualizzazione di un progetto specifico.
 * In essa sono presenti tutti i dettagli del progetto e la lista dei 
 * dipendenti che hanno lavorato al progetto e le ore lavorate
 * per ciascun dipendente.
 *
*/
public class ShowProjectActivity extends AppCompatActivity {

    /**
     * Tramite questo metodo verrà caricato, all'avvio dell'activity, il
     * layout dell'interfaccia grafica.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);
    }

    /**
     * Questo metodo si occupa di avviare il task di recupero dei
     * dati dal server una volta ripresa l'esecuzione dell'applicazione.
     *
     * NB: Questo metodo viene richiamato anche alla prima
     * creazione dell'activity!
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        new GetProjectDetailsTask(ShowProjectActivity.this).execute(extras.getInt("PROJECT_ID"));
    }
}
