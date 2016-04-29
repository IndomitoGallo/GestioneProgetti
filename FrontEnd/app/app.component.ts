import { Component }         from 'angular2/core';
import { HTTP_PROVIDERS }    from 'angular2/http';
import { Profile }           from './model/profile';
import { User }              from './model/user';
//import { UserComponent }     from './component/user.component';
//import { UserService }       from './service/user.service';

/*
 * Qui di seguito dichiariamo che il @Component AppComponent si riferisce al tag <my-app>,
 * che viene tradotto nel contenuto del campo "templateUrl", ovvero nel file "login-form.html".
 */
@Component({
    selector: 'my-app',
    templateUrl: 'app/html/login-form.html',
    providers:  [ HTTP_PROVIDERS
                  //, UserService
                ]
})

/*
 * La classe AppComponent contiene un array di profili che vengono visualizzati nella schermata di login
 * e l'utente che vuole loggarsi ne deve selezionare uno, in modo tale che l'id corrispondente
 * venga passato al metodo di login.
 */
export class AppComponent {

    profiles = [
       new Profile(1, 'Amministratore'),
       new Profile(2, 'Controller'),
       new Profile(3, 'Dipendente'),
       new Profile(4, 'Project Manager')
    ];
    
    errorMessage: string;
    user: User;

    //Costruttore inizializzato con UserService (Dependency Injection)
    constructor (
      //private _loginService: UserService
    ) {}

    //METODO DI LOGIN
    login(username: string, password: string, profile: number) {
         /*
         this._userService.login(username, password, profile).
                           subscribe(
                               user  => this.user = user,
                               error =>  this.errorMessage = <any>error
                           );
         */
    }

}
