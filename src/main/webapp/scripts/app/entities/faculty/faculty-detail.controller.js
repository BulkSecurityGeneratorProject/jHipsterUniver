'use strict';

angular.module('univerApp')
    .controller('FacultyDetailController', function ($scope, $stateParams, Faculty) {
        $scope.faculty = {};
        $scope.load = function (id) {
            Faculty.get({id: id}, function(result) {
              $scope.faculty = result;
            });
        };
        $scope.load($stateParams.id);
    });
