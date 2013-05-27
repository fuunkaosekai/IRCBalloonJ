package org.bone.ircballoon

import org.eclipse.swt.widgets.{List => SWTList, _}
import org.eclipse.swt.layout._
import org.eclipse.swt.events._
import org.eclipse.swt.graphics._
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.custom.StackLayout

import org.eclipse.swt._
import java.io.File
import java.io.PrintWriter
import scala.io.Source
import ImageUtil._

object Preference extends SWTHelper
{
    import java.util.prefs.Preferences

    var displayAvatar: Boolean = true
    var onlyAvatar: Boolean = false
    var usingTwitchAvatar: Boolean = false
    //var usingTwitchNickname: Boolean = true
    var usingTwitchColor: Boolean = true
    var usingDefaultEmotes: Boolean = true

    val preference = Preferences.userNodeForPackage(Preference.getClass)
    val settingsDir = new File(System.getProperty("user.home") + "/.ircballoon/")

    def readEmotes()
    {
        this.usingDefaultEmotes = preference.getBoolean("useDefaultEmote", true)

        val emoteFile = new File(settingsDir.getPath + "/emotes.txt")

        if (!emoteFile.exists) {
            return
        }

        for (emote <- Source.fromFile(emoteFile,"UTF-8").getLines()) {

            val Array(text, imageFile) = emote.split("\\|")

            loadFromFile(imageFile).foreach { image =>
                IRCEmotes.addEmote(text, imageFile)
            }
        }
    }
    
    def useTtvColor(): Boolean={
        usingTwitchColor
    }

    def saveEmotes()
    {
        if (!settingsDir.exists()) {
            settingsDir.mkdirs()
        }

        val emoteFile = new File(settingsDir.getPath + "/emotes.txt")
        val printer = new PrintWriter(emoteFile,"UTF-8")

        IRCEmotes.getCustomEmotes.foreach { case(text, emote) =>
            printer.println("%s|%s" format(text, emote.file))
        }

        printer.flush()
        printer.close()

        preference.putBoolean("useDefaultEmote", this.usingDefaultEmotes)
    }

    def readAvatars()
    {
        val avatarFile = new File(settingsDir.getPath + "/avatars.txt")

        this.displayAvatar = preference.getBoolean("displayAvatar", true)
        this.onlyAvatar = preference.getBoolean("onlyAvatar", false)
        this.usingTwitchAvatar = preference.getBoolean("usingTwitchAvatar", false)
        //this.usingTwitchNickname = preference.getBoolean("usingTwitchNickname", false)
        this.usingTwitchColor = preference.getBoolean("usingTwitchColor", false)

        if (!avatarFile.exists) {
            return
        }
       
        Source.fromFile(avatarFile,"UTF-8").getLines().foreach { avatar =>
            val Array(nickname, imageFile) = avatar.split("\\|")
            IRCUser.addAvatar(nickname, imageFile)
        }
    }

    def saveAvatars()
    {
        if (!settingsDir.exists()) {
            settingsDir.mkdirs()
        }

        val avatarFile = new File(settingsDir.getPath + "/avatars.txt")
        val printer = new PrintWriter(avatarFile,"UTF-8")

        IRCUser.getAvatars.foreach { case(nickname, avatar) =>
            printer.println("%s|%s" format(nickname, avatar.file))
        }

        printer.flush()
        printer.close()

        preference.putBoolean("displayAvatar", this.displayAvatar)
        preference.putBoolean("onlyAvatar", this.onlyAvatar)
        preference.putBoolean("usingTwitchAvatar", this.usingTwitchAvatar)
        //preference.putBoolean("usingTwitchNickname", this.usingTwitchNickname)
        preference.putBoolean("usingTwitchColor", this.usingTwitchColor)
    }


    def read(setting: BlockSetting)
    {
        setting.locationX.setText(preference.getInt("BlockX", 100).toString)
        setting.locationY.setText(preference.getInt("BlockY", 100).toString)
        setting.width.setText(preference.getInt("BlockWidth", 200).toString)
        setting.height.setText(preference.getInt("BlockHeight", 400).toString)

        val bgColor = new Color(
            Display.getDefault, 
            preference.getInt("BlockBGRed", 0),
            preference.getInt("BlockBGGreen", 0),
            preference.getInt("BlockBGBlue", 0)
        )

        val fontColor = new Color(
            Display.getDefault,
            preference.getInt("BlockFontRed", 255),
            preference.getInt("BlockFontGreen", 255),
            preference.getInt("BlockFontBlue", 255)
        )

        val borderColor = new Color(
            Display.getDefault,
            preference.getInt("BlockBorderRed", 255),
            preference.getInt("BlockBorderGreen", 255),
            preference.getInt("BlockBorderBlue", 255)
        )

        val nicknameColor = new Color(
            Display.getDefault,
            preference.getInt("BlockNicknameRed", 255),
            preference.getInt("BlockNicknameGreen", 255),
            preference.getInt("BlockNicknameBlue", 255)
        )

        setting.bgColor = bgColor
        setting.fontColor = fontColor
        setting.borderColor = borderColor
        setting.nicknameColor = nicknameColor
        setting.bgButton.setText(bgColor)
        setting.fgButton.setText(fontColor)
        setting.borderButton.setText(borderColor)
        setting.nicknameColorButton.setText(nicknameColor)

        val font = new Font(
            Display.getDefault, 
            preference.get("BlockFontName", MyFont.DefaultFontName),
            preference.getInt("BlockFontHeight", MyFont.DefaultFontSize),
            preference.getInt("BlockFontStyle", MyFont.DefaultFontStyle)
        )

        val nicknameFont = new Font(
            Display.getDefault, 
            preference.get("BlockNicknameFontName", MyFont.DefaultFontName),
            preference.getInt("BlockNicknameFontHeight", MyFont.DefaultFontSize),
            preference.getInt("BlockNicknameFontStyle", MyFont.DefaultFontStyle)
        )

        setting.messageFont = font
        setting.nicknameFont = nicknameFont

        setting.fontButton.setText(font)
        setting.nicknameFontButton.setText(nicknameFont)

        val transparent = preference.getInt("BlockTransparent", 20)
        setting.transparentScale.setSelection(transparent)
        setting.transparentLabel.setText(
            setting.alphaTitle + transparent + "%"
        )

        setting.messageSizeSpinner.setSelection(
            preference.getInt("BlockMessageSize", 10)
        )

        // 背景圖片
        val backgroundImage = preference.get("BlockBackgroundImage", "")

        if (backgroundImage != "") {
            setting.setBlockBackgroundImage(backgroundImage)
        }

        // Scroll Bar
        val hasScrollBar = preference.getBoolean("BlockScrollBar", false)
        setting.scrollBarCheckbox.setSelection(hasScrollBar)
        
        //on top
        val onTop = preference.getBoolean("BlockOnTop", false)
        setting.onTopCheckbox.setSelection(onTop)
    }

    def save(setting: BlockSetting)
    {
        // 視窗位置、大小
        preference.putInt("BlockX", setting.locationX.getText.toInt)
        preference.putInt("BlockY", setting.locationY.getText.toInt)
        preference.putInt("BlockWidth", setting.width.getText.toInt)
        preference.putInt("BlockHeight", setting.height.getText.toInt)

        // 配色
        preference.putInt("BlockBGRed", setting.bgColor.getRed)
        preference.putInt("BlockBGGreen", setting.bgColor.getGreen)
        preference.putInt("BlockBGBlue", setting.bgColor.getBlue)
        preference.putInt("BlockFontRed", setting.fontColor.getRed)
        preference.putInt("BlockFontGreen", setting.fontColor.getGreen)
        preference.putInt("BlockFontBlue", setting.fontColor.getBlue)
        preference.putInt("BlockBorderRed", setting.borderColor.getRed)
        preference.putInt("BlockBorderGreen", setting.borderColor.getGreen)
        preference.putInt("BlockBorderBlue", setting.borderColor.getBlue)

        preference.putInt("BlockNicknameRed", setting.nicknameColor.getRed)
        preference.putInt("BlockNicknameGreen", setting.nicknameColor.getGreen)
        preference.putInt("BlockNicknameBlue", setting.nicknameColor.getBlue)

        preference.putInt(
            "BlockTransparent", 
            setting.transparentScale.getSelection
        )

        // 背景圖片
        setting.blockBackgroundImage match {
            case Some(imageFile) => preference.put("BlockBackgroundImage", imageFile)
            case None => preference.remove("BlockBackgroundImage")
        }

        // 訊息字型
        val fontData = setting.messageFont.getFontData()(0)
        preference.put("BlockFontName", fontData.getName)
        preference.putInt("BlockFontHeight", fontData.getHeight)
        preference.putInt("BlockFontStyle", fontData.getStyle)

        // 暱稱字型
        val nicknameFontData = setting.nicknameFont.getFontData()(0)
        preference.put("BlockNicknameFontName", nicknameFontData.getName)
        preference.putInt("BlockNicknameFontHeight", nicknameFontData.getHeight)
        preference.putInt("BlockNicknameFontStyle", nicknameFontData.getStyle)

        // 訊息數量
        preference.putInt(
            "BlockMessageSize", 
            setting.messageSizeSpinner.getSelection
        )

        // Scroll Bar
        preference.putBoolean(
            "BlockScrollBar",
            setting.scrollBarCheckbox.getSelection
        )
        
        //on top
        preference.putBoolean(
            "BlockOnTop",
            setting.onTopCheckbox.getSelection
        )
    }

    def read(setting: BalloonSetting)
    {
        setting.locationX.setText(preference.getInt("BalloonX", 100).toString)
        setting.locationY.setText(preference.getInt("BalloonY", 100).toString)
        setting.width.setText(preference.getInt("BalloonWidth", 200).toString)
        setting.height.setText(preference.getInt("BalloonHeight", 400).toString)

        val bgColor = new Color(
            Display.getDefault, 
            preference.getInt("BalloonBGRed", 0),
            preference.getInt("BalloonBGGreen", 0),
            preference.getInt("BalloonBGBlue", 0)
        )

        val fontColor = new Color(
            Display.getDefault,
            preference.getInt("BalloonFontRed", 255),
            preference.getInt("BalloonFontGreen", 255),
            preference.getInt("BalloonFontBlue", 255)
        )

        val borderColor = new Color(
            Display.getDefault,
            preference.getInt("BalloonBorderRed", 255),
            preference.getInt("BalloonBorderGreen", 255),
            preference.getInt("BalloonBorderBlue", 255)
        )

        val nicknameColor = new Color(
            Display.getDefault,
            preference.getInt("BalloonNicknameRed", 255),
            preference.getInt("BalloonNicknameGreen", 255),
            preference.getInt("BalloonNicknameBlue", 255)
        )

        setting.bgColor = bgColor
        setting.fontColor = fontColor
        setting.borderColor = borderColor
        setting.nicknameColor = nicknameColor

        setting.bgButton.setText(bgColor)
        setting.fgButton.setText(fontColor)
        setting.borderButton.setText(borderColor)
        setting.nicknameColorButton.setText(nicknameColor)

        val font = new Font(
            Display.getDefault, 
            preference.get("BalloonFontName", MyFont.DefaultFontName),
            preference.getInt("BalloonFontHeight", MyFont.DefaultFontSize),
            preference.getInt("BalloonFontStyle", MyFont.DefaultFontStyle)
        )

        val nicknameFont = new Font(
            Display.getDefault, 
            preference.get("BalloonNicknameFontName", MyFont.DefaultFontName),
            preference.getInt("BalloonNicknameFontHeight", MyFont.DefaultFontSize),
            preference.getInt("BalloonNicknameFontStyle", MyFont.DefaultFontStyle)
        )

        setting.messageFont = font
        setting.nicknameFont = nicknameFont

        setting.fontButton.setText(font)
        setting.nicknameFontButton.setText(nicknameFont)

        val transparent = preference.getInt("BalloonTransparent", 20)
        setting.transparentScale.setSelection(transparent)
        setting.transparentLabel.setText(
            setting.alphaTitle + transparent + "%"
        )

        // 特效設定
        setting.displayTimeSpinner.setSelection(preference.getInt("BalloonDisplayTime", 5))
        setting.fadeTimeSpinner.setSelection(preference.getInt("BalloonFadeTime", 500))
        setting.spacingSpinner.setSelection(preference.getInt("BalloonSpacing", 5))

    }


    def save(setting: BalloonSetting)
    {
        // 視窗位置、大小
        preference.putInt("BalloonX", setting.locationX.getText.toInt)
        preference.putInt("BalloonY", setting.locationY.getText.toInt)
        preference.putInt("BalloonWidth", setting.width.getText.toInt)
        preference.putInt("BalloonHeight", setting.height.getText.toInt)

        // 配色
        preference.putInt("BalloonBGRed", setting.bgColor.getRed)
        preference.putInt("BalloonBGGreen", setting.bgColor.getGreen)
        preference.putInt("BalloonBGBlue", setting.bgColor.getBlue)
        preference.putInt("BalloonFontRed", setting.fontColor.getRed)
        preference.putInt("BalloonFontGreen", setting.fontColor.getGreen)
        preference.putInt("BalloonFontBlue", setting.fontColor.getBlue)
        preference.putInt("BalloonBorderRed", setting.borderColor.getRed)
        preference.putInt("BalloonBorderGreen", setting.borderColor.getGreen)
        preference.putInt("BalloonBorderBlue", setting.borderColor.getBlue)
        preference.putInt("BalloonNicknameRed", setting.nicknameColor.getRed)
        preference.putInt("BalloonNicknameGreen", setting.nicknameColor.getGreen)
        preference.putInt("BalloonNicknameBlue", setting.nicknameColor.getBlue)
        preference.putInt(
            "BalloonTransparent", 
            setting.transparentScale.getSelection
        )

        // 字型
        val fontData = setting.messageFont.getFontData()(0)
        val nicknameFontData = setting.nicknameFont.getFontData()(0)
        preference.put("BalloonFontName", fontData.getName)
        preference.putInt("BalloonFontHeight", fontData.getHeight)
        preference.putInt("BalloonFontStyle", fontData.getStyle)

        preference.put("BalloonNicknameFontName", nicknameFontData.getName)
        preference.putInt("BalloonNicknameFontHeight", nicknameFontData.getHeight)
        preference.putInt("BalloonNicknameFontStyle", nicknameFontData.getStyle)

        // 特效設定
        preference.putInt("BalloonDisplayTime", setting.displayTimeSpinner.getSelection)
        preference.putInt("BalloonFadeTime", setting.fadeTimeSpinner.getSelection)
        preference.putInt("BalloonSpacing", setting.spacingSpinner.getSelection)
    }

    def read(ircSetting: IRCSetting)
    {
        val portText = preference.get("IRCPort", "6667").trim match {
            case ""   => "6667"
            case port => port
        }

        ircSetting.hostText.setText(preference.get("IRCHostname", ""))
        ircSetting.portText.setText(portText)
        ircSetting.nickname.setText(preference.get("IRCNickname", ""))
        ircSetting.channel.setText(preference.get("IRCChannel", ""))
        ircSetting.onJoinButton.setSelection(preference.getBoolean("IRCOnJoin", false))
        ircSetting.onLeaveButton.setSelection(preference.getBoolean("IRCOnLeave", false))
    }

    def save(ircSetting: IRCSetting)
    {
        preference.put("IRCHostname", ircSetting.hostText.getText)
        preference.put("IRCPort", ircSetting.portText.getText)
        preference.put("IRCNickname", ircSetting.nickname.getText)
        preference.put("IRCChannel", ircSetting.channel.getText)
        preference.putBoolean("IRCOnJoin", ircSetting.onJoinButton.getSelection)
        preference.putBoolean("IRCOnLeave", ircSetting.onLeaveButton.getSelection)
    }

    def read(justinSetting: JustinSetting)
    {
        val portText = preference.get("JTVPort", "443").trim match {
            case ""   => "443"
            case port => port
        }
        justinSetting.username.setText(preference.get("JTVUsername", ""))
        justinSetting.channel.setText(preference.get("JTVChannel", ""))
        justinSetting.portText.setText(portText)
        justinSetting.onJoinButton.setSelection(preference.getBoolean("JTVOnJoin", false))
        justinSetting.onLeaveButton.setSelection(preference.getBoolean("JTVOnLeave", false))
    }

    def save(justinSetting: JustinSetting)
    {
        preference.put("JTVUsername", justinSetting.username.getText)
        preference.put("JTVPort", justinSetting.portText.getText)
        preference.put("JTVChannel", justinSetting.channel.getText)
        preference.putBoolean("JTVOnJoin", justinSetting.onJoinButton.getSelection)
        preference.putBoolean("JTVOnLeave", justinSetting.onLeaveButton.getSelection)
    }

}

