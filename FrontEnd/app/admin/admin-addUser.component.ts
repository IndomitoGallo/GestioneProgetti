import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../model/user';
import { Profile }    from '../model/profile';
import { UserProfiles } from '../model/userProfiles';

import { AdminService } from './admin.service';

/*
 * Qui di seguito dichiariamo il @Component AdminAddUserComponent,
 * il cui Html template si trova nel file "admin-addUser.html".
 */
@Component({
    templateUrl: 'app/admin/admin-addUser.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [AdminService]
})

export class AdminAddUserComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    userProfiles: UserProfiles;
    user: User;

    //profili da scegliere per l'utente creato
    dipendente = false;
    pm = false;

    //Costruttore inizializzato con AdminService e Router (Dependency Injection)
    constructor(private _adminService: AdminService, private _router: Router, private routeParams: RouteParams) { }

    /*
     * La funzione addUser prende in input tutti i campi del form di creazione
     * dell'utente, tranne l'informazioni relativa ai profili associati, che può ricavare
     * dalle variabili del Component "dipendente" e "pm".
     */
    addUser(event, name: string, surname: string, username: string,
            email: string, pwd: string, skill: string) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();

        this.user = new User(0, username, pwd, email, name, surname, this.escapeString(skill), false, 0);

        if(!this.dipendente && !this.pm) {
            this.errorMessage="Devi scegliere almeno un profilo tra quelli proposti";
        }
        else if(!this.dipendente && this.pm) {
            //Lancio il service associando l'utente al profilo PM
            this.userProfiles = new UserProfiles(this.user, [4], this.sessionId);
            this._adminService.addUser(this.userProfiles)
                              .subscribe(
                                  esito => {
                                    this._router.navigate( ['Admin', { sessionId: this.sessionId }] );
                                  },
                                  error =>  this.errorMessage = <any>error
                              );

        }
        else if(this.dipendente && !this.pm) {
            //Lancio il service associando l'utente al profilo Dipendente
            this.userProfiles = new UserProfiles(this.user, [3], this.sessionId);
            this._adminService.addUser(this.userProfiles)
                              .subscribe(
                                  esito => {
                                    this._router.navigate( ['Admin', { sessionId: this.sessionId }] );
                                  },
                                  error =>  this.errorMessage = <any>error
                              );
        }
        else if(this.dipendente && this.pm) {
            //Lancio il service associando l'utente a entrambi i profili
            this.userProfiles = new UserProfiles(this.user, [3, 4], this.sessionId);
            this._adminService.addUser(this.userProfiles)
                              .subscribe(
                                  esito => {
                                    this._router.navigate( ['Admin', { sessionId: this.sessionId }] );
                                  },
                                  error =>  this.errorMessage = <any>error
                              );
        }

    }

    /*
     * Questa funzione effettua l'escape di una stringa in modo che l'SQL accetti
     * anche caratteri speciali come l'apostrofo.
     */
    escapeString(myString: string): string {

        myString = myString.replace(/[\0\n\r\b\t\\'"\x1a]/g, function (s) {
          switch (s) {
              case "\0":
                return "\\0";
              case "\n":
                return "\\n";
              case "\r":
                return "\\r";
              case "\b":
                return "\\b";
              case "\t":
                return "\\t";
              case "\x1a":
                return "\\Z";
              case "'":
                return "''";
              case '"':
                return '""';
              default:
                return "\\" + s;
            }
        });

        return myString;

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

    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        console.log("ViewUser SESSION: " + this.sessionId);
    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

}
