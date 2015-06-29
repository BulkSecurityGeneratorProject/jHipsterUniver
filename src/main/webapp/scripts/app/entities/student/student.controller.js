'use strict';

angular.module('univerApp')
    .controller('StudentController', function ($scope, Student, Faculty) {
        $scope.students = [];
        $scope.facultys = Faculty.query();
        $scope.loadAll = function() {
            Student.query(function(result) {
               $scope.students = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Student.get({id: id}, function(result) {
                $scope.student = result;
                $('#saveStudentModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.student.id != null) {
                Student.update($scope.student,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Student.save($scope.student,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Student.get({id: id}, function(result) {
                $scope.student = result;
                $('#deleteStudentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Student.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStudentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveStudentModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.student = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
