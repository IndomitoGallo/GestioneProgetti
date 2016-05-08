System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var Project;
    return {
        setters:[],
        execute: function() {
            Project = (function () {
                function Project(id, name, description, status, budget, cost) {
                    this.id = id;
                    this.name = name;
                    this.description = description;
                    this.status = status;
                    this.budget = budget;
                    this.cost = cost;
                }
                return Project;
            }());
            exports_1("Project", Project);
        }
    }
});
//# sourceMappingURL=project.js.map