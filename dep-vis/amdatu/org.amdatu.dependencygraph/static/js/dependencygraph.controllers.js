var controllers = angular.module('dependencyControllers', []);
var groups = [

'com.liferay.amazon',
'com.liferay.announcements',
'com.liferay.application',
'com.liferay.asset',
'com.liferay.blogs',
'com.liferay.bookmarks',
'com.liferay.calendar',
'com.liferay.comment',
'com.liferay.configuration',
'com.liferay.currency',
'com.liferay.dictionary',
'com.liferay.document',
'com.liferay.dynamic',
'com.liferay.expando',
'com.liferay.exportimport',
'com.liferay.flags',
'com.liferay.frontend',
'com.liferay.hello',
'com.liferay.iframe',
'com.liferay.invitation',
'com.liferay.item',
'com.liferay.journal',
'com.liferay.layout',
'com.liferay.loan',
'com.liferay.marketplace',
'com.liferay.mentions',
'com.liferay.message',
'com.liferay.microblogs',
'com.liferay.mobile',
'com.liferay.monitoring',
'com.liferay.my',
'com.liferay.nested',
'com.liferay.network',
'com.liferay.password',
'com.liferay.plugins',
'com.liferay.polls',
'com.liferay.portal',
'com.liferay.portlet',
'com.liferay.product',
'com.liferay.quick',
'com.liferay.ratings',
'com.liferay.roles',
'com.liferay.rss',
'com.liferay.search',
'com.liferay.service',
'com.liferay.shopping',
'com.liferay.site',
'com.liferay.social',
'com.liferay.staging',
'com.liferay.translator',
'com.liferay.trash',
'com.liferay.unit',
'com.liferay.user',
'com.liferay.users',
'com.liferay.web',
'com.liferay.wiki',
'com.liferay.workflow',
'com.liferay.xsl'

];


controllers.controller('DependencyCtrl', function DependencyCtrl($scope, $http) {
  $http.get("/api/jsonws/dependency-graph-rest.dependencies/service-graph").success(function (data) {
    $scope.data = data;
    $scope.draw();
  });

  $scope.draw = function () {
    $("svg").remove();

    var w = 3000, h = 3000;

    var vis = d3.select("graph").append("svg:svg").attr("width", w).attr("height", h);
    var color = d3.scale.category20();

    var nodes = [];
    var links = [];
    angular.forEach($scope.data, function (node) {
      if ((node.name.indexOf('org.eclipse') == -1) &&
          ($scope.filter == undefined || node.name.indexOf($scope.filter) != -1 || hasDependencyOnFilter(node))) {
        for (var i = 0; i < groups.length; i++) {
          if (node.name.indexOf(groups[i]) != -1) {
            node.group = i % 20;
            break;
          }
        }

        node.tooltip = node.notes;
        node.name = node.name.replace('com.liferay.', '');
        node.value = node.dependencies.length;
        node.dependencies = node.dependencies.map(function(depName) {
          return depName.replace('com.liferay.', '');
        });
        nodes.push(node);
      }
    });

    function hasDependencyOnFilter(node) {
      var result = false;

      angular.forEach(node.dependencies, function (dependency) {
        if (dependency.indexOf($scope.filter) != -1) {
          result = true;
          return false;
        }
      });

      return result;
    }



    var padding = 15, // separation between circles
        radius=40;
    function collide(alpha) {
      var quadtree = d3.geom.quadtree(nodes);
      return function(d) {
        var rb = 2*radius + padding,
            nx1 = d.x - rb,
            nx2 = d.x + rb,
            ny1 = d.y - rb,
            ny2 = d.y + rb;
        quadtree.visit(function(quad, x1, y1, x2, y2) {
          if (quad.point && (quad.point !== d)) {
            var x = d.x - quad.point.x,
                y = d.y - quad.point.y,
                l = Math.sqrt(x * x + y * y);
            if (l < rb) {
              l = (l - rb) / l * alpha;
              d.x -= x *= l;
              d.y -= y *= l;
              quad.point.x += x;
              quad.point.y += y;
            }
          }
          return x1 > nx2 || x2 < nx1 || y1 > ny2 || y2 < ny1;
        });
      };
    }

    angular.forEach(nodes, function (node) {
      angular.forEach(node.dependencies, function (dependency) {
        angular.forEach(nodes, function (otherNode) {
          if (otherNode.name == dependency) {
            links.push({
              source: node,
              target: otherNode,
              value: 1
            });
          }
        });
      });

    });

    var force = d3.layout.force()
        .size([w, h])
        .nodes(nodes)
        .links(links)
        .distance(800)
        .start();

    var svg = d3.select("body").append("svg")
        .attr("width", w)
        .attr("height", h);

    var link = svg.selectAll(".link")
        .data(links)
        .enter().append("line")
        .attr("class", "link")
        .style("stroke-width", function (d) {
          return Math.sqrt(d.value);
        });

    var node = svg.selectAll(".node")
        .data(nodes)
        .enter().append("g")
        .attr("class", "node")
        .call(force.drag)
        .on('dblclick', connectedNodes); //Added code

    node.append("circle")
        .attr("r", function(d) {
          return (Math.max(10, Math.min(80, (d.dependencies.length^2))));
        })
        .style("fill", function (d) {
          return color(d.group);
        });

    node.append("text")
        .attr("dx", 10)
        .attr("dy", ".35em")
        .text(function (d) {
          return d.name
        });

    node.append('svg:title').text(function(d) { return d.notes;});


    //var node = svg.selectAll(".node")
    //    .data(nodes)
    //    .enter().append("text")
    //    .attr("class", "node")
    //    .attr("r", 5)
    //    .style("fill", function(d) { return color(d.group); })
    //    .call(force.drag).text(function (d) {
    //        return d.name;
    //    });
    //;


    force.on("tick", function () {

      link.attr("x1", function (d) {
        return d.source.x;
      })
          .attr("y1", function (d) {
            return d.source.y;
          })
          .attr("x2", function (d) {
            return d.target.x;
          })
          .attr("y2", function (d) {
            return d.target.y;
          });

      node.attr("cx", function (d) {
        return d.x;
      })
          .attr("cy", function (d) {
            return d.y;
          });
      node.each(collide(0.5)); //Added
      //Changed

      d3.selectAll("circle").attr("cx", function (d) {
        return d.x;
      })
          .attr("cy", function (d) {
            return d.y;
          });

      d3.selectAll("text").attr("x", function (d) {
        return d.x;
      })
          .attr("y", function (d) {
            return d.y;
          });


    });

    //Toggle stores whether the highlighting is on
    var toggle = 0;
//Create an array logging what is connected to what
    var linkedByIndex = {};
    for (i = 0; i < nodes.length; i++) {
      linkedByIndex[i + "," + i] = 1;
    };
    links.forEach(function (d) {
      linkedByIndex[d.source.index + "," + d.target.index] = 1;
    });
//This function looks up whether a pair are neighbours
    function neighboring(a, b) {
      return linkedByIndex[a.index + "," + b.index];
    }
    function connectedNodes() {
      if (toggle == 0) {
        //Reduce the opacity of all but the neighbouring nodes
        d = d3.select(this).node().__data__;
        node.style("opacity", function (o) {
          return neighboring(d, o) | neighboring(o, d) ? 1 : 0.1;
        });
        link.style("opacity", function (o) {
          return d.index==o.source.index | d.index==o.target.index ? 1 : 0.1;
        });
        //Reduce the op
        toggle = 1;
      } else {
        //Put them back to opacity=1
        node.style("opacity", 1);
        link.style("opacity", 1);
        toggle = 0;
      }
    }

  };


});
