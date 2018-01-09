package org.androidpn.client.task;

import org.androidpn.client.NotificationIQ;
import org.androidpn.client.XmmpChatManagerListener;
import org.androidpn.client.XmppManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;

public class LoginTask implements Runnable {
	
	protected final Log log = LogFactory.getLog(getClass());

    private final XmppManager xmppManager;
    
    private final String      username;
	
    private final String      password;
	
	private static final String XMPP_RESOURCE_NAME = "AndroidpnClient";

    public LoginTask(XmppManager xmppManager,String username,String password) {
        this.xmppManager = xmppManager;
        this.username  = username;
        this.password  = password;
    }

    public void run() {
        log.debug( "LoginTask.run()...");

        if (!xmppManager.isAuthenticated()) {
            log.debug( "username=" + username);
            log.debug("password=" + password);

            try {
                xmppManager.getConnection().login(
                		username,
                		password, XMPP_RESOURCE_NAME);
                log.debug( "Loggedn in successfully");

                // connection listener
                if (xmppManager.getConnectionListener() != null) {
                    xmppManager.getConnection().addConnectionListener(
                            xmppManager.getConnectionListener());
                }

                // packet filter
                PacketFilter packetFilter = new PacketTypeFilter(
                        NotificationIQ.class);
                // packet listener
                PacketListener packetListener = xmppManager
                        .getNotificationPacketListener();
                xmppManager.getConnection().addPacketListener(packetListener, packetFilter);
                xmppManager.getConnection().getChatManager().addChatListener(new XmmpChatManagerListener());

            } catch (XMPPException e) {
                log.debug( "LoginTask.run()... xmpp error");
                log.debug("Failed to login to xmpp server. Caused by: "
                        + e.getMessage());
                String INVALID_CREDENTIALS_ERROR_CODE = "401";
                String errorMessage = e.getMessage();
                if (errorMessage != null
                        && errorMessage
                                .contains(INVALID_CREDENTIALS_ERROR_CODE)) {
                    //xmppManager.reregisterAccount();
                    return;
                }
                xmppManager.startReconnectionThread();

            } catch (Exception e) {
                log.debug( "LoginTask.run()... other error");
                log.debug( "Failed to login to xmpp server. Caused by: "
                        + e.getMessage());
                xmppManager.startReconnectionThread();
            }

        } else {
            log.debug("Logged in already");
        }

    }
}