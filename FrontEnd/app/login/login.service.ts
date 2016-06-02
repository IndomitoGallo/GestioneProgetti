import {Injectable} from 'angular2/core';
import {BackEndURL} from '../backEndURL';
import {Http, Response, Headers, RequestOptions, HTTP_PROVIDERS} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';

@Injectable()
export class LoginService {

    constructor (private http: Http) {}

    login (username: string, password: string, profile: number): Observable<string>  {

        var loginData = [username, password, profile];
        let body = JSON.stringify(loginData);
        //let body = [username, password, profile];
        console.log("RequestBody = " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(BackEndURL + '/login', body, options)
                        .map(this.extractData)
                        .catch(this.handleError);

    }

    /*
     * Il metodo extractData è un'utility per gestire la risposta dal server
     * e quindi parsare bene il json.
     */
    private extractData(res: Response) {
        console.log("Response = " + res.json());
        if (res.status < 200 || res.status >= 300) {
            throw new Error('Bad response status: ' + res.status);
        }
        let body = JSON.stringify(res.json());
        console.log("ResponseBody = " + body);
        return body || { };
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleError (error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 400) {
            errMsg = "Verificare che tutti i campi siano stati inseriti correttamente";
        }
        else {
            errMsg = "Non è possibile al momento effettuare il login";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

}
