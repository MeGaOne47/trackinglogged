// Thiết lập kết nối web socket với máy chủ Chatbot GPT
var socket = new WebSocket("ws://localhost:8080/chatbot");

socket.onopen = function() {
    console.log("Kết nối đã được thiết lập.");
    socket.send("Xin chào, tôi là một người dùng.");
};

socket.onmessage = function(event) {
    console.log("Nhận được tin nhắn từ máy chủ: " + event.data);
};


// Lấy thẻ HTML để hiển thị các tin nhắn từ Chatbot GPT
var chatMessages = document.getElementById("chat-messages");

// Hàm này được gọi khi nhận được tin nhắn mới từ Chatbot GPT
function onMessageReceived(message) {
    // Thêm tin nhắn vào thẻ HTML
    chatMessages.innerHTML += "<p>" + message + "</p>";
}

// Gửi tin nhắn đến Chatbot GPT khi người dùng nhấn nút "Gửi"
function sendMessage() {
    var inputElement = document.getElementById("chat-input");
    var message = inputElement.value;

    // Gửi tin nhắn đến máy chủ Chatbot GPT
    socket.emit("message", message);

    // Thêm tin nhắn vào thẻ HTML
    chatMessages.innerHTML += "<p><strong>Bạn:</strong> " + message + "</p>";

    // Xóa nội dung trong ô nhập liệu
    inputElement.value = "";
}

// Xử lý các tin nhắn từ máy chủ Chatbot GPT
socket.on("message", onMessageReceived);
