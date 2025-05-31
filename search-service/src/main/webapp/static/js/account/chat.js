'use strict';

let stompClient = null;
const userSenderId = document.body.getAttribute('data-current-user-id');
const userDestinationId = document.body.getAttribute('data-receiver-user-id');

// connecting user to websocket
function connect() {
    // using Stomp for working with websocket endpoint
    stompClient = Stomp.over(new SockJS('/ws'));
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/messages', function (response) {
            const message = JSON.parse(response.body);
            drawMessage(message.accountAuthorId, message.text, message.id, message.photo);
        });
    });
}

window.onload = connect;

function sendMessage() {
    const messageInput = document.getElementById('text');
    const fileInput = document.getElementById('photo');
    const messageContent = messageInput.value.trim();
    if (stompClient) {
        const message = {
            accountAuthorId: userSenderId,
            destinationId: userDestinationId,
            text: messageContent
        };
        if (fileInput.files.length > 0) {
            const reader = new FileReader();
            reader.onload = function (event) {
                // remove 'data:image/...;base64,' part
                message.photo = event.target.result.split(',')[1];
                sendMessageToServer(message);
            };
            reader.readAsDataURL(fileInput.files[0]);
        } else {
            sendMessageToServer(message);
        }
    }}

function sendMessageToServer(message) {
    console.log("Sending message:", message);
    stompClient.send("/app/message", {}, JSON.stringify(message));
    drawMessage(message.accountAuthorId, message.text, null, message.photo);
    document.getElementById('text').value = '';
    document.getElementById('photo').value = '';
}

function drawMessage(senderId, content, messageId, photo) {
    const messagesDiv = document.querySelector('.messages');
    const messageContainer = document.createElement('div');
    const isSender = senderId === userSenderId;
    messageContainer.classList.add('d-flex', 'flex-row', 'mb-4',
        isSender ? 'justify-content-end' : 'justify-content-start');
    const messageContentContainer = document.createElement('div');
    messageContentContainer.classList.add('p-3', isSender ? 'ms-3' : 'me-3', 'border', 'bg-body-tertiary');
    messageContentContainer.style.borderRadius = '15px';
    if (isSender) {
        messageContentContainer.style.backgroundColor = 'rgba(57, 192, 237, .2)';
    }
    const messageText = document.createElement('p');
    messageText.classList.add('small', 'mb-0');
    messageText.textContent = content;
    messageContentContainer.appendChild(messageText);
    if (photo) {
        const messagePhoto = document.createElement('img');
        if (messageId) {
            messagePhoto.src = `/personal-message/image?id=${messageId}`;
        } else {
            // ensure using appropriate MIME type
            messagePhoto.src = 'data:image/jpeg;base64,' + photo;
        }
        messagePhoto.alt = "Message photo";
        messagePhoto.width = 150;
        messagePhoto.height = 150;
        messageContentContainer.appendChild(messagePhoto);
    }
    const avatarLink = createAvatarLink(senderId);
    if (isSender) {
        messageContainer.appendChild(avatarLink);
        messageContainer.appendChild(messageContentContainer);
    } else {
        messageContainer.appendChild(messageContentContainer);
        messageContainer.appendChild(avatarLink);
    }
    messagesDiv.appendChild(messageContainer);
}

function createAvatarLink(senderId) {
    const avatarLink = document.createElement('a');
    avatarLink.href = `/account?id=${senderId}`;
    const avatarImg = document.createElement('img');
    avatarImg.src = `/account/avatar?id=${senderId}`;
    avatarImg.alt = "Profile avatar";
    avatarImg.style.width = '45px';
    avatarImg.style.borderRadius = '50%';
    avatarLink.appendChild(avatarImg);
    return avatarLink;
}
