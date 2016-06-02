package it.uniroma2.gestioneprogettiandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.tasks.GetProjectsTask;
import it.uniroma2.gestioneprogettiandroid.tasks.LogoutTask;

/**
 * Activity dell'applicazione che rappresenta la finestra principale
 * una volta effettuato il login.
 * In essa sono presenti un campo indicante il nome del progetto, il pulsante
 * di logout, il pulsante di refresh e la lista dei progetti. Ciascun elemento
 * della lista avrà un campo indicante il nome del progetto e
 * un pulsante "visualizza".
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Tramite questo metodo verrà caricato, all'avvio dell'activity, il
     * layout dell'interfaccia principale e verranno creati dei listener
     * sui pulsanti di refresh e logout.
     * I due listener attiveranno il proprio task una volta
     * cliccato il rispettivo pulsante.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.main_listView);
        Button logoutButton = (Button) findViewById(R.id.main_logoutButton);
        Button refreshButton = (Button) findViewById(R.id.main_refreshButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutTask(MainActivity.this).execute();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetProjectsTask(MainActivity.this).execute();
            }
        });
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

        new GetProjectsTask(MainActivity.this).execute();
    }

}
