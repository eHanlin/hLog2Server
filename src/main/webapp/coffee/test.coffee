ws = new SockJS('/hLog2/sockJs/log')

ws.onopen = (e) ->
  console.log("open ")

ws.onclose = (e) ->
  console.log("close ")

ws.onmessage = (e) ->
  console.log("message ")

hLogApp = angular.module 'hLogApp', []

hLogApp.controller 'hLogCtrl', ($scope) ->
  $scope.httpPostMessage = ""
  $scope.sockJsMessage = ""

  $scope.sendUseHttpPost = (message) ->
    $.ajax {
      type : 'POST',
      url : 'Log',
      data : message,
      cache : false,
      contentType : 'application/json',
      dataType : 'json'
    }

  $scope.sendUseSockJs = (message) ->
    ws.send(message)
