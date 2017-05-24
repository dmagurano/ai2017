app.controller('CalculateController', ['$scope', 'PathsDataProvider',
    function ($scope, PathsDataProvider) {
        angular.extend($scope, {
            turin: {
                        lat: 45.07,
                        lng: 7.69,
                        zoom: 13
            },
            paths: {}

        });

        
        this.calculate = function(){
            console.log(this.source + "->" + this.destination);

            PathsDataProvider.setSource(this.source);
            PathsDataProvider.setDestination(this.destination);

            $scope.paths = PathsDataProvider.getPath();

            // TODO use a service to print all the edges
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

                return percorsi.paths[i];                    
            }
        };
    }
]);