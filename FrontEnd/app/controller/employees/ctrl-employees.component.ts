import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, ROUTER_DIRECTIVES } from 'angular2/router';

import { Project }             from '../../model/project';
import { User }                from '../../model/user';
import { ControllerService }   from '../controller.service';

/*
 * Qui di seguito dichiariamo che il @Component CtrlEmployeesComponent ha come URL-template
 * il contenuto del file 'app/controller/ctrl-projects.html'.
 */
@Component({
    templateUrl: 'app/controller/employees/ctrl-employees.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

/*
 * La classe CtrlEmployeesComponent contiene un array di utenti che vengono visualizzati nella schermata
 * e il Controller può fare diverse operazioni su di essi.
 */
export class CtrlEmployeesComponent {

    errorMessage: string;

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService, private _router: Router) { }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

}
