app.controller('MainCtrl', [ '$scope', 'LinesDataProvider',  'leafletBoundsHelpers', '$routeParams', '$location',
    function ($scope, LinesDataProvider, leafletBoundsHelpers, $routeParams, $location) {

        this.lineID = $routeParams.lineID;

        this.lines = LinesDataProvider.load();

        this.goToLink = function(line) {
          $location.path('#!/lines/' + line.line);
        };

        angular.extend($scope, {
            // center: {
            //             lat: 45.07,
            //             lng: 7.69,
            //             zoom: 13
            // },
            bounds: {},
            paths: {}
        });

        if ($routeParams.lineID) {
            var PathInfo = LinesDataProvider.loadPath($routeParams.lineID);
            $scope.paths = PathInfo.paths;
            $scope.bounds = leafletBoundsHelpers.createBoundsFromArray(PathInfo.bounds);
        };

    }
]);

// app.controller('TryMapCtrl', [ '$scope', 'LinesDataProvider',  'leafletBoundsHelpers', '$routeParams',
//     function ($scope, LinesDataProvider, leafletBoundsHelpers, $routeParams) {

//             angular.extend($scope, {
//             center: {
//                         lat: 45.07,
//                         lng: 7.69,
//                         zoom: 13
//             },
//             bounds: {},
//             paths: {}
//         });

//         if ($routeParams.lineID) {
//             var PathInfo = LinesDataProvider.loadPath($routeParams.lineID);
//             $scope.paths = PathInfo.paths;
//             $scope.bounds = leafletBoundsHelpers.createBoundsFromArray(PathInfo.bounds);
//         };

//     }
// ]);



app.factory('LinesDataProvider', ['Linee', '$filter',
    function (linee,$filter) {
        return {
            load : function () { return linee.lines; },
            query : function (id) {
                for (var i=0; i < linee.lines.length; i++ ) {
                    if (linee.lines[i].line == id)
                        return linee.lines[i].stops;
                }
            },
            loadPath : function (id) {
                var PathInfo = new Object();
                var max_lat,max_lng,min_lat,min_lng;
                min_lat = 90; max_lat = -90; min_lng = 180; max_lng = -180;
                var paths = {
                    p1 : {
                        type : "polyline",
                        weight: 4,
                        color: 'red',
                        message: "<h3>Line: " + id + "</h3>",
                        latlngs : []
                    }
                };
                // TODO continue developing
                // https://stackoverflow.com/questions/18234442/angularjs-from-a-factory-how-can-i-call-another-function
                this.query(id).forEach( function (stop) {
                    var point = new Object();
                    var stop = $filter('filter')(linee.stops, function (s) {return s.id === stop;})[0];
                    point.lat = stop.latLng[0];
                    point.lng = stop.latLng[1];
                    paths.p1.latlngs.push(point);
                    if (point.lat < min_lat)
                        min_lat = point.lat;
                    if (point.lat > max_lat)
                        max_lat = point.lat;                                
                    if (point.lng < min_lng)
                        min_lng = point.lng;
                    if (point.lng > max_lng)
                        max_lng = point.lng;
                });

                PathInfo.bounds = [
                                    [max_lat, max_lng],
                                    [min_lat, min_lng]
                                ];
                PathInfo.paths = paths;                

                return PathInfo;
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


