//Librerie del Framework
import { bootstrap }      from 'angular2/platform/browser';
import { HTTP_PROVIDERS } from 'angular2/http';
import { ROUTER_PROVIDERS } from 'angular2/router';
import 'rxjs/Rx'; // Add all operators to Observable


import { AppComponent }   from './app.component';
bootstrap(AppComponent, [HTTP_PROVIDERS, ROUTER_PROVIDERS]);
