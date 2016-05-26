import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../../model/user';
import { Profile }    from '../../model/profile';
import { Project }    from '../../model/project';

import { ControllerService } from '../controller.service';

/*
 * Qui di seguito dichiariamo il @Component CtrlUpdateProjectComponent,
 * il cui Html template si trova nel file "ctrl-updateProject.html".
 */
@Component({
    templateUrl: 'app/controller/projects/ctrl-updateProject.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

export class CtrlUpdateProjectComponent implements OnInit {

    errorMessage: string;

    sessionId: string;
    projectId: string;

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService, private _router: Router, private routeParams: RouteParams) { }

    /*
     * La funzione updateUser prende in input tutti i campi del form di creazione
     * dell'utente, tranne l'informazioni relativa ai profili associati, che può ricavare
     * dalle variabili del Component "dipendente" e "pm".
     */
    updateUser(event, name: string, surname: string, username: string,
            email: string, pwd: string, skill: string) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        //Torno alla pagina principale dell'Amministratore
        this._router.navigate( ['Controller', {sessionId: this.sessionId}] );

    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        console.log("UpdateProject SESSION: " + this.sessionId);
        //Recupero l'id dell'utente da visualizzare
        this.projectId = this.routeParams.get('projectId');
        console.log("UpdateProject PROJECT: " + this.projectId);
        //carico dal server le info dell'utente da visualizzare
    }

}
