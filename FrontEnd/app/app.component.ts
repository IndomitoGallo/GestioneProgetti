import { Component }         from 'angular2/core';
//Librerie per richieste/risposte Http
import { HTTP_PROVIDERS }    from 'angular2/http';
//librerie per il meccanismo di Routing
import { RouteConfig, ROUTER_DIRECTIVES, RouterOutlet, ROUTER_PROVIDERS } from 'angular2/router';

import { LoginComponent }    from './login.component';
import { AdminComponent }    from './admin/admin.component';
import { ControllerComponent }    from './controller/controller.component';
import { EmployeeComponent }    from './employee/employee.component';
import { PMComponent }    from './pm/pm.component';

/*
 * Qui di seguito dichiariamo che il @Component AppComponent si riferisce al tag <my-app>,
 * che viene tradotto nel contenuto del campo "template", ovvero nel tag '<router-outlet></router-outlet>'.
 * RouterOutlet è un componente che mostra le views all'interno del tag <router-outlet> a seconda del path
 * che viene richiesto.
 * Con il campo directives stiamo dichiarando che il suddetto tag verrà gestito/tradotto dalle
 * ROUTER_DIRECTIVES, dichiarate in @RouteConfig.
 * Con il campo providers stiamo dichiarando che l'applicazione utilizzerà i Servizi Http e quelli
 * offerti dai file "service" della nostra applicazione.
 */
@Component({
    selector: 'my-app',
    template: '<router-outlet></router-outlet>',
    directives: [ ROUTER_DIRECTIVES ],
    providers:  [ HTTP_PROVIDERS, ROUTER_PROVIDERS
                  //, LoginService, AdminService, ControllerService, EmployeeService, PMService
                ]
})

/*
 * Il decoratore @RouteConfig crea un nuovo router.
 * Applichiamo tale decoratore al modulo AppComponent, che in tal modo sarà l'host del router.
 * All'interno di @RouteConfig() dichiariamo un array di Route Definitions.
 * Ogni definizione si traduce in una Route che ha:
 * 1) path - l'URL della Route
 * 2) name - nome della Route
 * 3) component - il componente associato con la route
 * Quando l'URL del browser matcha con una delle Route dichiarate, viene creata o
 * restituita un'istanza del Componente associato e viene mostrato nella Single-Page.
 */

@RouteConfig([
    {path:'/',               name: 'Login',         component: LoginComponent, useAsDefault: true},
    {path:'/admin',          name: 'Admin',         component: AdminComponent},
    {path:'/controller',     name: 'Controller',    component: ControllerComponent},
    {path:'/employee',       name: 'Employee',      component: EmployeeComponent},
    {path:'/pm',             name: 'PM',            component: PMComponent}
])

export class AppComponent { }
