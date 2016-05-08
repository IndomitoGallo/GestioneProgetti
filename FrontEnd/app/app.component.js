System.register(['angular2/core', 'angular2/http', 'angular2/router', './login/login.component', './admin/admin.component', './admin/admin-addUser.component', './admin/admin-viewUser.component', './admin/admin-updateUser.component', './controller/controller.component', './controller/projects/ctrl-projects.component', './controller/employees/ctrl-employees.component', './employee/employee.component', './pm/pm.component'], function(exports_1, context_1) {
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
    var core_1, http_1, router_1, login_component_1, admin_component_1, admin_addUser_component_1, admin_viewUser_component_1, admin_updateUser_component_1, controller_component_1, ctrl_projects_component_1, ctrl_employees_component_1, employee_component_1, pm_component_1;
    var AppComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (login_component_1_1) {
                login_component_1 = login_component_1_1;
            },
            function (admin_component_1_1) {
                admin_component_1 = admin_component_1_1;
            },
            function (admin_addUser_component_1_1) {
                admin_addUser_component_1 = admin_addUser_component_1_1;
            },
            function (admin_viewUser_component_1_1) {
                admin_viewUser_component_1 = admin_viewUser_component_1_1;
            },
            function (admin_updateUser_component_1_1) {
                admin_updateUser_component_1 = admin_updateUser_component_1_1;
            },
            function (controller_component_1_1) {
                controller_component_1 = controller_component_1_1;
            },
            function (ctrl_projects_component_1_1) {
                ctrl_projects_component_1 = ctrl_projects_component_1_1;
            },
            function (ctrl_employees_component_1_1) {
                ctrl_employees_component_1 = ctrl_employees_component_1_1;
            },
            function (employee_component_1_1) {
                employee_component_1 = employee_component_1_1;
            },
            function (pm_component_1_1) {
                pm_component_1 = pm_component_1_1;
            }],
        execute: function() {
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
            AppComponent = (function () {
                function AppComponent() {
                }
                AppComponent = __decorate([
                    core_1.Component({
                        selector: 'my-app',
                        template: '<router-outlet></router-outlet>',
                        directives: [router_1.ROUTER_DIRECTIVES],
                        providers: [http_1.HTTP_PROVIDERS, router_1.ROUTER_PROVIDERS
                        ]
                    }),
                    router_1.RouteConfig([
                        //Login
                        { path: '/', name: 'Login', component: login_component_1.LoginComponent, useAsDefault: true },
                        //Admin's Workflows
                        { path: '/admin', name: 'Admin', component: admin_component_1.AdminComponent },
                        { path: '/admin/addUser', name: 'AddUser', component: admin_addUser_component_1.AdminAddUserComponent },
                        { path: '/admin/viewUser', name: 'ViewUser', component: admin_viewUser_component_1.AdminViewUserComponent },
                        { path: '/admin/updateUser', name: 'UpdateUser', component: admin_updateUser_component_1.AdminUpdateUserComponent },
                        //Controller's Workflows
                        { path: '/controller', name: 'Controller', component: controller_component_1.ControllerComponent },
                        { path: '/controller/projects', name: 'CtrlProjects', component: ctrl_projects_component_1.CtrlProjectsComponent },
                        { path: '/controller/employees', name: 'CtrlEmployees', component: ctrl_employees_component_1.CtrlEmployeesComponent },
                        //Employee's Workflows
                        { path: '/employee', name: 'Employee', component: employee_component_1.EmployeeComponent },
                        //ProjectManager's Workflows
                        { path: '/pm', name: 'PM', component: pm_component_1.PMComponent }
                    ]), 
                    __metadata('design:paramtypes', [])
                ], AppComponent);
                return AppComponent;
            }());
            exports_1("AppComponent", AppComponent);
        }
    }
});
//# sourceMappingURL=app.component.js.map