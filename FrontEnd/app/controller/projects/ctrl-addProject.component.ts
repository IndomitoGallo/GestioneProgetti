import { Component }           from 'angular2/core';
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

export class CtrlAddProjectComponent {

    errorMessage: string;

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _router: Router) { }

    managers: User[];

    /*
     * La funzione addProject prende in input tutti i campi del form di creazione
     * del progetto.
     */
    addProject(event, name: string, description: string, budget: number) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        //Torno alla pagina principale dell'Amministratore
        this._router.navigate( ['CtrlProjects'] );

    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

}
