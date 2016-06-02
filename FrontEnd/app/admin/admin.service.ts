import {Injectable} from 'angular2/core';
import {BackEndURL} from '../backEndURL';
import {Http, Response, Headers, RequestOptions, HTTP_PROVIDERS} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';

import { User } from '../model/user';
import { UserProfiles } from '../model/userProfiles';

@Injectable()
export class AdminService {

    constructor (private http: Http) {}

    //retrieve Users
    getUsers (sessionId : string): Observable<User[]>  {
        return this.http.get(BackEndURL + '/users?sessionId=' + sessionId)
                        .map(this.extractData)
                        .catch(this.handleErrorUsers);
    }

    //retrieve User
    getUser (sessionId : string, userId: string): Observable<UserProfiles>  {
        return this.http.get(BackEndURL + '/users/' + userId + '?sessionId=' + sessionId)
                        .map(this.extractData)
                        .catch(this.handleErrorUser);
    }

    //add User
    addUser(userProfiles: UserProfiles): Observable<string> {

        var user = userProfiles.user;
        var profiles = userProfiles.profiles;
        var sessionId = userProfiles.sessionId;

        let body = JSON.stringify({ user, profiles, sessionId });
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(BackEndURL + '/addUser', body, options)
                              .map(this.response)
                              .catch(this.handleErrorAdd);

    }

    //add User
    updateUser(userProfiles: UserProfiles): Observable<string> {

        var user = userProfiles.user;
        var profiles = userProfiles.profiles;
        var sessionId = userProfiles.sessionId;

        let body = JSON.stringify({ user, profiles, sessionId });
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.put(BackEndURL + '/users/' + user.id, body, options)
                              .map(this.response)
                              .catch(this.handleErrorUpdate);

    }

    //add User
    deleteUser(sessionId: string, userId: string): Observable<string> {

        let body = JSON.stringify({ sessionId });
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.delete(BackEndURL + '/users/' + userId + '?sessionId=' + sessionId)
                              .map(this.response)
                              .catch(this.handleErrorDelete);

    }

    //logout
    logout(sessionId: string): Observable<string> {

        let body = JSON.stringify({ sessionId });
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(BackEndURL + '/logout', body, options)
                              .map(this.response)
                              .catch(this.handleErrorLogout);

    }

    /*
     * Il metodo extractData è un'utility per gestire la risposta dal server
     * e quindi parsare bene il json.
     */
    private extractData(res: Response) {
        if (res.status < 200 || res.status >= 300) {
          throw new Error('Bad response status: ' + res.status);
        }
        let body = res.json();
        console.log("Body = " + JSON.stringify(body));
        return body || { };
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorUsers(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else {
            errMsg = "Non è possibile al momento visionare la lista degli utenti";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorUser(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else {
            errMsg = "Non è possibile al momento visionare le informazioni dell'utente selezionato";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorAdd(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else if(error.status == 400) {
            errMsg = "Username o e-mail già esistenti"
        }
        else {
            errMsg = "Non è possibile al momento aggiungere un nuovo utente";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorUpdate(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else if(error.status == 400) {
            errMsg = "Username o e-mail già esistenti"
        }
        else {
            errMsg = "Non è possibile al momento aggiornare l'utente selezionato";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorDelete(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else {
            errMsg = "Non è possibile al momento eliminare l'utente selezionato";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorLogout(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg = "Non è possibile al momento effettuare il logout";
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo response è un'utility per gestire la risposta dal server
     * nel caso in cui non vengono restituiti dati al client.
     */
    private response(res: Response): string {
        if (res.status < 200 || res.status >= 300) {
          throw new Error('Bad response status: ' + res.status);
        }
        console.log("Body = " + JSON.stringify(res));
        return "success";
    }

}
