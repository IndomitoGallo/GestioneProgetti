import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams } from 'angular2/router';

/*
 * Qui di seguito dichiariamo il @Component AdminComponent,
 * il cui Html template si trova nel file "admin.html".
 */
@Component({
    templateUrl: 'app/admin/admin.html'
})

export class AdminComponent {

    errorMessage: string;

    constructor() { }

}
