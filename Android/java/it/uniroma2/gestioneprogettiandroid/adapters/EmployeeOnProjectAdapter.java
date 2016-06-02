package it.uniroma2.gestioneprogettiandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.domain.EmployeeOnProject;

/**
 * Questa classe si occupa di popolare la ListView dei dipendenti
 * presenti in un progetto.
 * Viene quindi aggiunto per ogni elemento il nome e cognome del dipendente e
 * le ore lavorate sul progetto.
*/
public class EmployeeOnProjectAdapter extends ArrayAdapter<EmployeeOnProject> {

    private LayoutInflater layoutInflater;

    public EmployeeOnProjectAdapter(Context context, int resource, List<EmployeeOnProject> objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
    }

   /**
    * Per ogni dipendente passato a questo adapter viene generata una view
    * che rappresenta la riga con il nome, cognome e numero di ore.
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.dipendente, null); 
        EmployeeOnProject employeeOnProject = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.dipendente_name);
        TextView hours = (TextView) view.findViewById(R.id.dipendente_hours);

        name.setText(employeeOnProject.getEmployee().getName() + " " + employeeOnProject.getEmployee().getSurname());
        hours.setText(Integer.toString(employeeOnProject.getHours()) + " ore");

        return view;
    }
}
