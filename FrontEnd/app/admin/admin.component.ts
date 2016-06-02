import { Component, OnInit, Input }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User } from '../model/user';
import { AdminService } from './admin.service';

/*
 * Qui di seguito dichiariamo il @Component AdminComponent,
 * il cui Html template si trova nel file "admin.html".
 * Tale componente gestisce la pagina principale del profilo Amministratore,
 * e quindi la tabella degli utenti da visualizzare.
 */
@Component({
    templateUrl: 'app/admin/admin.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [AdminService]
})

export class AdminComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    users: User[];

    //Costruttore inizializzato con AdminService e Router (Dependency Injection)
    constructor(private _adminService: AdminService, private _router: Router, private routeParams: RouteParams) { }

    //displayUser
    displayUser(id: number) {
        console.log("L'utente da visualizzare ha id: " + id);
        //L'Amministratore viene reindirizzato alla pagina di visualizzazione di un utente
        this._router.navigate( ['ViewUser', { sessionId: this.sessionId, userId: id }] );
    }

    //updateUser
    updateUser(id) {
        console.log("L'utente da aggiornare ha id: " + id);
        //L'Amministratore viene reindirizzato alla pagina di aggiornamento di un utente
        this._router.navigate( ['UpdateUser', { sessionId: this.sessionId, userId: id }] );
    }

    //deleteUser
    deleteUser(id) {
        console.log("L'utente da eliminare ha id: " + id);
        //chiamata alla procedura deleteUser di AdminService
        this._adminService.deleteUser(this.sessionId, id)
                         .subscribe(
                               esito => {
                                 //this._router.navigate( ['Admin', { sessionId: this.sessionId }] );
                                 //carico dal server tutti gli utenti da visualizzare
                                 this._adminService.getUsers(this.sessionId)
                                                  .subscribe(
                                                        users  => this.users = users,
                                                        error =>  this.errorMessage = "Impossibile caricare la lista degli utenti"
                                                   );
                               },
                               error =>  this.errorMessage = <any>error
                          );

    }

    //metodo che cambia la pagina in un form per la creazione di un nuovo utente
    redirectCreation() {
        //L'Amministratore viene reindirizzato alla pagina di creazione di un utente
        this._router.navigate( ['AddUser', { sessionId: this.sessionId }] );
    }

    //logout
    logout() {
        this._adminService.logout(this.sessionId)
                          .subscribe(
                                esito => {
                                  this._router.navigate( ['Login'] );
                                },
                                error =>  this.errorMessage = <any>error
                          );
    }

    //Metodo eseguito automaticamente quando si attiva tale componente
    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        //carico dal server tutti gli utenti da visualizzare
        this._adminService.getUsers(this.sessionId)
                         .subscribe(
                               users  => this.users = users,
                               error =>  this.errorMessage = <any>error
                          );
    }

}
