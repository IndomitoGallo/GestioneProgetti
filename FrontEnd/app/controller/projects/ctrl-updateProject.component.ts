import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../../model/user';
import { Profile }    from '../../model/profile';
import { Project }    from '../../model/project';
import { ProjectForm }    from '../../model/projectForm';

import { ControllerService } from '../controller.service';

/*
 * Qui di seguito dichiariamo il @Component CtrlUpdateProjectComponent,
 * il cui Html template si trova nel file "ctrl-updateProject.html".
 */
@Component({
    templateUrl: 'app/controller/projects/ctrl-updateProject.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

export class CtrlUpdateProjectComponent implements OnInit {

    errorMessage: string;

    sessionId: string;
    projectId: string;

    //dati progetto nel form
    project: Project;
    //dati progetto per l'update
    projectUpdate: Project;

    pm: number;
    employees: User[];
    managers: User[] = [];
    employeesAssociation: number[];

    dipendenti: boolean[] = [];
    pmName: string;
    pms: User[];

    standby: boolean;
    concluso: boolean;
    active: boolean = false;

    selectedEmployees: number[] = [];

    //un flag che mi dice se è stato selezionato almeno un dipendente
    selectedFlag: boolean;

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService, private _router: Router, private routeParams: RouteParams) { }

    /*
     * La funzione updateUser prende in input tutti i campi del form di creazione
     * dell'utente, tranne l'informazioni relativa ai profili associati, che può ricavare
     * dalle variabili del Component "dipendente" e "pm".
     */
    updateProject(event, name: string, description: string, budget: number, status: number, pm: number) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        if(budget <= 0) {
            this.errorMessage = "Inserire un budget maggiore di zero";
        }
        else {
            console.log("Status: " + status);
            if(status == 1) {
                this.projectUpdate = new Project(this.project.id, this.escapeString(name), this.escapeString(description), "in corso", budget, 0, pm);
            } else if(status == 2) {
                this.projectUpdate = new Project(this.project.id, this.escapeString(name), this.escapeString(description), "stand-by", budget, 0, pm);
            } else {
                this.projectUpdate = new Project(this.project.id, this.escapeString(name), this.escapeString(description), "concluso", budget, 0, pm);
            }

            console.log("Progetto che si vuole aggiornare = " + JSON.stringify(this.projectUpdate));

            this.selectedFlag = false;
            //catturo i dipendenti selezionati
            for(var i=1; i < this.dipendenti.length + 1; i++) {
                if(this.dipendenti[i]) {
                    this.selectedEmployees.push(i);
                    this.selectedFlag = true;
                }
            }

            if(!this.selectedFlag) {
                this.errorMessage = "Devi selezionare almeno un Dipendente";
            }
            else {
                console.log("Dipendenti associati al progetto creato = " + this.selectedEmployees);
                this._ctrlService.updateProject(this.sessionId, this.projectUpdate, this.selectedEmployees)
                                  .subscribe(
                                      esito => {
                                        this._router.navigate( ['CtrlProjects', {sessionId : this.sessionId}] );
                                      },
                                      error =>  this.errorMessage = <any>error
                                  );
            }
        }

    }

    /*
     * Questa funzione permette di riattivare il progetto.
     */
    activateProject(event, status: number) {
        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        var projectId = this.project.id;
        var projectName = this.project.name;
        var projectDescription = this.project.description;
        var projectBudget = this.project.budget;
        var projectCost = this.project.cost;
        var projectPM = this.project.projectManager;

        if(status == 1) {
            this.project = new Project(projectId, projectName, projectDescription, "in corso", projectBudget, projectCost, projectPM);
        } else if(status == 2) {
            this.project = new Project(projectId, projectName, projectDescription, "stand-by", projectBudget, projectCost, projectPM);
        } else {
            this.project = new Project(projectId, projectName, projectDescription, "concluso", projectBudget, projectCost, projectPM);
        }

        console.log("Progetto che si vuole riattivare = " + JSON.stringify(this.project));

        //catturo i dipendenti selezionati
        for(var i=1; i < this.dipendenti.length + 1; i++) {
            if(this.dipendenti[i]) {
                this.selectedEmployees.push(i);
            }
        }
        console.log("Dipendenti associati al progetto da riattivare = " + this.selectedEmployees);
        this.project.name = this.escapeString(this.project.name);
        this.project.description = this.escapeString(this.project.description);
        this._ctrlService.updateProject(this.sessionId, this.project, this.selectedEmployees)
                          .subscribe(
                              esito => {
                                this._router.navigate( ['CtrlProjects', {sessionId : this.sessionId}] );
                              },
                              error =>  this.errorMessage = <any>error
                          );
    }

    /*
     * Questa funzione effettua l'escape di una stringa in modo che l'SQL accetti
     * anche caratteri speciali come l'apostrofo.
     */
    escapeString(myString: string): string {

        myString = myString.replace(/[\0\n\r\b\t\\'"\x1a]/g, function (s) {
          switch (s) {
              case "\0":
                return "\\0";
              case "\n":
                return "\\n";
              case "\r":
                return "\\r";
              case "\b":
                return "\\b";
              case "\t":
                return "\\t";
              case "\x1a":
                return "\\Z";
              case "'":
                return "''";
              case '"':
                return '""';
              default:
                return "\\" + s;
            }
        });

        return myString;

    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        console.log("UpdateProject SESSION: " + this.sessionId);
        //Recupero l'id dell'utente da visualizzare
        this.projectId = this.routeParams.get('projectId');
        console.log("UpdateProject PROJECT: " + this.projectId);

        //carico dal server le info del progetto da visualizzare
        this._ctrlService.getProjectForm(this.sessionId, this.projectId)
                         .subscribe(
                               projectForm => {
                                   console.log("ResponseBody = " + JSON.stringify(projectForm));
                                   this.project = projectForm.project;
                                   this.pm = this.project.projectManager;
                                   this.employees = projectForm.employees;
                                   this.pms = projectForm.pms;
                                   this.employeesAssociation = projectForm.employeesAssociation;
                                   console.log("PROGETTO = " + JSON.stringify(this.project));
                                   console.log("PM = " + JSON.stringify(this.pm));
                                   console.log("Dipendenti = " + JSON.stringify(this.employees));
                                   console.log("PMS = " + JSON.stringify(this.pms));
                                   console.log("Associazioni = " + JSON.stringify(this.employeesAssociation));
                                   this.setDipendenti();
                                   this.parseManagers();
                                   console.log("PMS = " + JSON.stringify(this.managers));
                                   console.log("PmName = " + this.pmName);
                                   if(this.project.status == "stand-by" || this.project.status == "concluso") {
                                       this.standby = true;
                                   }
                                   else {
                                       this.standby = false;
                                   }
                                   this.active = true;
                               },
                               error =>  this.errorMessage = <any>error
                          );
    }

    /*
     * Questa funzione serve a settare un array di booleani per prendere in ingresso gli id dei dipendenti scelti
     */
    setDipendenti() {
        //scopro qual è l'id più grande tra i Dipendenti in ingresso
        var maxIndex: number = 0;
        for(var i=0; i < this.employees.length; i++) {
            if(this.employees[i].id > maxIndex) {
                maxIndex = this.employees[i].id;
            }
        }
        console.log("Indice Massimo = " + maxIndex);
        //inizializzo l'array di booleani con dentro tanti valori quant'è grande maxIndex
        for(var j=0; j < (maxIndex+1); j++) {
            this.dipendenti.push(false);
        }
        //setto a true i valori dei dipendenti appartenenti al progetto
        for(var k=0; k < (this.employeesAssociation.length); k++) {
            this.dipendenti[this.employeesAssociation[k]] = true;
        }
        console.log("ARRAY BOOLEANO = " + this.dipendenti);
    }

    /*
     * Questa funzione serve a togliere il pm predefinito dalla lista di tutti i pms e settarlo in pm.
     */
    parseManagers() {
        var pm: User;
        var length = this.pms.length;
        for(var i=0; i < length; i++) {
            pm = this.pms.shift();
            if(pm.id != this.pm) {
                this.managers.push(pm);
            }
            else {
                this.pmName = pm.name + " " + pm.surname;
            }
        }
    }

    /*
     * La seguente funzione è gestita nell'html dalle checkbox: quando il Controller
     * clicca su una delle checkbox cambia lo stato di una delle celle dell'array
     * booleano dipendenti. In questo modo, quando vengono inviati i dati al server
     * la funzione di creazione del progetto conosce quali e quanti dipendenti sono stati scelti.
     */
    isSelected(employee: number) {
        console.log("Selected Employee = " + employee);
        if(this.dipendenti[employee])
            this.dipendenti[employee] = false;
        else this.dipendenti[employee] = true;
        console.log("ARRAY BOOLEANO = " + this.dipendenti);
    }

}
