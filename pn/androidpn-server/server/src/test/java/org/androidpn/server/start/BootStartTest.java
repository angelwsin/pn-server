package org.androidpn.server.start;

import org.androidpn.server.xmpp.XmppServer;

public class BootStartTest {
    
    public static void main(String[] args) {
        //System.setProperty("base.dir", "D:\\pn\\");
      // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-config.xml");
    	
        XmppServer.getInstance();
    }

}
