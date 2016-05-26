import { Component }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, ROUTER_DIRECTIVES } from 'angular2/router';

import { Profile }             from '../model/profile';
import { User }                from '../model/user';
import { LoginService }        from './login.service';

/*
 * Qui di seguito dichiariamo che il @Component AppComponent si riferisce al tag <my-app>,
 * che viene tradotto nel contenuto del campo "templateUrl", ovvero nel file "login-form.html".
 */
@Component({
    templateUrl: 'app/login/login-form.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [LoginService]
})

/*
 * La classe LoginComponent contiene un array di profili che vengono visualizzati nella schermata di login
 * e l'utente che vuole loggarsi ne deve selezionare uno, in modo tale che l'id corrispondente
 * venga passato al metodo di login.
 */
export class LoginComponent {

    errorMessage: string;

    profiles = [
         new Profile(1, 'Amministratore'),
         new Profile(2, 'Controller'),
         new Profile(3, 'Dipendente'),
         new Profile(4, 'Project Manager')
    ];

    //sessionId = "sessione di prova";
    sessionId: string;

    //Costruttore inizializzato con LoginService e Router (Dependency Injection)
    constructor(private _loginService: LoginService, private _router: Router) { }

    //METODO DI LOGIN
    login(event, username: string, password: string, profile: number) {

        //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
        event.preventDefault();
        //rendo visibile il router all'interno della funzione
        var router = this._router;

        // chiamata alla procedura di Login del Service
        this._loginService.login(username, password, profile)
                         .subscribe(
                             function(session) {
                                this.sessionId = session;
                                console.log("SessionID = " + this.sessionId);
                                if(profile == 1) {
                                  router.navigate( ['Admin', { sessionId: this.sessionId }] );
                                }
                                if(profile == 2) {
                                  router.navigate( ['Controller', { sessionId: this.sessionId }] );
                                }
                                if(profile == 3) {
                                  router.navigate( ['Employee', { sessionId: this.sessionId }] );
                                }
                                if(profile == 4) {
                                  router.navigate( ['PM', { sessionId: this.sessionId }] );
                                }
                             },
                             error =>  this.errorMessage = "Verificare che i dati inseriti siano corretti"
                          );

    }

}
