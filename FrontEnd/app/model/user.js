System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var User;
    return {
        setters:[],
        execute: function() {
            User = (function () {
                function User(id, name, surname, username, email, password, skill, isDeactivated) {
                    this.id = id;
                    this.name = name;
                    this.surname = surname;
                    this.username = username;
                    this.email = email;
                    this.password = password;
                    this.skill = skill;
                    this.isDeactivated = isDeactivated;
                }
                return User;
            }());
            exports_1("User", User);
        }
    }
});
//# sourceMappingURL=user.js.map