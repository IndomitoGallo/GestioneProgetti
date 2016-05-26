import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../../model/user';
import { Profile }    from '../../model/profile';
import { ControllerService }   from '../controller.service';

/*
 * Qui di seguito dichiariamo il @Component CtrlAddProjectComponent,
 * il cui Html template si trova nel file "ctrl-addProject.html".
 */
@Component({
    templateUrl: 'app/controller/projects/ctrl-addProject.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

export class CtrlAddProjectComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    managers: User[];

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService, private _router: Router, private routeParams: RouteParams) { }

    /*
     * La funzione addProject prende in input tutti i campi del form di creazione
     * del progetto.
     */
    addProject(event, name: string, description: string, budget: number) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        //Torno alla pagina principale dell'Amministratore
        this._router.navigate( ['CtrlProjects', {sessionId : this.sessionId}] );

    }

    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

}
