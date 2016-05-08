System.register(['angular2/core', 'angular2/router'], function(exports_1, context_1) {
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
    var core_1, router_1;
    var AdminAddUserComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            }],
        execute: function() {
            /*
             * Qui di seguito dichiariamo il @Component AdminAddUserComponent,
             * il cui Html template si trova nel file "admin-addUser.html".
             */
            AdminAddUserComponent = (function () {
                //Costruttore inizializzato con AdminService e Router (Dependency Injection)
                function AdminAddUserComponent(_router) {
                    this._router = _router;
                    //profili da scegliere per l'utente creato
                    this.dipendente = false;
                    this.pm = false;
                }
                /*
                 * La funzione addUser prende in input tutti i campi del form di creazione
                 * dell'utente, tranne l'informazioni relativa ai profili associati, che può ricavare
                 * dalle variabili del Component "dipendente" e "pm".
                 */
                AdminAddUserComponent.prototype.addUser = function (event, name, surname, username, email, pwd, skill) {
                    //La seguente riga di codice serve soltanto ad impedire il ricaricamento della pagina
                    event.preventDefault();
                    if (!this.dipendente && !this.pm) {
                        this.errorMessage = "Devi scegliere almeno un profilo tra quelli proposti";
                    }
                    else if (!this.dipendente && this.pm) {
                    }
                    else if (this.dipendente && !this.pm) {
                    }
                    else if (this.dipendente && this.pm) {
                    }
                    //Torno alla pagina principale dell'Amministratore
                    this._router.navigate(['Admin']);
                };
                /*
                 * La seguente funzione è gestita nell'html dalle checkbox: quando l'amministratore
                 * clicca su una delle checkbox cambia lo stato delle variabile interne del
                 * Component: "dipendente" e "pm". In questo modo, quando vengono inviati i dati al form
                 * la funzione di creazione dell'utente conosce quali e quanti profili sono stati scelti.
                 */
                AdminAddUserComponent.prototype.isSelected = function (profilo) {
                    if (profilo == "dip") {
                        if (this.dipendente) {
                            this.dipendente = false;
                        }
                        else
                            this.dipendente = true;
                    }
                    else {
                        if (this.pm) {
                            this.pm = false;
                        }
                        else
                            this.pm = true;
                    }
                };
                AdminAddUserComponent = __decorate([
                    core_1.Component({
                        templateUrl: 'app/admin/admin-addUser.html',
                        directives: [router_1.ROUTER_DIRECTIVES]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router])
                ], AdminAddUserComponent);
                return AdminAddUserComponent;
            }());
            exports_1("AdminAddUserComponent", AdminAddUserComponent);
        }
    }
});
//# sourceMappingURL=admin-addUser.component.js.map