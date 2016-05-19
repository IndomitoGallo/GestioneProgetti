package it.uniroma2.gestioneprogetti.response;

import java.util.List;

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
