app.controller('MainCtrl', ['$scope', 'LinesDataProvider',
    function ($scope, LinesDataProvider) {
        this.lines = LinesDataProvider.load();

        angular.extend($scope, {
            turin: {
                        lat: 45.07,
                        lng: 7.69,
                        zoom: 13
                    }
        });

        this.printOnMap = function(lineId){
            console.log("clicked on " + lineId);

            LinesDataProvider.loadPath(lineId);
        };
    }
]);

app.factory('LinesDataProvider', ['Linee', '$filter',
    function (linee,$filter) {
        return {
            load : function () { return linee.lines; },
            query : function (id) {

                linee.lines.forEach(
                    function(line){
                        if (line.line == id)
                            return line.stops;
                    }
                )
            },
            loadPath : function (id) {
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
                query(id).forEach( function (stop) {
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


