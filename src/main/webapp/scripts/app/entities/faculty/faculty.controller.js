'use strict';

angular.module('univerApp')
    .controller('FacultyController', function ($scope, Faculty) {
        $scope.facultys = [];
        $scope.loadAll = function() {
            Faculty.query(function(result) {
               $scope.facultys = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Faculty.get({id: id}, function(result) {
                $scope.faculty = result;
                $('#saveFacultyModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.faculty.id != null) {
                Faculty.update($scope.faculty,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Faculty.save($scope.faculty,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Faculty.get({id: id}, function(result) {
                $scope.faculty = result;
                $('#deleteFacultyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Faculty.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFacultyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveFacultyModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.faculty = {title: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
