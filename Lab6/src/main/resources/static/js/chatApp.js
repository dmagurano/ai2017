var stompClient = null;
var whoami = null;
var alert = {};
var types = ['cantiere', 'incidente', 'incendio', 'altro', 'Davide, we are watching you']

/* map */
//initializing map
mymap = L.map('chat-map').setView([45.07, 7.69], 13);
L.tileLayer('https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZGF2cjA5MTAiLCJhIjoiY2owemk4N2FmMDJ1ZzMzbno3YjZxZDN3YyJ9.eJdGDM0goIVXcFmMrQX8og')
	.addTo(mymap);
/* map */

$('#chatMessages').empty();

$('#modal-body').append(
		function(){
			firstType = types[0];
			
			html = '<div class="radio"><label><input type="radio" name="type" checked="checked" value="' + firstType + '">' + firstType + '</label></div>';
			
			typesWithoutFirst = types.slice(1, types.length);
			typesWithoutFirst.forEach(
					function(type){
						html += '<div class="radio"><label><input type="radio" name="type" value="' + type + '">' + type + '</label></div>';
					});
			return html;
		}
);

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
	
	/* to retrieve alerts */
	stompClient.subscribe('/user/queue/alerts', function (alertsArr) {
		var alertsArray = JSON.parse(alertsArr.body);
		alertsArray.forEach(processAlert);
	});
		
	/* subscribing to the alerts */
	stompClient.subscribe('/topic/chat/alerts', function (alert) {
		processAlert(JSON.parse(alert.body));
	} );
	        
	/* sending user join message */
	stompClient.send( "/app/join", {}, JSON.stringify({'name': $("#name").val(),'topicName': getUrlParameter('topic')}) );
});

function sendMessage(msg) {
	// extract text msg, lat, lng, type from msg
	min_lat = 45.00;
	max_lat = 45.11;
	min_lng = 7.59;
	max_lng = 7.75;
	
	lat = (Math.random() * (max_lat - min_lat) + min_lat).toFixed(4);
	lng = (Math.random() * (max_lng - min_lng) + min_lng).toFixed(4);
	
	console.log("sending ", alert);
	
	stompClient.send( 
			"/app/chat", 
			{},
			JSON.stringify({
				'message': msg, 
				'lat': alert.lat,
				'lng': alert.lng,
				'address': alert.address,
				'type':alert.type
			})
	);
	
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

function processAlert(alert) {
	// TODO uptade map with alert
	console.log("new alert received");
	console.log(alert);
	
	var iconUrl;
	
	switch(alert.type){
	case 'cantiere':
		iconUrl = 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-yellow.png';
		break;
	case 'incidente':
		iconUrl = 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-orange.png';
		break;
	case 'incendio':
		iconUrl = 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png';
		break;
	case 'altro':
		iconUrl = 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-grey.png';
		break;
	default:
		iconUrl = 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-grey.png';
		break;
	}
	
	var coloredIcon = new L.Icon({
		  iconUrl: iconUrl,
		  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
		  iconSize: [25, 41],
		  iconAnchor: [12, 41],
		  popupAnchor: [1, -34],
		  shadowSize: [41, 41]
		});
	
	var marker = L.marker([alert.lat, alert.lng], {icon: coloredIcon}).addTo(mymap);
	marker.bindPopup(
			/*
			"<b>" + alert.type.toUpperCase() + "</b><br>"
			+ alert.address + "<br>"
			+ "attivo dal " + printDateTime(alert.timestamp) + "<br>"
			+ "segnalato da " + alert.nickname + "<br>"
			+ 
			+ "	<div class=\"acidjs-rating-stars acidjs-rating-disabled\">"
			+ "		valutazione"
			+ "			<input type=\"hidden\" name=\"alert-id\">"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-0\" value=\"5\" />"
			+ "			<label for=\"group-2-0\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-1\" value=\"4\" />"
			+ "			<label for=\"group-2-1\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-2\" value=\"3\" checked=\"checked\"/>"
			+ "			<label for=\"group-2-2\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-3\" value=\"2\" />"
			+ "			<label for=\"group-2-3\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-4\"  value=\"1\" />"
			+ "			<label for=\"group-2-4\"></label>"
			+ "</div>"
			+ "<a>vota</a>"
			+ 
			*/
			loadPopup(alert)
	);

	
}

function loadPopup(alert){
	return "<b>" + alert.type.toUpperCase() + "</b><br>"
			+ alert.address + "<br>"
			+ "attivo dal " + printDateTime(alert.timestamp) + "<br>"
			+ "segnalato da " + alert.nickname + "<br>"
			+ "valutazione"
			+ "	<div id=\"rating-out-" + alert.id + "\" class=\"acidjs-rating-stars acidjs-rating-disabled\">"
			+ "			<input type=\"hidden\" name=\"alert-id\">"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-0\" value=\"5\" />"
			+ "			<label for=\"group-2-0\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-1\" value=\"4\" />"
			+ "			<label for=\"group-2-1\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-2\" value=\"3\" checked=\"checked\"/>"
			+ "			<label for=\"group-2-2\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-3\" value=\"2\" />"
			+ "			<label for=\"group-2-3\"></label>"
			+ "			<input disabled=\"disabled\" type=\"radio\" name=\"group-2\" id=\"group-2-4\"  value=\"1\" />"
			+ "			<label for=\"group-2-4\"></label>"
			+ "	</div><br>"
			+ " <button onclick=\"enableRating(\'" + alert.id + "\')\" id=\"rating-button-" + alert.id + "\" >vota</button>"
			+ "	<div id=\"rating-in-" + alert.id + "\" class=\"acidjs-rating-stars hidden-rating\">"
			+ "			<input type=\"hidden\" name=\"alert-id\">"
			+ "			<input type=\"radio\" name=\"group-2\" id=\"group-2-0\" value=\"5\" />"
			+ "			<label for=\"group-2-0\"></label>"
			+ "			<input type=\"radio\" name=\"group-2\" id=\"group-2-1\" value=\"4\" />"
			+ "			<label for=\"group-2-1\"></label>"
			+ "			<input type=\"radio\" name=\"group-2\" id=\"group-2-2\" value=\"3\" checked=\"checked\"/>"
			+ "			<label for=\"group-2-2\"></label>"
			+ "			<input type=\"radio\" name=\"group-2\" id=\"group-2-3\" value=\"2\" />"
			+ "			<label for=\"group-2-3\"></label>"
			+ "			<input type=\"radio\" name=\"group-2\" id=\"group-2-4\"  value=\"1\" />"
			+ "			<label for=\"group-2-4\"></label>"
			+ "			<button onclick=\"submitRating(\'" + alert.id + "\')\">invia</button>"
			+ "	</div><br>";
}

function enableRating(alertId){
	$("#rating-in-"+ alertId).show();
	$("#rating-button-"+ alertId).hide();
}

function submitRating(alertId){
	// TODO
	// send rate and update all rates
}

function printDateTime(timestamp){
	var date = new Date(timestamp);
	
	var day = date.getDate();
	
	var month = date.getMonth() + 1;
	
	var year = date.getFullYear();
	
	var hours = date.getHours();
	
	var minutes = "0" + date.getMinutes();
	
	var seconds = "0" + date.getSeconds();

	var formattedTime = day + "." + month + "." + year + " " + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
	
	return formattedTime;
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
	$("#chatMessages").append('<li class="left clearfix admin_chat"><span class="chat-img1 pull-right"><img src="https://scontent-mxp1-1.xx.fbcdn.net/v/t1.0-9/995179_496393017119212_1942402182_n.jpg?oh=931db49c4f4f7c905efde31e3371f592&oe=59E4426F" alt="User Avatar" class="img-circle"/></span><div class="chat-body2 clearfix"><p>'+
								msg.message+
								'</p><div class="chat_time pull-left">'+time+'</div></div></li>');
	updateChat();
}

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

$("#sendBtn").click(function(){
	sendMessage($("#textArea").val());
	
	searchCompleted = false;
	
	alert = {};
});

$("#textArea").keypress(function(event) {
    if (event.which == 13) {
      event.preventDefault();
      sendMessage($("#textArea").val());
    }
});

var searchCompleted = false;

$("#textArea").bind(
		'input propertychange', 
		function(){
			if(searchCompleted != false)
				return;
				
			tmp_string = this.value;
			
			var matches = tmp_string.match(/\[(.*?)\]/);

			if (matches) {
			    var location = matches[1];
			    
			    if (location){
				    //console.log("location:" , location);

				    $.getJSON('http://nominatim.openstreetmap.org/search?q=' + location + ',+torino&format=json',
				    	function(data) {
				    		console.log(data);
				    		searchCompleted = true;
				    		// search 'Turin' in data
				    		var turinResult;
				    		for(var i in data){
				    			if ((data[i].display_name.indexOf('Torino') !== -1) || (data[i].display_name.indexOf('Turin') !== -1)){
				    				turinResult = data[i];
				    				break;
				    			}
				    		}
				        	//parse data from JSON
				    		alert.lat = turinResult.lat;
				    		alert.lng = turinResult.lon;
				    		alert.address = turinResult.display_name;
				    		
				    		// TODO add popup for type selection
				    		$('#alertsModal').modal({
				    			  backdrop: 'static',
				    			  keyboard: false
				    			});
				    	}
				    );
			    }
			}
		}
);

$("#modal-select").click(
		function(){
			alert.type = document.querySelector('input[name="type"]:checked').value;

    		console.log(alert);
		}
);