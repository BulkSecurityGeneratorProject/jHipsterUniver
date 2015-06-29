'use strict';

angular.module('univerApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
