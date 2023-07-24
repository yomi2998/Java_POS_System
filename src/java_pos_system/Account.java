package java_pos_system;

import java.util.HashMap;

public class Account {
    HashMap<String,String> logininfo = new HashMap<String,String>();
    
    Account(){
        logininfo.put("Bro", "pizza");
        logininfo.put("Bromethus", "PASSWORD");
        logininfo.put("BroCode", "abc123");
    }
    
    protected HashMap getLoginInfo(){
        return logininfo;
    }
}
