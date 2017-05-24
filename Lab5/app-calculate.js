app.controller("MapController", [ '$scope', 
    function($scope) {
        angular.extend($scope, {
            turin: {
                        lat: 45.07,
                        lng: 7.69,
                        zoom: 13
                    }
            /*,
            paths: DataProvider.loadPath()        
            */
        });
    }
]);
/*
app.controller('MainCtrl', ['$scope', 'RoutesDataProvider',
    function ($scope, RoutesDataProvider) {
        //this.test = RoutesDataProvider.loadAll();
        this.allRoutes = RoutesDataProvider.loadAll();
        this.findRouteFn = function() {
            this.route = RoutesDataProvider.loadByPositions(null,null);
        }
        //console.log($scope.allPaths);
    }
]);
*/

/*
app.factory('RoutesDataProvider', ['Routes',
    function (routes) {
        return {
            loadAll: function() {
                return routes.routes;
            },
            loadByPositions: function(start,stop) {
                // return a random path
                var i = Math.floor(Math.random() * (routes.routes.length - 0));
                return routes.routes[i];
            }
        };
    }
]);
*/

app.directive('myDirective', function () {
  /*
  return {

  };
  */
});


