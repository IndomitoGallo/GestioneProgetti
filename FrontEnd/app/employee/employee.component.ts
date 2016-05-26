import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { EmployeeService } from './employee.service';

/*
 * Qui di seguito dichiariamo il @Component EmployeeComponent,
 * il cui Html template si trova nel file "employee.html".
 */
@Component({
    templateUrl: 'app/employee/employee.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [EmployeeService]
})

export class EmployeeComponent {

    errorMessage: string;

    sessionId: string;

    //Costruttore inizializzato con EmployeeService e Router (Dependency Injection)
    constructor(private _employeeService: EmployeeService, private _router: Router, private routeParams: RouteParams) { }

    //logout
    logout() {
        this._employeeService.logout(this.sessionId)
                          .subscribe(
                                esito => {
                                  this._router.navigate( ['Login'] );
                                },
                                error => this.errorMessage = "Impossibile effettuare il Logout"
                          );
    }
}
