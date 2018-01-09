package org.androidpn.client.task;

import org.androidpn.client.NotificationIQProvider;
import org.androidpn.client.XmppManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.ProviderManager;

public class ConnectTask implements Runnable {
	
	protected final Log log = LogFactory.getLog(getClass());

	private final XmppManager xmppManager;
    
    private final  String xmppHost;

    private final  int xmppPort;

    public ConnectTask(XmppManager xmppManager, String xmppHost,int xmppPort) {
        this.xmppManager = xmppManager;
        this.xmppHost = xmppHost;
        this.xmppPort = xmppPort;
    }

    public void run() {
        log.debug( "ConnectTask.run()...");

        if (!xmppManager.isConnected()) {
            // Create the configuration for this new connection
            ConnectionConfiguration connConfig = new ConnectionConfiguration(
                    xmppHost, xmppPort);
            connConfig.setSecurityMode(SecurityMode.disabled);
            //connConfig.setSecurityMode(SecurityMode.required);
            connConfig.setSASLAuthenticationEnabled(false);
            connConfig.setCompressionEnabled(false);

            XMPPConnection connection = new XMPPConnection(connConfig);
            xmppManager.setConnection(connection);

            try {
                // Connect to the server
                connection.connect();
                log.debug("XMPP connected successfully");

                // packet provider
                ProviderManager.getInstance().addIQProvider("notification",
                        "androidpn:iq:notification",
                        new NotificationIQProvider());

            } catch (XMPPException e) {
                log.debug( "XMPP connection failed", e);
            }

        } else {
            log.debug( "XMPP connected already");
        }
    }
}
