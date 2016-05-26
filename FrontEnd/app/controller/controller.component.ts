import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { RouteConfig, Router, RouteParams, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';

import { CtrlProjectsComponent }    from './projects/ctrl-projects.component';
import { CtrlEmployeesComponent }   from './employees/ctrl-employees.component';

import { User }                from '../model/user';

import { ControllerService } from './controller.service';

/*
 * Qui di seguito dichiariamo il @Component ControllerComponent,
 * il cui Html template si trova nel file "controller.html".
 */
@Component({
    templateUrl: 'app/controller/controller.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

export class ControllerComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    //Costruttore inizializzato con Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService,private _router: Router, private routeParams: RouteParams) { }

    //logout
    logout() {
        this._ctrlService.logout(this.sessionId)
                          .subscribe(
                                esito => {
                                  this._router.navigate( ['Login'] );
                                },
                                error => this.errorMessage = "Impossibile effettuare il Logout"
                          );
    }

    //Metodo eseguito automaticamente quando si attiva tale componente
    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
    }

    //Metodo per andare nell'area dei projects
    goToProjects() {
        this._router.navigate( ['CtrlProjects', {sessionId: this.sessionId}] );
    }

    //Metodo per andare nell'area degli employees
    goToUsers() {
        this._router.navigate( ['CtrlEmployees', {sessionId: this.sessionId}] );
    }
}
