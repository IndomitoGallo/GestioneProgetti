package it.uniroma2.gestioneprogetti.dao;

import it.uniroma2.gestioneprogetti.domain.Timesheet;
import java.util.List;

public interface ITimesheetDAO {
    public List<Timesheet> displayTimesheet();
    public String addTimesheet(Timesheet timesheet);
    public String updateTimesheet(Timesheet timesheet);
    public String deleteTimesheet(Timesheet timesheet);
}
