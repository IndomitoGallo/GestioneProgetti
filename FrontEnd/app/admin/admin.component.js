System.register(['angular2/core', 'angular2/router', '../model/user'], function(exports_1, context_1) {
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
    var core_1, router_1, user_1;
    var AdminComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (user_1_1) {
                user_1 = user_1_1;
            }],
        execute: function() {
            /*
             * Qui di seguito dichiariamo il @Component AdminComponent,
             * il cui Html template si trova nel file "admin.html".
             * Tale componente gestisce la pagina principale del profilo Amministratore,
             * e quindi la tabella degli utenti da visualizzare.
             */
            AdminComponent = (function () {
                //Costruttore inizializzato con AdminService e Router (Dependency Injection)
                function AdminComponent(_router) {
                    this._router = _router;
                    this.users = [
                        new user_1.User(1, 'Nome', 'Cognome', 'IndomitoGallo', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
                        new user_1.User(2, 'Nome', 'Cognome', 'LorenzoB', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
                        new user_1.User(3, 'Nome', 'Cognome', 'Gaudo', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
                        new user_1.User(4, 'Nome', 'Cognome', 'Aizevs', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
                        new user_1.User(3, 'Nome', 'Cognome', 'LukCame', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false),
                        new user_1.User(4, 'Nome', 'Cognome', 'Dav33', 'ciccio@example.it', 'pellicciaus', 'segaiolo', false)
                    ];
                }
                AdminComponent.prototype.displayUser = function (userId) {
                    //L'Amministratore viene reindirizzato alla pagina di visualizzazione di un utente
                    this._router.navigate(['ViewUser']);
                };
                AdminComponent.prototype.updateUser = function (userId) {
                    //L'Amministratore viene reindirizzato alla pagina di aggiornamento di un utente
                    this._router.navigate(['UpdateUser']);
                };
                AdminComponent.prototype.redirectCreation = function () {
                    //L'Amministratore viene reindirizzato alla pagina di creazione di un utente
                    this._router.navigate(['AddUser']);
                };
                AdminComponent = __decorate([
                    core_1.Component({
                        templateUrl: 'app/admin/admin.html',
                        directives: [router_1.ROUTER_DIRECTIVES]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router])
                ], AdminComponent);
                return AdminComponent;
            }());
            exports_1("AdminComponent", AdminComponent);
        }
    }
});
//# sourceMappingURL=admin.component.js.map