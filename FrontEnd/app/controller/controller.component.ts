import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams } from 'angular2/router';

import { User }                from '../model/user';

/*
 * Qui di seguito dichiariamo il @Component ControllerComponent,
 * il cui Html template si trova nel file "controller.html".
 */
@Component({
    templateUrl: 'app/controller/controller.html'
})

export class ControllerComponent {

    errorMessage: string;

    constructor(private _router: Router) { }

}
