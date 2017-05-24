/*
    TODO 
        different colour for going and return
        markers on bus stops, with info on lines
        different colour for on foot or by bus (better: different colour for each line)
        update table containing path details
        make edges follow the roads
*/

var app = angular.module('App', ['ngRoute', 'ngResource', 'ui-leaflet'])

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'main.html',
            controller: 'MainCtrl',
            controllerAs: 'ctrl'
        })
        .when('/calculate', {
            templateUrl: 'calculate.html',
            controller: 'CalculateController',
            controllerAs: 'ctrl'
        })
        .when('/lines/:lineID', {
            templateUrl: 'main.html',
            controller: 'MainCtrl',
            controllerAs: 'ctrl'
        })
        .otherwise({ redirectTo: "/" });

    // configure html5 to get links working on jsfiddle
    // $locationProvider.html5Mode(true);
});
