app.controller('CalculateController', ['$scope', 'PathsDataProvider', 'leafletBoundsHelpers',
    function ($scope, PathsDataProvider, leafletBoundsHelpers) {
        angular.extend($scope, {
            turin: {
                        lat: 45.07,
                        lng: 7.69,
                        zoom: 13
            },
            paths: {},
            bounds: {}
        });

        
        this.calculate = function(){
            //console.log(this.source + "->" + this.destination);

            PathsDataProvider.setSource(this.source);
            PathsDataProvider.setDestination(this.destination);

            var PathInfo = PathsDataProvider.getPath();

            $scope.paths = PathInfo.paths;
            $scope.bounds = leafletBoundsHelpers.createBoundsFromArray(PathInfo.bounds);
        }
    }
]);

app.factory('PathsDataProvider', [ 'Percorsi',
    function (percorsi) {
        var source, destination;

        return {
            setSource: function(src){
                source = src;
            },
            setDestination: function(dst){
                destination = dst;
            },
            getPath: function() {
                // use source and destination

                // return a random path
                
                var i = Math.floor(Math.random() * (percorsi.paths.length));

                var PathInfo = new Object();
                var max_lat,max_lng,min_lat,min_lng;
                min_lat = 90; max_lat = -90; min_lng = 180; max_lng = -180;
                var paths = {
                    p1 : {
                        type : "polyline",
                        weight: 4,
                        color: 'green',
                        latlngs : []
                    }
                };

                percorsi.paths[i].forEach( function (edge) {
                    /* {
                        "idSource": "331",
                        "idDestination": "333",
                        "latSrc": 45.05624,
                        "lonSrc": 7.66459,
                        "latDst": 45.05883,
                        "lonDst": 7.66633,
                        "mode": false,
                        "cost": 318,
                        "edgeLine": "S05"
                    } */
                    var pointSrc = new Object();
                    var pointDst = new Object();

                    //var stop = $filter('filter')(linee.stops, function (s) {return s.id === stop;})[0];

                    pointSrc.lat = edge.latSrc;
                    pointSrc.lng = edge.lonSrc;

                    pointDst.lat = edge.latSrc;
                    pointDst.lng = edge.lonSrc;

                    paths.p1.latlngs.push(pointDst);
                    paths.p1.latlngs.push(pointSrc);

                    if (pointSrc.lat < min_lat)
                        min_lat = pointSrc.lat;
                    if (pointDst.lat < min_lat)
                        min_lat = pointDst.lat;

                    if (pointSrc.lat > max_lat)
                        max_lat = pointSrc.lat;
                    if (pointDst.lat > max_lat)
                        max_lat = pointDst.lat;

                    if (pointSrc.lng < min_lng)
                        min_lng = pointSrc.lng;
                    if (pointDst.lng < min_lng)
                        min_lng = pointDst.lng;

                    if (pointSrc.lng > max_lng)
                        max_lng = pointSrc.lng;
                    if (pointDst.lng > max_lng)
                        max_lng = pointDst.lng;
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