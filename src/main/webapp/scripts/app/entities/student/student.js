'use strict';

angular.module('univerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('student', {
                parent: 'entity',
                url: '/student',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Students'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/students.html',
                        controller: 'StudentController'
                    }
                },
                resolve: {
                }
            })
            .state('studentDetail', {
                parent: 'entity',
                url: '/student/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Student'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/student-detail.html',
                        controller: 'StudentDetailController'
                    }
                },
                resolve: {
                }
            });
    });
