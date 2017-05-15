var stompClient = null;
var whoami = null;
$('#chatMessages').empty();

var socket = new SockJS('/transportsChat'); //Connect user to web socket
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
	console.log('Connected: ' + frame);
	whoami = frame.headers["user-name"];
	/* subscribing to the users list  topic */
	stompClient.subscribe('/topic/presence/'+getUrlParameter('topic'), function (mess) {      	
		/*callback*/
	    $("#usersList").empty();
    	var users =  JSON.parse(mess.body).users;
	    users.forEach(updateUsers)
	});
		
	/* subscribing to the chat topic */
	stompClient.subscribe('/topic/chat/' + getUrlParameter('topic'), function (mess) {
		processMessage(JSON.parse(mess.body));
	} );
	
	/* to retrieve last messages */
	stompClient.subscribe('/user/queue/' + getUrlParameter('topic'), function (messArr) {
		var msgArray = JSON.parse(messArr.body);
		msgArray.forEach(processMessage);
	});
	        
	/* sending user join message */
	stompClient.send( "/app/join", {}, JSON.stringify({'name': $("#name").val(),'topicName': getUrlParameter('topic')}) );
});

function sendMessage(msg) {
	stompClient.send( "/app/chat", {}, JSON.stringify({'message': msg}) );
	 $("#textArea").val('');
	 $("#textArea").focus();
}

function processMessage(mess) {
	var msgDate = new Date(mess.date)
	if ( mess.username != whoami )
		newRecvMessage(mess,msgDate);
	else
		newSentMessage(mess,msgDate);
}


function updateUsers (user) {
	$("#usersList").append('<li class="left clearfix\\"> ' + 
			 '<span class="chat-img pull-left">' +
			 '<img src="http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-8/128/User-blue-icon.png" alt="User Avatar" class="img-circle">' +
			 '</span>' +
			 '<div class="chat-body clearfix">' +
				'<div class="header_sec">' +
				   '<strong class="primary-font">'+user+'</strong>'+
				'</div></div></li>');
}

function newRecvMessage (msg,date) {
	var time = isToday(date) ? date.toLocaleTimeString() : date.toLocaleString();
	$("#chatMessages").append('<li class="left clearfix"><span class="chat-img1 pull-left"><img src="http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-8/128/User-blue-icon.png" alt="User Avatar" class="img-circle"/></span>'+
								'<div class="chat-nickname">'+
								msg.nickname+
								'</div><div class="chat-body1 clearfix"><p>'+
								msg.message+
								'</p><div class="chat_time pull-right">'+time+'</div></div></li>');
	updateChat();
	
}

function newSentMessage (msg,date) {
	var time = isToday(date) ? date.toLocaleTimeString() : date.toLocaleString();
	$("#chatMessages").append('<li class="left clearfix admin_chat"><span class="chat-img1 pull-right"><img src="https://lh6.googleusercontent.com/-y-MY2satK-E/AAAAAAAAAAI/AAAAAAAAAJU/ER_hFddBheQ/photo.jpg" alt="User Avatar" class="img-circle"/></span><div class="chat-body2 clearfix"><p>'+
								msg.message+
								'</p><div class="chat_time pull-left">'+time+'</div></div></li>');
	updateChat();
}

$("#sendBtn").click(function(){
	sendMessage($("#textArea").val());
	
});

$("#textArea").keypress(function(event) {
    if (event.which == 13) {
      event.preventDefault();
      sendMessage($("#textArea").val());
    }
});

function updateChat() {
	var chatdiv = $('.chat_area');
	chatdiv.scrollTop(chatdiv.get(0).scrollHeight);
}

function isToday (date) {
	var now = new Date();
	if (date.toDateString() == now.toDateString())
		return true;
	else 
		return false;
}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};