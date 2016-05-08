System.register(['angular2/core', 'angular2/router', '../model/profile'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, router_1, profile_1;
    var LoginComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (profile_1_1) {
                profile_1 = profile_1_1;
            }],
        execute: function() {
            /*
             * Qui di seguito dichiariamo che il @Component AppComponent si riferisce al tag <my-app>,
             * che viene tradotto nel contenuto del campo "templateUrl", ovvero nel file "login-form.html".
             */
            LoginComponent = (function () {
                //Costruttore inizializzato con LoginService e Router (Dependency Injection)
                function LoginComponent(/*private _loginService: LoginService,*/ _router) {
                    this._router = _router;
                    this.profiles = [
                        new profile_1.Profile(1, 'Amministratore'),
                        new profile_1.Profile(2, 'Controller'),
                        new profile_1.Profile(3, 'Dipendente'),
                        new profile_1.Profile(4, 'Project Manager')
                    ];
                }
                //METODO DI LOGIN
                LoginComponent.prototype.login = function (event, username, password, profile) {
                    //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
                    event.preventDefault();
                    /* PROCEDURA DI LOGIN
                    this._userService.login(username, password, profile)
                                     .subscribe(
                                           user  => this.user = user,
                                           error =>  this.errorMessage = <any>error
                                      );
                    */
                    if (profile == 1) {
                        this._router.navigate(['Admin']);
                    }
                    if (profile == 2) {
                        this._router.navigate(['Controller']);
                    }
                    if (profile == 3) {
                        this._router.navigate(['Employee']);
                    }
                    if (profile == 4) {
                        this._router.navigate(['PM']);
                    }
                };
                LoginComponent = __decorate([
                    core_1.Component({
                        templateUrl: 'app/login/login-form.html',
                        directives: [router_1.ROUTER_DIRECTIVES]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router])
                ], LoginComponent);
                return LoginComponent;
            }());
            exports_1("LoginComponent", LoginComponent);
        }
    }
});
//# sourceMappingURL=login.component.js.map