'use strict';

angular.module('univerApp')
    .controller('MarkDetailController', function ($scope, $stateParams, Mark, Student, Course) {
        $scope.mark = {};
        $scope.load = function (id) {
            Mark.get({id: id}, function(result) {
              $scope.mark = result;
            });
        };
        $scope.load($stateParams.id);
    });
