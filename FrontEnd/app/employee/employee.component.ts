import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams } from 'angular2/router';

/*
 * Qui di seguito dichiariamo il @Component EmployeeComponent,
 * il cui Html template si trova nel file "employee.html".
 */
@Component({
    templateUrl: 'app/employee/employee.html'
})

export class EmployeeComponent {

    errorMessage: string;

    constructor() { }

}
