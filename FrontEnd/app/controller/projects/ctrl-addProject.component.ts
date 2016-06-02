import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../../model/user';
import { Project }    from '../../model/project';
import { PmsEmployees }    from '../../model/pmsEmployees';
import { ControllerService }   from '../controller.service';

/*
 * Qui di seguito dichiariamo il @Component CtrlAddProjectComponent,
 * il cui Html template si trova nel file "ctrl-addProject.html".
 */
@Component({
    templateUrl: 'app/controller/projects/ctrl-addProject.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

export class CtrlAddProjectComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    active: boolean = true;

    pms: User[];
    employees: User[];

    pm: number;
    dipendenti: boolean[] = [];

    project: Project;
    selectedEmployees: number[] = [];


    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService, private _router: Router, private routeParams: RouteParams) { }

    /*
     * La funzione addProject prende in input tutti i campi del form di creazione
     * del progetto.
     */
    addProject(event, name: string, description: string, budget: number, pm: number) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        if(budget <= 0) {
            this.errorMessage = "Inserire un budget maggiore o uguale a zero";
        }
        else {
          
            this.project = new Project(0, name, description, "in corso", budget, 0, pm);
            console.log("Progetto che si vuole creare = " + JSON.stringify(this.project));

            //catturo i dipendenti selezionati
            for(var i=1; i < this.dipendenti.length + 1; i++) {
                if(this.dipendenti[i]) {
                    this.selectedEmployees.push(i);
                }
            }
            console.log("Dipendenti associati al progetto creato = " + this.selectedEmployees);
            this.project.name = this.escapeString(this.project.name);
            this.project.description = this.escapeString(this.project.description);
            this._ctrlService.addProject(this.sessionId, this.project, this.selectedEmployees)
                              .subscribe(
                                  esito => {
                                    this._router.navigate( ['CtrlProjects', {sessionId : this.sessionId}] );
                                  },
                                  error =>  {
                                    this.errorMessage = <any>error;
                                    //elimino i dipendenti precedentemente selezionati
                                    var length = this.selectedEmployees.length;
                                    for(var i=0; i < length; i++) {
                                            this.selectedEmployees.pop();
                                    }
                                  }
                              );

        }

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

    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        //Recupero la lista dei PM e dei Dipendenti dal server
        this._ctrlService.getPmsEmployees(this.sessionId)
                         .subscribe(
                               pmsEmployees => {
                                   console.log("ResponseBody = " + JSON.stringify(pmsEmployees));
                                   this.employees = pmsEmployees[0];
                                   this.pms = pmsEmployees[1];
                                   console.log("Dipendenti = " + JSON.stringify(this.employees));
                                   console.log("PMS = " + JSON.stringify(this.pms));
                                   this.setDipendenti();
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
        console.log("ARRAY BOOLEANO = " + this.dipendenti);
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

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

}
