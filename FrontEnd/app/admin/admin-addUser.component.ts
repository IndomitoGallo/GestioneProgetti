import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../model/user';
import { Profile }    from '../model/profile';

/*
 * Qui di seguito dichiariamo il @Component AdminAddUserComponent,
 * il cui Html template si trova nel file "admin-addUser.html".
 */
@Component({
    templateUrl: 'app/admin/admin-addUser.html',
    directives: [ ROUTER_DIRECTIVES ]
})

export class AdminAddUserComponent {

    errorMessage: string;

    //profili da scegliere per l'utente creato
    dipendente = false;
    pm = false;

    //Costruttore inizializzato con AdminService e Router (Dependency Injection)
    constructor(private _router: Router) { }

    /*
     * La funzione addUser prende in input tutti i campi del form di creazione
     * dell'utente, tranne l'informazioni relativa ai profili associati, che può ricavare
     * dalle variabili del Component "dipendente" e "pm".
     */
    addUser(event, name: string, surname: string, username: string,
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
        this._router.navigate( ['Admin'] );

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

}
