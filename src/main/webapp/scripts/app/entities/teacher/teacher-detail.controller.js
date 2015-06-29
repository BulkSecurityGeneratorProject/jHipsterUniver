'use strict';

angular.module('univerApp')
    .controller('TeacherDetailController', function ($scope, $stateParams, Teacher, Course, User) {
        $scope.teacher = {};
        $scope.load = function (id) {
            Teacher.get({id: id}, function(result) {
              $scope.teacher = result;
            });
        };
        $scope.load($stateParams.id);
    });
