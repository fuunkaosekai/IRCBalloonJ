package org.bone.ircballoon

import org.eclipse.swt.widgets.{List => SWTList, _}
import org.eclipse.swt.layout._
import org.eclipse.swt.events._
import org.eclipse.swt.graphics._
import org.eclipse.swt.custom._

import org.eclipse.swt._
import scala.math._
import scala.collection.JavaConversions._
import I18N.i18n._

import jtvirc.UserColorMapper

trait MessageIcon
{
    def nicknameStyles(message: String, defColor: Color, font: Font): List[StyleRange] = 
    {
        val regex = """\n(\[OP\] )?(\[(.)*\] )?\w+:""".r

        regex.findAllIn(message).matchData.map { data => 
            val sptmsg = data.toString.split(" ")
            val username = sptmsg(sptmsg.length-1).replace("\n","").replace(":","").toLowerCase()    //parse username
            
            //println("username: "+username)
            
            val style = new StyleRange
            style.start = data.start
            style.length = data.end - data.start
            
            //will add checking Preference.useTtvColor
            val userColor = UserColorMapper.ins().getColor(username)
            
            if(userColor != null){
                style.foreground = new Color(Display.getDefault,userColor.getRed(),userColor.getGreen(),userColor.getBlue())
            }else{
                style.foreground = defColor
            }
            
            //style.foreground = defColor
            style.font = font
            style
        }.toList
    }

    def opStyles(message: String): List[StyleRange] = 
    {
        val regex = """\[OP\] """.r

        regex.findAllIn(message).matchData.map { data => 
            val style = new StyleRange

            style.start = data.start
            style.length = data.end - data.start
            style.data = MyIcon.ircOP
            style.metrics = new GlyphMetrics(
                MyIcon.ircOP.getBounds.height, 0, 
                MyIcon.ircOP.getBounds.width / 4
            )

            style
        }.toList
    }

    def emoteStyles(message: String): List[StyleRange] = {
        
        val styleList = IRCEmotes.getEmotes.map { case (text, image) =>
            val regex = """\Q%s\E""".format(text).r

            regex.findAllIn(message).matchData.map { data => 
                val style = new StyleRange
                style.start = data.start
                style.length = data.end - data.start
                style.data = image
                style.metrics = new GlyphMetrics(
                    image.getBounds.height, 0, 
                    image.getBounds.width / text.length
                )

                style
            }.toList
        }

        styleList.flatten.toList
    }

    def avatarStyles(message: String): List[StyleRange] = {
        
        val avatarHolders = """\[(\w)+\]""".r.findAllIn(message).matchData

        avatarHolders.map { data =>

            val user = IRCUser(data.matched.drop(1).dropRight(1))

            user.avatar.map { avatar =>
                val style = new StyleRange
                style.start = data.start
                style.length = data.end - data.start
                style.data = avatar
                style.metrics = new GlyphMetrics(
                    avatar.getBounds.height, 0, 
                    avatar.getBounds.width / data.matched.length
                )
                style
            }
        }.toList.flatten
    }

}

