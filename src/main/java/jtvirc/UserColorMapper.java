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
        return map.get(user.toLowerCase());
    }
    
    public boolean setColor(String user, String color){
        System.out.println(color);
        if(color.matches("^#[0-9a-fA-F]{6}$")){
            map.put(user.toLowerCase(),Color.decode(color));
            System.out.printf("Saved %s %s",user,map.get(user));
            return true;
        }else{
            //handling jtv color string
            return true;
        }
    }
    
}