var app = angular.module('App', ['ngRoute', 'ngResource', 'ui-leaflet'])

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'main.html',
            controller: 'MainCtrl',
            controllerAs: 'ctrl'
        })
        .when('/map', {
            templateUrl: 'map.html',
            controller: 'MapController'
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

app.controller("MapController", [ '$scope', 'DataProvider', function($scope, DataProvider) {
    angular.extend($scope, {
        turin: {
                    lat: 45.07,
                    lng: 7.69,
                    zoom: 13
                },
        paths: DataProvider.loadPath()        
    });
}]);

app.factory('DataProvider', ['Linee', '$filter',
    function (linee,$filter) {
        return {
            load : function () { return linee.lines; },
            query : function (id) {
            linee.lines.forEach( function (line) {
                if (line.line == id)
                    return line;
            })},
            loadPath : function () { 
                var paths = {
                    p1 : {
                        type : "polyline",
                        weight: 4,
                        color: 'red',
                        message: "<h3>Line: METRO</h3>",
                        latlngs : []
                    }
                };
                
                linee.lines[0].stops.forEach( function (stop) {
                    var point = new Object();
                    var stop = $filter('filter')(linee.stops, function (s) {return s.id === stop;})[0];
                    point.lat = stop.latLng[0];
                    point.lng = stop.latLng[1];
                    paths.p1.latlngs.push(point)
                });
                return paths;
            }
        };
    }
]);



app.directive('myDirective', function () {
    /*
  return {
  };
  */
});


