const url = 'http://localhost:8080';
let stompClient = null;

$(document).ready(function() {
	connect("minhvt27");
});

function connect(username) {
	let socket = new SockJS(url + '/notification');
	console.log("connect to server");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function() {
		console.log('Web Socket is connected');
		stompClient.subscribe('/queue/notification/' + username, function(message) {
		    // hiển thị thông báo
			$("#message").text(message.body);
		});
	});
}
//
//$(function() {
//	$("form").on('submit', function(e) {
//		e.preventDefault();
//	});
//	$("#send").click(function() {
//		stompClient.send("/app/hello", {}, $("#name").val());
//	});
//});