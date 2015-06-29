'use strict';

angular.module('univerApp')
    .controller('TeacherController', function ($scope, Teacher, Course, User) {
        $scope.teachers = [];
        $scope.courses = Course.query();
        $scope.users = User.query();
        $scope.loadAll = function() {
            Teacher.query(function(result) {
               $scope.teachers = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Teacher.get({id: id}, function(result) {
                $scope.teacher = result;
                $('#saveTeacherModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.teacher.id != null) {
                Teacher.update($scope.teacher,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Teacher.save($scope.teacher,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Teacher.get({id: id}, function(result) {
                $scope.teacher = result;
                $('#deleteTeacherConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Teacher.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTeacherConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveTeacherModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.teacher = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
