'use strict';

angular.module('univerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


