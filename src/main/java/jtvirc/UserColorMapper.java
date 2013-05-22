package jtvirc;

import java.util.HashMap;
import java.awt.Color;

public class UserColorMapper{
    private static UserColorMapper ins;
    private HashMap<String,Color> map = new HashMap<String,Color>();
    public static UserColorMapper ins(){
        if(ins== null){
            ins = new UserColorMapper();
        }
        return ins;
    }
    
    public Color getColor(String user){
        return map.get(user);
    }
    
    public void setColor(String user, String color){
        map.put(user,Color.decode(color));
    }
    
}