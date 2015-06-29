'use strict';

angular.module('univerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('course', {
                parent: 'entity',
                url: '/course',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Courses'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/course/courses.html',
                        controller: 'CourseController'
                    }
                },
                resolve: {
                }
            })
            .state('courseDetail', {
                parent: 'entity',
                url: '/course/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Course'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/course/course-detail.html',
                        controller: 'CourseDetailController'
                    }
                },
                resolve: {
                }
            });
    });
