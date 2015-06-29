'use strict';

angular.module('univerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher', {
                parent: 'entity',
                url: '/teacher',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Teachers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/teacher/teachers.html',
                        controller: 'TeacherController'
                    }
                },
                resolve: {
                }
            })
            .state('teacherDetail', {
                parent: 'entity',
                url: '/teacher/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Teacher'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/teacher/teacher-detail.html',
                        controller: 'TeacherDetailController'
                    }
                },
                resolve: {
                }
            });
    });
