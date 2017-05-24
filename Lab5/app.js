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
            controller: 'MapController',
        })
        .otherwise({ redirectTo: "/" });

    // configure html5 to get links working on jsfiddle
    // $locationProvider.html5Mode(true);
});
