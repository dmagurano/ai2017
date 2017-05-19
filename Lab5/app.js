var app = angular.module('App', ['ngRoute', 'ngResource'])

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'main.html',
            controller: 'MainCtrl',
            controllerAs: 'ctrl'
        })
        .when('/map', {
            templateUrl: 'map.html',
            controller: 'SimpleMapController'
        })
        .otherwise({ redirectTo: "/" });

    // configure html5 to get links working on jsfiddle
    // $locationProvider.html5Mode(true);
});

app.controller('MainCtrl', ['$scope', 'DataProvider',
    function ($scope, DataProvider) {
        this.lines = DataProvider.load();
    }
]);

app.controller("SimpleMapController", [ '$scope', function($scope) {
    angular.extend($scope, {
        defaults: {
            scrollWheelZoom: false
        }
    });
}]);

app.factory('DataProvider', ['Linee',
    function (linee) {
        return {
            load : function () { return linee.lines; },
            query : function (id) {
                linee.lines.forEach( function (line) {
                if (line.line == id)
                    return line;
            })}
        };
    }
]);



app.directive('myDirective', function () {
    /*
  return {
  };
  */
});


