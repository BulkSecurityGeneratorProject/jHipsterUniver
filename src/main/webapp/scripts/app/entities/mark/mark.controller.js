'use strict';

angular.module('univerApp')
    .controller('MarkController', function ($scope, Mark, Student, Course) {
        $scope.marks = [];
        $scope.students = Student.query();
        $scope.courses = Course.query();
        $scope.loadAll = function() {
            Mark.query(function(result) {
               $scope.marks = result;
            });
        };
        $scope.loadAll(

        );


        $scope.showUpdate = function (id) {
            Mark.get({id: id}, function(result) {
                $scope.mark = result;
                $('#saveMarkModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.mark.id != null) {
                Mark.update($scope.mark,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Mark.save($scope.mark,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Mark.get({id: id}, function(result) {
                $scope.mark = result;
                $('#deleteMarkConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Mark.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMarkConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveMarkModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.mark = {rating: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
