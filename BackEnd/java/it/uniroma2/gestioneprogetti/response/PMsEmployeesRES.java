package it.uniroma2.gestioneprogetti.response;

import java.util.List;
/**
 * La classe PMsEmployeesRES modella un oggetto response che incapsula al suo interno 
 * le proprietà List<UserRES> "lista di dipendenti" e List<UserRES> "lista dei projectManget" in modo tale
 * da trasportare i dati dallo strato Application allo strato Services.
 * @author Gruppo Talocci
 */
public class PMsEmployeesRES extends AbstractRES{

    private List<UserRES> employeesList;
    private List<UserRES> pmsList;

    public List<UserRES> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<UserRES> employeesList) {
        this.employeesList = employeesList;
    }

    public List<UserRES> getPmsList() {
        return pmsList;
    }

    public void setPmsList(List<UserRES> pmsList) {
        this.pmsList = pmsList;
    }

}
