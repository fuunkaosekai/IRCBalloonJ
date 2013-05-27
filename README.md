IRCBalloonJ
===========
IRCBalloonJ is a modification of IRCBalloon 0.7.1, a project by Brian Hsu.
It intends to fix some bugs of IRCBalloon and provides new features

IRCBalloonJ began from 2013/05/17 (GMT-6)

began from 2013/05/21 (GMT-6), this project included a modified PircBotX 1.9 to connect to IRC servers

Thanks for Brian Hsu (Author of IRCBalloon) and Append's help

ChangeLog
===========
 - 0.2.2
    - Emotes, nickname/avatar now support UTF-8
        - This change makes old versions of IRCBalloon and IRCBalloonJ unable to read preferences
 - 0.2.1
    - Support Jtv chatroom nickname color (experimental)
 - 0.2
    - Remove "Use Justin.TV / Twitch nickname" option because ithis feature is no longer available on jtv/ttv
    - Add "Use Justin.TV / Twitch nickname color" option (experimental)
        - It is located at Preference -> Avatar / Nickname
        - Sometimes IRC servers (espacially for jtv channels) won't give any information about nickname color,
          in that case the "Nickname Color" setting on main window will be applied
        - Currently only ttv channel is supported
    - Fix a bug that the nickname in first line is not colored
    - Remove unexpected messages on log on main window
 - 0.1.1
    - Add a title for pinned chat window
        - When disabling "Always on top" option, the chatroom should be able to be captured by Window Capture in OBS
 - 0.1
    - Add port and channel option for Twitch/Justin.tv
    - Pinned chat: Fix a bug that every word ends with ":" will be colored as nickname color
    - Pinned chat: decrease spacing between each line
    - Display disconnect notification on pinned chat or balloon notification
    - Detect unexpected disconnection (and will show a disconnection message on pinned chat/balloon notification)
        - Currently auto reconnection is not supported
    - Add "Always on top" option for pinned chat.
    - Disable connect button when previewing
    - Change the text of Connect button to "Disconnect" when connecting to an IRC server

License
========

IRCBalloonJ is released under GNU GPL v3.

Download
==========
https://mega.co.nz/#F!GIkXGYwK!X2EQoT-y9K4HrfH4WlQdyA

Source Code
==========
You can get source code of IRCBalloonJ at
https://github.com/fuunkaosekai/IRCBalloonJ

About the Author
==========
Fuunkao Sekai
http://fuunkao-sekai.blogspot.com

Information of PircBotX
==========
PircBotX is a simple, easy to use, Java IRC Framework by Lord.Qua... and entityreborn
http://code.google.com/p/pircbotx/

Information of IRCBalloon
==========
Brian Hsu's (Author's) home page:
http://bone.twbbs.org.tw/

IRCBalloon on GitHub:
https://github.com/brianhsu/IRCBalloon
