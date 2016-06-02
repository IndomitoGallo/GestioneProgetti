import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, ROUTER_DIRECTIVES, RouteParams } from 'angular2/router';

import { Project }             from '../../model/project';
import { User }                from '../../model/user';
import { ControllerService }   from '../controller.service';

/*
 * Qui di seguito dichiariamo che il @Component CtrlProjectsComponent ha come URL-template
 * il contenuto del file 'app/controller/ctrl-projects.html'.
 */
@Component({
    templateUrl: 'app/controller/projects/ctrl-projects.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [ControllerService]
})

/*
 * La classe CtrlProjectsComponent contiene un array di progetti che vengono visualizzati nella schermata
 * e il Controller può fare diverse operazioni su di essi.
 */
export class CtrlProjectsComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    projects: Project[];

    //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
    constructor(private _ctrlService: ControllerService, private _router: Router, private routeParams: RouteParams) { }

    displayProject(projId) {
        //Il Controller viene reindirizzato alla pagina di visualizzazione di un progetto
        this._router.navigate( ['ViewProject', { sessionId : this.sessionId, projectId: projId }] );
    }

    updateProject(projId) {
        //Il Controller viene reindirizzato alla pagina di aggiornamento di un progetto
        this._router.navigate( ['UpdateProject', { sessionId : this.sessionId, projectId: projId }] );
    }

    redirectCreation() {
        //Il Controller viene reindirizzato alla pagina di creazione di un progetto
        this._router.navigate( ['AddProject', {sessionId: this.sessionId}] );
    }

    //Metodo eseguito automaticamente quando si attiva tale componente
    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        //carico dal server tutti i progetti da visualizzare
        this._ctrlService.getProjects(this.sessionId)
                         .subscribe(
                               projects  => this.projects = projects,
                               error =>  this.errorMessage = <any>error
                          );
    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        this._router.navigate( ['Controller', { sessionId: this.sessionId }] );
    }

}
