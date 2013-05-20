package org.bone.ircballoon

import org.eclipse.swt.widgets.{List => SWTList, _}
import org.eclipse.swt.layout._
import org.eclipse.swt.events._
import org.eclipse.swt.graphics._
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.custom.StackLayout

import org.eclipse.swt._
import I18N.i18n._

class JustinSetting(parent: TabFolder, onModify: ModifyEvent => Any) extends 
       Composite(parent, SWT.NONE) with SWTHelper
{
    val tabItem = new TabItem(parent, SWT.NONE)
    val gridLayout = new GridLayout(2,  false)
    val username = createText(this, tr("Username:"))
    val password = createText(this, tr("Password:"), SWT.PASSWORD)
    val portText = createText(this, tr("Port:"))
    val channel = createText(this, tr("Channel:"))
    val (onJoinButton, onLeaveButton) = createJoinLeaveButton(this)

    def createIRCBot(callback: IRCMessage => Any, 
                     onLog: String => Any, 
                     onError: Exception => Any) =
    {
        val hostname = channel.getText match{
            case "" => "%s.jtvirc.com" format(username.getText)
            case _ =>  "%s.jtvirc.com" format(channel.getText)
        }
        
        val password = Some(this.password.getText.trim)
        val join_channel = channel.getText match{
            case "" => "#%s" format(username.getText)
            case _ =>  "#%s" format(channel.getText)
        }
        new IRCBot(
            hostname, portText.getText.toInt, username.getText, 
            password, join_channel, callback, onLog, onError, 
            onJoinButton.getSelection, 
            onLeaveButton.getSelection
        )
    }

    def isSettingOK = {
        val username = this.username.getText.trim
        val password = this.password.getText.trim

        username.length > 0 && password.length > 0
    }

    def setModifyListener()
    {
        username.addModifyListener(onModify)
        password.addModifyListener(onModify)
    }

    def setUIEnabled(isEnabled: Boolean)
    {
        this.username.setEnabled(isEnabled)
        this.password.setEnabled(isEnabled)
    }

    def setDefaultValue()
    {
        portText.setText("443")
    }
    
    
    this.setDefaultValue()
    this.setLayout(gridLayout)
    this.setModifyListener()
    this.tabItem.setText("Justin / Twitch")
    this.tabItem.setControl(this)
}

