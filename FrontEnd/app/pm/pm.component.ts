import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams } from 'angular2/router';

/*
 * Qui di seguito dichiariamo il @Component PMComponent,
 * il cui Html template si trova nel file "pm.html".
 */
@Component({
    templateUrl: 'app/pm/pm.html'
})

export class PMComponent {

    errorMessage: string;

    constructor() { }

}
