import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, ROUTER_DIRECTIVES } from 'angular2/router';

import { Project }             from '../../model/project';
import { User }                from '../../model/user';

/*
 * Qui di seguito dichiariamo che il @Component CtrlProjectsComponent ha come URL-template
 * il contenuto del file 'app/controller/ctrl-projects.html'.
 */
@Component({
    templateUrl: 'app/controller/projects/ctrl-projects.html',
    directives: [ ROUTER_DIRECTIVES ]
})

/*
 * La classe CtrlProjectsComponent contiene un array di progetti che vengono visualizzati nella schermata
 * e il Controller può fare diverse operazioni su di essi.
 */
export class CtrlProjectsComponent {

    errorMessage: string;

    projects = [
         new Project(1, 'Progetto1', 'Descrizione', 'Status', 10000, 1000),
         new Project(2, 'Progetto2', 'Descrizione', 'Status', 10000, 1000),
         new Project(3, 'Progetto3', 'Descrizione', 'Status', 10000, 1000),
         new Project(4, 'Progetto4', 'Descrizione', 'Status', 10000, 1000)
    ];

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(/*private _controllerService: ControllerService,*/ private _router: Router) { }

}
