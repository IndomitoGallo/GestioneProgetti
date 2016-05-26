import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, ROUTER_DIRECTIVES, RouteParams } from 'angular2/router';

import { Project }             from '../model/project';
import { User }                from '../model/user';
import { PMService }           from './pm.service';

/*
 * Qui di seguito dichiariamo il @Component PMComponent,
 * il cui Html template si trova nel file "pm.html".
 */
@Component({
    templateUrl: 'app/pm/pm.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [PMService]
})

export class PMComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    projects: Project[];

    //Costruttore inizializzato con PMService e Router (Dependency Injection)
    constructor(private _pmService: PMService, private _router: Router, private routeParams: RouteParams) { }

    //logout
    logout() {
        this._pmService.logout(this.sessionId)
                          .subscribe(
                                esito => {
                                  this._router.navigate( ['Login'] );
                                },
                                error => this.errorMessage = "Impossibile effettuare il Logout"
                          );
    }

    //displayProject
    displayProject(projId) {
        //Il PM viene reindirizzato alla pagina di visualizzazione di un progetto
        this._router.navigate( ['ViewPMProject', { sessionId : this.sessionId, projectId: projId }] );
    }

    //Metodo eseguito automaticamente quando si attiva tale componente
    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        //carico dal server tutti i progetti da visualizzare
        this._pmService.getProjects(this.sessionId)
                         .subscribe(
                               projects  => this.projects = projects,
                               error =>  this.errorMessage = "Errore durante il caricamento dei progetti"
                          );
    }

}
