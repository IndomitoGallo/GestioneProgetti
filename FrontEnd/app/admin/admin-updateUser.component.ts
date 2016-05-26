import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../model/user';
import { Profile }    from '../model/profile';
import { UserProfiles } from '../model/userProfiles';

import { AdminService } from './admin.service';

/*
 * Qui di seguito dichiariamo il @Component AdminUpdateUserComponent,
 * il cui Html template si trova nel file "admin-updateUser.html".
 */
@Component({
    templateUrl: 'app/admin/admin-updateUser.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [AdminService]
})

export class AdminUpdateUserComponent implements OnInit {

    errorMessage: string;

    sessionId: string;
    userId: string;

    //utente di prova
    user: User;
    profiles: number[];
    //profili da scegliere per l'utente creato
    dipendente = false;
    pm = false;

    //Costruttore inizializzato con AdminService e Router (Dependency Injection)
    constructor(private _adminService: AdminService, private _router: Router, private routeParams: RouteParams) { }

    /*
     * La funzione updateUser prende in input tutti i campi del form di creazione
     * dell'utente, tranne l'informazioni relativa ai profili associati, che può ricavare
     * dalle variabili del Component "dipendente" e "pm".
     */
    updateUser(event, name: string, surname: string, username: string,
            email: string, pwd: string, skill: string) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        if(!this.dipendente && !this.pm) {
            this.errorMessage="Devi scegliere almeno un profilo tra quelli proposti";
        }
        else if(!this.dipendente && this.pm) {
            //Lancio il service associando l'utente al profilo PM
        }
        else if(this.dipendente && !this.pm) {
            //Lancio il service associando l'utente al profilo Dipendente
        }
        else if(this.dipendente && this.pm) {
            //Lancio il service associando l'utente a entrambi i profili
        }
        //Torno alla pagina principale dell'Amministratore
        this._router.navigate( ['Admin', {sessionId: this.sessionId}] );

    }

    /*
     * La seguente funzione è gestita nell'html dalle checkbox: quando l'amministratore
     * clicca su una delle checkbox cambia lo stato delle variabile interne del
     * Component: "dipendente" e "pm". In questo modo, quando vengono inviati i dati al form
     * la funzione di creazione dell'utente conosce quali e quanti profili sono stati scelti.
     */
    isSelected(profilo: string) {

        if(profilo == "dip") {
            if(this.dipendente) {
                this.dipendente = false;
            }
            else this.dipendente = true;
        }
        else {
            if(this.pm) {
                this.pm = false;
            }
            else this.pm = true;
        }

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
        console.log("UpdateUser SESSION: " + this.sessionId);
        //Recupero l'id dell'utente da visualizzare
        this.userId = this.routeParams.get('userId');
        console.log("UpdateUser USER: " + this.userId);
        //carico dal server le info dell'utente da visualizzare
        this._adminService.getUser(this.sessionId, this.userId)
                         .subscribe(
                               function(userProfiles) {
                                  console.log("UserProfiles: " + JSON.stringify(userProfiles));
                                  this.user = userProfiles.user;
                                  this.profiles = userProfiles.profiles;
                                  console.log("User: " + JSON.stringify(this.user));
                                  console.log("Profiles: " + JSON.stringify(this.profiles));
                                  console.log("Username: " + this.user.username);
                               }
                               //userProfiles  => this.userProfiles = userProfiles
                               ,
                               error =>  this.errorMessage = <any>error
                          );
    }

}
