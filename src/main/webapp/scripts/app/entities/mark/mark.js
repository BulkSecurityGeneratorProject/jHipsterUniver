'use strict';

angular.module('univerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mark', {
                parent: 'entity',
                url: '/mark',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Marks'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mark/marks.html',
                        controller: 'MarkController'
                    }
                },
                resolve: {
                }
            })
            .state('markDetail', {
                parent: 'entity',
                url: '/mark/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Mark'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mark/mark-detail.html',
                        controller: 'MarkDetailController'
                    }
                },
                resolve: {
                }
            });
    });
