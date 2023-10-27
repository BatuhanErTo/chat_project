document.addEventListener("DOMContentLoaded", function () {
    if (!credits.token){
        window.location.replace("http://127.0.0.1:5500/index.html");
    }
    const loadChatHistory = async () => {
        try {  
            const chatIdParamSearch = new URLSearchParams(window.location.search);
            const response = await api.get(`chats/getChatHistory/${chatIdParamSearch.get('chatId')}`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${credits.token}`
                }
            });
            if (response.status === 200) {
                displayChatHistory(elements.chatWindow, response);
            }
        } catch (err) {
            console.log(err)
        }
    };


    document.querySelector('form').addEventListener("submit", async function (event) {
        event.preventDefault();
        const chatIdParamSearch = new URLSearchParams(window.location.search);
        let createMessageRequestData = {
            messageInfo: elements.messageInput.value.trim()
        }
        if (createMessageRequestData.messageInfo) {
            createMessageRequest(createMessageRequestData.messageInfo,
                credits.nickname, chatIdParamSearch);
        } else {
            alert("Message and nickname cannot be null!")
        }

        clearInputs(elements.messageInput);
    });

    elements.signOutButton.addEventListener("click", async function(){
            sessionStorage.clear('nickname');
            sessionStorage.clear('token');
            window.location.replace("http://127.0.0.1:5500/index.html");
    });

    elements.previousButton.addEventListener("click", async function(){
        window.location.replace("http://127.0.0.1:5500/home.html");
    });

    setInterval(loadChatHistory, 2000);
});

function displayChatHistory(chatHistory, response) {
    chatHistory.innerHTML = '';
    response.data.forEach(element => {
        const timestamp = new Date(element.timeToReceived);
        const dateFormat = createFormattedTime(timestamp);
        const p = document.createElement('p');
        if (element.nickname === credits.nickname){
            p.textContent = "You" + ": " + element.message + dateFormat;
        }else{
            p.textContent = element.nickname + ": " + element.message + dateFormat;
        }
        chatHistory.appendChild(p);
    });
}

async function createMessageRequest(messageInfo, nicknameInfo, chatIdParamSearch) {
    const newMessage = {
        message: messageInfo,
        nickname: nicknameInfo,
        chatId: chatIdParamSearch.get('chatId')
    }
    const sendPostRequest = async () => {
        try {
            await api.post('messages/createMessage', newMessage, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${credits.token}`
                }
            });
        } catch (err) {
            console.error(err);
        }
    };
    await sendPostRequest();
}


function createFormattedTime(timestamp) {
    let minutes = timestamp.getMinutes() < 9 ? "0" + timestamp.getMinutes() : timestamp.getMinutes();
    return " ----- [" + timestamp.getHours() + ":" + minutes + " ("
        + timestamp.getDate() + "/" + (timestamp.getMonth() + 1) + "/" + timestamp.getFullYear() + ")" + "]";
}

function clearInputs(input1) {
    input1.value = "";
}

const api = axios.create({
    baseURL: 'http://localhost:8080/v1/'
})

const credits = {
    token: sessionStorage.getItem('token'),
    nickname: sessionStorage.getItem('nickname')
}

const elements = {
    chatWindow: document.getElementById("chat-window"),
    messageInput: document.getElementById("message-info"),
    sendButton: document.getElementById("send-button"),
    signOutButton: document.getElementById("signOut-button"),
    previousButton: document.getElementById("previous-button")
}
