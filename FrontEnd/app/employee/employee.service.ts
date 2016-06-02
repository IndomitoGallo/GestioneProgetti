import {Injectable} from 'angular2/core';
import {BackEndURL} from '../backEndURL';
import {Http, Response, Headers, RequestOptions, HTTP_PROVIDERS} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';

@Injectable()
export class EmployeeService {

    constructor (private http: Http) {}

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
