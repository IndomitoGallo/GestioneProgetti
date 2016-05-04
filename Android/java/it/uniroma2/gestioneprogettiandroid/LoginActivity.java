package it.uniroma2.gestioneprogettiandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Prendo il pulsante presente nell'activity_login
        Button loginButton = (Button) findViewById(R.id.login_loginButton);
        Spinner profileSpinner = (Spinner) findViewById(R.id.login_profileSpinner);

        /*
         @TODO
         Da capire come utilizzare questo menu a tendina
         */
        /* Popolo il menu a tendina con i profili definiti in strings.xml (login_profiles) .*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.login_profiles, android.R.layout.simple_spinner_item);

        // Seleziono il layout da usare (uno di default)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Uso l'adapter
        profileSpinner.setAdapter(adapter);

        // Questo metodo imposta un evento per quando viene cliccato il pulsante
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                 @TODO
                 Da implementare i controlli
                */

                // Apre l'attività principale
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }
}
