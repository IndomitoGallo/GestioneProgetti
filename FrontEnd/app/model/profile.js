System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var Profile;
    return {
        setters:[],
        execute: function() {
            Profile = (function () {
                function Profile(id, name) {
                    this.id = id;
                    this.name = name;
                }
                return Profile;
            }());
            exports_1("Profile", Profile);
        }
    }
});
//# sourceMappingURL=profile.js.map