'use strict';

angular.module('univerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('faculty', {
                parent: 'entity',
                url: '/faculty',
                data: {
                    roles: ['ROLE_ANONYMOUS','ROLE_ADMIN','ROLE_USER'],
                    pageTitle: 'Facultys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/faculty/facultys.html',
                        controller: 'FacultyController'
                    }
                },
                resolve: {}
            })
            .state('facultyDetail', {
                parent: 'entity',
                url: '/faculty/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Faculty'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/faculty/faculty-detail.html',
                        controller: 'FacultyDetailController'
                    }
                },
                resolve: {}
            });
    });
