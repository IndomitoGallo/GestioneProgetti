System.register(['angular2/core', 'angular2/http', './model/profile'], function(exports_1, context_1) {
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
    var core_1, http_1, profile_1;
    var AppComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (profile_1_1) {
                profile_1 = profile_1_1;
            }],
        execute: function() {
            //import { UserComponent }     from './component/user.component';
            //import { UserService }       from './service/user.service';
            /*
             * Qui di seguito dichiariamo che il @Component AppComponent si riferisce al tag <my-app>,
             * che viene tradotto nel contenuto del campo "templateUrl", ovvero nel file "login-form.html".
             */
            AppComponent = (function () {
                //Costruttore inizializzato con UserService (Dependency Injection)
                function AppComponent() {
                    this.profiles = [
                        new profile_1.Profile(1, 'Amministratore'),
                        new profile_1.Profile(2, 'Controller'),
                        new profile_1.Profile(3, 'Dipendente'),
                        new profile_1.Profile(4, 'Project Manager')
                    ];
                }
                //METODO DI LOGIN
                AppComponent.prototype.login = function (username, password, profile) {
                    /*
                    this._userService.login(username, password, profile).
                                      subscribe(
                                          user  => this.user = user,
                                          error =>  this.errorMessage = <any>error
                                      );
                    */
                };
                AppComponent = __decorate([
                    core_1.Component({
                        selector: 'my-app',
                        templateUrl: 'app/html/login-form.html',
                        providers: [http_1.HTTP_PROVIDERS
                        ]
                    }), 
                    __metadata('design:paramtypes', [])
                ], AppComponent);
                return AppComponent;
            }());
            exports_1("AppComponent", AppComponent);
        }
    }
});
//# sourceMappingURL=app.component.js.map