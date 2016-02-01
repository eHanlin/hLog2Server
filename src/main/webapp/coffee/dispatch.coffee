hLogApp = angular.module 'hLogApp', []

hLogApp.controller 'hLogCtrl', ($scope) ->
  $scope.items = []
  $scope.news = {type:""}
  $scope.newUploading = false

  $scope.getList = () ->
    $.get 'Dispatch', (data) ->
      if(data.success)
        $scope.$apply ->
          $scope.items = data.value
          $scope.news = {type:""}
          $scope.newUploading = false

  $scope.getList()

  $scope.addAllDispatch = (queue) ->
    $scope.newUploading = true
    $.post 'type/all/Dispatch', {queue:queue}, (data) ->
      if(data.success == true)
        $scope.getList()

  $scope.addRegexDispatch = (queue, regex) ->
    $scope.newUploading = true
    $.post 'type/regex/Dispatch', {queue:queue, regex:regex}, (data) ->
      if(data.success == true)
        $scope.getList()

  $scope.addJsonFieldDispatch = (queue, key, value) ->
    $scope.newUploading = true
    $.post 'type/jsonField/Dispatch', {queue:queue, key:key, value:value}, (data) ->
      if(data.success == true)
        $scope.getList()

  $scope.removeDispatch = (id) ->
    $.ajax {
      type : 'DELETE',
      url : 'Dispatch?id='+id,
      cache : false,
      success : (data) ->
        if(data.success == true)
          $scope.getList()
    }