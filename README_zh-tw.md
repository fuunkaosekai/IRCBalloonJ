IRCBalloonJ
===========
IRCBalloonJ 是改自墳墓的作品IRCBalloon 0.7.1
嘗試修復一些bug和增加新功能

本專案從2013/05/17 (GMT-6)開始

於2013/05/21 (GMT-6)開始，本專案使用了修改過的PircBotX 1.9來連線至IRC伺服器

特別感謝墳墓(原作者)和Append的指導與幫忙

改版紀錄
===========
 - 0.2.2
    - 表情圖示，大頭貼與暱稱支援UTF-8
        - 這個更新會讓舊版的IRCBalloon和IRCBalloonJ無法讀取偏好設定
 - 0.2.1
    - 支援Jtv聊天室暱稱顏色 (測試中)
 - 0.2
    - 刪除"使用 Justin.TV / Twitch 上的暱稱" 因此選項已被Jtv/Ttv移除
    - 增加"使用 Justin.TV / Twitch 上的暱稱顏色" 選項 (測試中)
        - 選項位置: 偏好設定→大頭貼與暱稱偏好設定
        - 有時候IRC伺服器 (特別是Jtv頻道) 不會回傳暱稱顏色, 這時會套用在主視窗所選的"暱稱顏色"
        - 現在只支援Ttv頻道
    - 修正bug: 聊天室第一行的暱稱沒有上色
    - 刪除log裡不該出現的訊息
 - 0.1.1
    - 聊天室視窗加入視窗標題
        - 當取消 "視窗至頂" 選項時, 聊天室應該能被OBS的Window Capture抓到
 - 0.1
    - 增加伺服器port和頻道選項
    - 固定視窗: 解決任何結尾是":"的字被取代為暱稱字色
    - 固定視窗: 縮小行距
    - 在聊天室或氣球顯示斷線訊息
    - 偵測異常斷線(並在聊天室或氣球通知顯示斷線訊息)
        - 目前不支援自動重新連線
    - 固定視窗: 增加 "視窗至頂" 選項
    - 預覽時禁止連線
    - 當連線到IRC伺服器時把"連線"按紐改成"離線"

授權條款
========

IRCBalloonJ 採用 GNU GPL v3 做為授權條款

下載
==========
https://mega.co.nz/#F!GIkXGYwK!X2EQoT-y9K4HrfH4WlQdyA

原始碼
==========
IRCBalloonJ原始碼存放位置(GitHub):
https://github.com/fuunkaosekai/IRCBalloonJ

關於作者
==========
雖小臉世界
http://fuunkao-sekai.blogspot.com

PircBotX資訊
==========
PircBotX是一個簡單好用的Java IRC框架，開發者為Lord.Qua...和entityreborn
http://code.google.com/p/pircbotx/

IRCBalloon資訊
==========
作者墳墓的首頁:
http://bone.twbbs.org.tw/

IRCBalloon on GitHub:
https://github.com/brianhsu/IRCBalloon
