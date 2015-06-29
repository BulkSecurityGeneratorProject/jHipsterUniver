'use strict';

angular.module('univerApp')
    .controller('CourseDetailController', function ($scope, $stateParams, Course, Faculty) {
        $scope.course = {};
        $scope.load = function (id) {
            Course.get({id: id}, function(result) {
              $scope.course = result;
            });
        };
        $scope.load($stateParams.id);
    });
