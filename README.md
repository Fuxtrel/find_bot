# FindBot
This bot can find any requests in google\
### Commands:
#### /start - start bot session
#### /help - return list of commands
#### /find - enable find mode (just type message in chat, send it, and bot get first answer from Google)
#### /stop - disable find mode

# How to set up bot environment

### 1) Download telegram-bot-api to  local repository
   ```
   git clone --recursive https://github.com/tdlib/telegram-bot-api.git
   ```
   https://github.com/tdlib/telegram-bot-api.git
### 2) Build it
   ```
   cd telegram-bot-api
   mkdir build
   cd build
   cmake -DCMAKE_BUILD_TYPE=Release ..
   cmake --build . --target install
   ```
### 3) Start telegram-bot-api server with parameters:
   ```
   telegram-bot-api --local --api-id=13263913 --api-hash=801d9887c886098c20334b50a5da83d8 &
   ```
> ### To kill than telegram-bot-api use 
> ```
> killall telegram-bot-api
> ```
### 4) Build and run find_bot with commands:
   ```
   gradle init && gradle build && gradle run
   ```
### 5) Add bot to your telegram application

http://t.me/find_www_bot