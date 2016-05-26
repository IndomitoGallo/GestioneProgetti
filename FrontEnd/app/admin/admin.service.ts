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
                        .catch(this.handleError);
    }

    //retrieve User
    getUser (sessionId : string, userId: string): Observable<UserProfiles>  {
        return this.http.get(BackEndURL + '/users/' + userId + '?sessionId=' + sessionId)
                        .map(this.extractData)
                        .catch(this.handleError);
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
                              .catch(this.handleError);

    }

    //add User
    deleteUser(sessionId: string, userId: string): Observable<string> {

        let body = JSON.stringify({ sessionId });
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.delete(BackEndURL + '/users/' + userId + '?sessionId=' + sessionId)
                              .map(this.response)
                              .catch(this.handleError);

    }

    //logout
    logout(sessionId: string): Observable<string> {

        let body = JSON.stringify({ sessionId });
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(BackEndURL + '/logout', body, options)
                              .map(this.response)
                              .catch(this.handleError);

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
    private handleError (error: any) {
        // In a real world app, we might send the error to remote logging infrastructure
        let errMsg = error.message || 'Server error';
        console.error(errMsg); // log to console instead
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
