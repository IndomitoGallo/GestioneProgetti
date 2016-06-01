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

public class EmployeeOnProjectAdapter extends ArrayAdapter<EmployeeOnProject> {
    // Questo oggetto inietta un layout XML dentro una view
    private LayoutInflater layoutInflater;

    public EmployeeOnProjectAdapter(Context context, int resource, List<EmployeeOnProject> objects) {
        super(context, resource, objects);

        // inizializzo sto coso
        layoutInflater = LayoutInflater.from(context);
    }

    /* Questo metodo restituisce l'elemento della lista nella posizione specificata */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.dipendente, null); // inietto un elemento della lista nella view
        EmployeeOnProject employeeOnProject = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.dipendente_name);
        TextView hours = (TextView) view.findViewById(R.id.dipendente_hours);

        name.setText(employeeOnProject.getEmployee().getName() + " " + employeeOnProject.getEmployee().getSurname());
        hours.setText(Integer.toString(employeeOnProject.getHours()) + " ore");

        return view;
    }
}