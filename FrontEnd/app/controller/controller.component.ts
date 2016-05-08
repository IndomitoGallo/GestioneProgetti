import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { RouteConfig, Router, RouteParams, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';

import { CtrlProjectsComponent }    from './projects/ctrl-projects.component';
import { CtrlEmployeesComponent }   from './employees/ctrl-employees.component';

import { User }                from '../model/user';

/*
 * Qui di seguito dichiariamo il @Component ControllerComponent,
 * il cui Html template si trova nel file "controller.html".
 */
@Component({
    templateUrl: 'app/controller/controller.html',
    directives: [ ROUTER_DIRECTIVES ]
})

export class ControllerComponent {

    errorMessage: string;

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _router: Router) { }

}
