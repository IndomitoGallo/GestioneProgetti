import { Component, OnInit }           from 'angular2/core';
import { HTTP_PROVIDERS }      from 'angular2/http';
import { Router, RouteParams, ROUTER_DIRECTIVES } from 'angular2/router';

import { User }       from '../model/user';
import { Profile }    from '../model/profile';
import { UserProfiles } from '../model/userProfiles';

import { AdminService } from './admin.service';

/*
 * Qui di seguito dichiariamo il @Component AdminViewUserComponent,
 * il cui Html template si trova nel file "admin-viewUser.html".
 */
@Component({
    templateUrl: 'app/admin/admin-viewUser.html',
    directives: [ ROUTER_DIRECTIVES ],
    providers: [AdminService]
})

export class AdminViewUserComponent implements OnInit {

    errorMessage: string;

    sessionId: string;

    userId: string;

    active: boolean = false;

    //User data
    user: User;
    profili: number[];
    profiles: string;
    seniority: string;

    //Costruttore inizializzato con AdminService e Router (Dependency Injection)
    constructor(private _adminService: AdminService, private _router: Router, private routeParams: RouteParams) { }

    ngOnInit() {
        //Recupero l'id della sessione
        this.sessionId = this.routeParams.get('sessionId');
        console.log("ViewUser SESSION: " + this.sessionId);
        //Recupero l'id dell'utente da visualizzare
        this.userId = this.routeParams.get('userId');
        console.log("ViewUser USER: " + this.userId);
        //carico dal server le info dell'utente da visualizzare
        this._adminService.getUser(this.sessionId, this.userId)
                         .subscribe(
                               userProfiles => {
                                  this.user = userProfiles.user;
                                  //convert userProfiles.profiles to String
                                  var profile = userProfiles.profiles.pop();
                                  if(profile == 1) {
                                      this.profiles = "Amministratore";
                                  } else if(profile == 2) {
                                      this.profiles = "Controller";
                                  } else if(profile == 3) {
                                      if(userProfiles.profiles.pop() == 4) {
                                          this.profiles = "Dipendente, Project Manager";
                                      }
                                      else {
                                          this.profiles = "Dipendente";
                                      }
                                  } else if(profile == 4) {
                                      if(userProfiles.profiles.pop() == 3) {
                                          this.profiles = "Dipendente, Project Manager";
                                      } else {
                                          this.profiles = "Project Manager";
                                      }
                                  }
                                  console.log("Profiles: " + JSON.stringify(this.profiles));

                                  //convert Seniority to String
                                  /*if(this.user.seniority == 1) {
                                      this.seniority = "Junior";
                                  } else if (this.user.seniority == 2) {
                                      this.seniority = "Middle";
                                  } else if (this.user.seniority == 3) {
                                      this.seniority = "Senior";
                                  } else {
                                      this.seniority = "Livello di seniority non ancora assegnato";
                                  }*/

                                  this.active = true;
                               },
                               error =>  this.errorMessage = <any>error
                          );

    }

    /*
     * Questa funzione viene attivata dal tasto "Torna Indietro" e riporta l'utente
     * alla pagina precedentemente visualizzata.
     */
    goBack() {
        window.history.back();
    }

}
