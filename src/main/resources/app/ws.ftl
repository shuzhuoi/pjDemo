<#assign base="${springMacroRequestContext.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>宸瑞院前急救系统-院内管理后台</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="icon" href="favicon.ico">
	<link rel="stylesheet"  type="text/css" href="${base}/css/webuploader.css">
	 <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	 <script src="${base}/js/common/webuploader.min.js"></script>
	<script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
	 <style type="text/css">
		#dndArea{
			width:500px;
			height:400px;
			border-color:red;
			border-style: dashed;
			margin:auto auto;
			line-height: 400px;
			color: gray;
		}
	</style>
</head>

<body align="center">
	<h1>ws.ftl</h1>
	<input id="text" type="text" /><button id="connect1" onclick="connect1();">连接</button>
	<button onclick="send()">Send</button><br>

	<button onclick="closeWebSocket()">Close</button><br>
	<div id="message">
	</div>
	<br/>
	<hr/>
	<div>
    <div>
        <button id="connect" onclick="connect();">连接</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开连接</button>
    </div>
    <div id="conversationDiv">
        <label>输入你的名字</label><input type="text" id="name" />
        <button id="sendName" onclick="sendName();">发送</button>
        <p id="response"></p>
        <p id="response1"></p>
    </div>
    <a href="${base}/main.ftl">main.ftl</a><br>
    <a href="${base}/a.ftl">/a.ftl</a>
</div>



<script type="text/javascript">
    var websocket = null;


    	//判断当前浏览器是否支持WebSocket
        if('WebSocket' in window){
            websocket = new WebSocket("ws://192.168.2.231:8080/websocket");
        }
        else{
            alert('Not support websocket')
        }




		//连接发生错误的回调方法
	    websocket.onerror = function(){
	        setMessageInnerHTML("error");
	    };

	    //连接成功建立的回调方法
	    websocket.onopen = function(event){
	        setMessageInnerHTML("open");
	    }

	    //接收到消息的回调方法
	    websocket.onmessage = function(event){
	        setMessageInnerHTML(event.data);
	    }

	    //连接关闭的回调方法
	    websocket.onclose = function(){
	        setMessageInnerHTML("close");
	    }

	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	    window.onbeforeunload = function(){
	        websocket.close();
	    }




    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }

    //发送消息
    function send(){
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
</script>

	<!-- <script type="text/javascript">
    var stompClient = null;
    //此值有服务端传递给前端,实现方式没有要求
    var userId ="${userId}";

    function setConnected(connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
        $('#response').html();
    }

    function connect() {
        var socket = new SockJS('${base}/endpointWisely'); //1连接SockJS的endpoint是“endpointWisely”，与后台代码中注册的endpoint要一样。
        stompClient = Stomp.over(socket);//2创建STOMP协议的webSocket客户端。
        stompClient.connect({}, function(frame) {//3连接webSocket的服务端。
            setConnected(true);
            console.log('开始进行连接Connected: ' + frame);
            //4通过stompClient.subscribe（）订阅服务器的目标是'/topic/getResponse'发送过来的地址，与@SendTo中的地址对应。
            stompClient.subscribe('/topic/getResponse', function(respnose){
                showResponse(respnose.body);
            });

            //4通过stompClient.subscribe（）订阅服务器的目标是'/user/' + userId + '/msg'接收一对一的推送消息,其中userId由服务端传递过来,用于表示唯一的用户,通过此值将消息精确推送给一个用户
            stompClient.subscribe('/user/' + userId + '/msg', function(respnose){
            	console.log(respnose.body);
                showResponse1(respnose.body);
            });
        });
    }


    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");

    }

    function sendName() {
        var name = $('#name').val();
        //通过stompClient.send（）向地址为"/welcome"的服务器地址发起请求，与@MessageMapping里的地址对应。因为我们配置了registry.setApplicationDestinationPrefixes(Constant.WEBSOCKETPATHPERFIX);所以需要增加前缀/ws-push/
        stompClient.send("/ws-push/welcome", {}, JSON.stringify({ 'name': name }));
    }

    function showResponse(message) {
        var response = $("#response");
        response.append("<br>");
        response.append(message);
    }
    function showResponse1(message) {
        var response = $("#response1");
        response.append("<br>");
        response.append(message);
    }
</script> -->
</body>
</html>
