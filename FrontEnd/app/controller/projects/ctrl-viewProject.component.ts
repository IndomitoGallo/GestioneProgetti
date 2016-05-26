import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../../model/user';
import { Profile }    from '../../model/profile';
import { Project }    from '../../model/project';
import { Employee }    from '../../model/employee';

import { ControllerService } from '../controller.service';

/*
 * Qui di seguito dichiariamo il @Component CtrlViewProjectComponent,
 * il cui Html template si trova nel file "ctrl-viewProject.html".
 */
@Component({
    templateUrl: 'app/controller/projects/ctrl-viewProject.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

export class CtrlViewProjectComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    projectId: string;

    active: boolean = false;

    //Project data
    //Project data
    project: Project;
    dipendenti: User[];
    hours: number[];
    projectManager: string;

    employees: Employee[] = [];

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService, private _router: Router, private routeParams: RouteParams) { }

    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        console.log("ViewProject SESSION: " + this.sessionId);
        //Recupero l'id dell'utente da visualizzare
        this.projectId = this.routeParams.get('projectId');
        console.log("ViewProject PROJECT: " + this.projectId);
        //carico dal server le info dell'utente da visualizzare
        this._ctrlService.getProject(this.sessionId, this.projectId)
                         .subscribe(
                               projectEmployeesHours => {
                                   console.log("ResponseBody = " + JSON.stringify(projectEmployeesHours));
                                   this.project = projectEmployeesHours.project;
                                   this.dipendenti = projectEmployeesHours.employees;
                                   this.projectManager = projectEmployeesHours.pmName;
                                   this.hours = projectEmployeesHours.hours;
                                   console.log("Project = " + JSON.stringify(this.project));
                                   console.log("Dipendenti = " + JSON.stringify(this.dipendenti));
                                   console.log("Hours = " + JSON.stringify(this.hours));
                                   console.log("PM = " + JSON.stringify(this.projectManager));
                                   this.parseEmployees();
                                   console.log("Employees = " + JSON.stringify(this.employees));
                                   this.active = true;
                               },
                               error =>  this.errorMessage = "Impossibile visualizzare le info del progetto selezionato"
                          );

    }

    /*
     * Questa funzione serve a parsare i dipendenti e le ore lavorative passate dal server.
     */
    parseEmployees() {
        var user: User;
        var hour: number;
        for(var i=0; i < this.dipendenti.length; i++) {
            user = this.dipendenti.shift();
            hour = this.hours.shift();
            var employee = new Employee(user.name + user.surname, hour);
            this.employees.push(employee);
        }
    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

}
