var depenencyApp = angular.module('dependencyApp', [
  'ngRoute',
  'dependencyControllers'
]);
 
depenencyApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/dependencies', {
        templateUrl: 'partials/dependencies.html',
        controller: 'DependencyCtrl'
      }).
      otherwise({
        redirectTo: '/dependencies'
      });
  }]);