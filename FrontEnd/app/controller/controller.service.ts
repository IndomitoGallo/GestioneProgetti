import {Injectable} from 'angular2/core';
import {BackEndURL} from '../backEndURL';
import {Http, Response, Headers, RequestOptions, HTTP_PROVIDERS} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';

import { User } from '../model/user';
import { Project } from '../model/project';
import { ProjectEmployeesHours } from '../model/projectEmployeesHours';
import { PmsEmployees }    from '../model/pmsEmployees';
import { ProjectEmployees }    from '../model/projectEmployees';
import { ProjectForm }    from '../model/projectForm';

@Injectable()
export class ControllerService {

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

    //getProjects
    getProjects(sessionId: string): Observable<Project[]> {
        return this.http.get(BackEndURL + '/projects?sessionId=' + sessionId)
                        .map(this.extractData)
                        .catch(this.handleErrorProjects);
    }

    //getProject
    getProject(sessionId: string, projectId: string): Observable<ProjectEmployeesHours> {
        return this.http.get(BackEndURL + '/projects/' + projectId + '?sessionId=' + sessionId)
                        .map(this.extractData)
                        .catch(this.handleErrorProject);
    }

    //getPmsEmployees
    getPmsEmployees(sessionId: string): Observable<PmsEmployees> {
        return this.http.get(BackEndURL + '/projects/pmsemployees?sessionId=' + sessionId)
                        .map(this.extractData)
                        .catch(this.handleErrorPmsEmployees);
    }

    //getProjectForm
    getProjectForm(sessionId: string, projectId: string): Observable<ProjectForm> {
        return this.http.get(BackEndURL + '/projects/form/' + projectId + '?sessionId=' + sessionId)
                        .map(this.extractData)
                        .catch(this.handleErrorProjectForm);
    }

    //insertProject
    addProject(sessionId: string, project: Project, employees: number[]): Observable<string> {

        var projectEmployees: ProjectEmployees = new ProjectEmployees(project, employees, sessionId);
        let body = JSON.stringify(projectEmployees);
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(BackEndURL + '/projects', body, options)
                              .map(this.response)
                              .catch(this.handleErrorAdd);

    }

    //updateProject
    updateProject(sessionId: string, project: Project, employees: number[]): Observable<string> {

        let body = JSON.stringify({ project, employees, sessionId });
        console.log("RequestBody: " + body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.put(BackEndURL + '/projects/' + project.id, body, options)
                              .map(this.response)
                              .catch(this.handleErrorUpdate);

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
        return body || { };
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorProject(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else {
            errMsg = "Non è possibile al momento visionare le informazioni del progetto selezionato";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorProjects(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else {
            errMsg = "Non è possibile al momento visionare la lista dei progetti";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorPmsEmployees(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else {
            errMsg = "Non è possibile al momento visionare la lista dei Project Manager e dei Dipendenti";
        }
        console.error("ErrorMessage: " + errMsg);
        return Observable.throw(errMsg);
    }

    /*
     * Il metodo handleError serve a catturare un eventuale errore proveniente dal server.
     */
    private handleErrorProjectForm(error: any) {
        console.log("Error: " + JSON.stringify(error));
        let errMsg;
        if(error.status == 401) {
            errMsg = "La sessione è scaduta: effettuare nuovamente il Login"
        }
        else {
            errMsg = "Non è possibile al momento visionare le informazioni del progetto che si vuole aggiornare";
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
        else if(error.status == 400) { //Bad Request
            errMsg = "Verificare che tutti i campi siano stati inseriti correttamente: non possono esistere due progetti con lo stesso nome";
        }
        else { //Service Unavailable
            errMsg = "Non è possibile al momento creare un nuovo progetto. Verificare il campo Name: non possono esistere due progetti con lo stesso nome";
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
        else if(error.status == 400) { //Bad Request
            errMsg = "Verificare che tutti i campi siano stati inseriti correttamente: non possono esistere due progetti con lo stesso nome";
        }
        else { //Service Unavailable
            errMsg = "Non è possibile al momento aggiornare il progetto selezionato. Verificare il campo Name: non possono esistere due progetti con lo stesso nome";
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
