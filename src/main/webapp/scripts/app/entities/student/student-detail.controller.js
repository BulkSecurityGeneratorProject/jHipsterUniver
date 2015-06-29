'use strict';

angular.module('univerApp')
    .controller('StudentDetailController', function ($scope, $stateParams, Student, Faculty) {
        $scope.student = {};
        $scope.load = function (id) {
            Student.get({id: id}, function(result) {
              $scope.student = result;
            });
        };
        $scope.load($stateParams.id);
    });
