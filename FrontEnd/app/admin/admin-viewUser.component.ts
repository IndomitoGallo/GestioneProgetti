import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../model/user';
import { Profile }    from '../model/profile';

/*
 * Qui di seguito dichiariamo il @Component AdminViewUserComponent,
 * il cui Html template si trova nel file "admin-viewUser.html".
 */
@Component({
    templateUrl: 'app/admin/admin-viewUser.html',
    directives: [ ROUTER_DIRECTIVES ]
})

export class AdminViewUserComponent {

    errorMessage: string;

    //utente di prova
    user = new User(1, 'Luca', 'Talocci', 'IndomitoGallo', 'lucatalocci@gmail.com', 'ciccio', 'Nullafacente', false);

    //Costruttore inizializzato con AdminService e Router (Dependency Injection)
    constructor(private _router: Router) { }

}
