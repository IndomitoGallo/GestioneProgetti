System.register(['angular2/core', 'angular2/router', '../../model/project'], function(exports_1, context_1) {
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
    var core_1, router_1, project_1;
    var CtrlProjectsComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (project_1_1) {
                project_1 = project_1_1;
            }],
        execute: function() {
            /*
             * Qui di seguito dichiariamo che il @Component CtrlProjectsComponent ha come URL-template
             * il contenuto del file 'app/controller/ctrl-projects.html'.
             */
            CtrlProjectsComponent = (function () {
                //Costruttore inizializzato con ControllerService e Router (Dependency Injection)
                function CtrlProjectsComponent(/*private _controllerService: ControllerService,*/ _router) {
                    this._router = _router;
                    this.projects = [
                        new project_1.Project(1, 'Progetto1', 'Descrizione', 'Status', 10000, 1000),
                        new project_1.Project(2, 'Progetto2', 'Descrizione', 'Status', 10000, 1000),
                        new project_1.Project(3, 'Progetto3', 'Descrizione', 'Status', 10000, 1000),
                        new project_1.Project(4, 'Progetto4', 'Descrizione', 'Status', 10000, 1000)
                    ];
                }
                CtrlProjectsComponent = __decorate([
                    core_1.Component({
                        templateUrl: 'app/controller/projects/ctrl-projects.html',
                        directives: [router_1.ROUTER_DIRECTIVES]
                    }), 
                    __metadata('design:paramtypes', [router_1.Router])
                ], CtrlProjectsComponent);
                return CtrlProjectsComponent;
            }());
            exports_1("CtrlProjectsComponent", CtrlProjectsComponent);
        }
    }
});
//# sourceMappingURL=ctrl-projects.component.js.map