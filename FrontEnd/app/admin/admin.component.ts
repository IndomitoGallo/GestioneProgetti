import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User } from '../model/user';

/*
 * Qui di seguito dichiariamo il @Component AdminComponent,
 * il cui Html template si trova nel file "admin.html".
 * Tale componente gestisce la pagina principale del profilo Amministratore,
 * e quindi la tabella degli utenti da visualizzare.
 */
@Component({
    templateUrl: 'app/admin/admin.html',
    directives: [ ROUTER_DIRECTIVES ]
})

export class AdminComponent {

    errorMessage: string;

    users = [
         new User(1, 'Nome', 'Cognome', 'IndomitoGallo', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
         new User(2, 'Nome', 'Cognome', 'LorenzoB', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
         new User(3, 'Nome', 'Cognome', 'Gaudo', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
         new User(4, 'Nome', 'Cognome', 'Aizevs', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
         new User(3, 'Nome', 'Cognome', 'LukCame', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
         new User(4, 'Nome', 'Cognome', 'Dav33', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false)
    ];

    //Costruttore inizializzato con AdminService e Router (Dependency Injection)
    constructor(private _router: Router) { }

    displayUser(userId) {
        //L'Amministratore viene reindirizzato alla pagina di visualizzazione di un utente
        this._router.navigate( ['ViewUser'] );
    }

    updateUser(userId) {
        //L'Amministratore viene reindirizzato alla pagina di aggiornamento di un utente
        this._router.navigate( ['UpdateUser'] );
    }

    redirectCreation() {
        //L'Amministratore viene reindirizzato alla pagina di creazione di un utente
        this._router.navigate( ['AddUser'] );
    }

}
