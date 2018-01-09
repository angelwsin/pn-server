package org.androidpn.client.task;

import java.util.HashMap;
import java.util.Map;

import org.androidpn.client.XmppManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Registration;

public class RegisterTask implements Runnable{
	
	protected final Log log = LogFactory.getLog(getClass());
	
	final XmppManager xmppManager;
	
	final String      username;
	
	final String      password;

    public RegisterTask(XmppManager xmppManager,String username,String password) {
        this.xmppManager = xmppManager;
        this.username  = username;
        this.password  = password;
    }

    public void run() {
        log.debug( "RegisterTask.run()...");

        if (!xmppManager.isRegistered()) {
            final String newUsername = username;//newRandomUUID();
            final String newPassword = password;

            Registration registration = new Registration();

            PacketFilter packetFilter = new AndFilter(new PacketIDFilter(
                    registration.getPacketID()), new PacketTypeFilter(
                    IQ.class));

            PacketListener packetListener = new PacketListener() {

                public void processPacket(Packet packet) {
                    log.debug("RegisterTask.PacketListener ,processPacket().....");
                    log.debug("RegisterTask.PacketListener ,packet="
                            + packet.toXML());

                    if (packet instanceof IQ) {
                        IQ response = (IQ) packet;
                        if (response.getType() == IQ.Type.ERROR) {
                            if (!response.getError().toString().contains(
                                    "409")) {
                                log.debug(
                                        "Unknown error while registering XMPP account! "
                                                + response.getError()
                                                        .getCondition());
                            }
                        } else if (response.getType() == IQ.Type.RESULT) {
                            log.debug("username=" + newUsername);
                            log.debug( "password=" + newPassword);
                            xmppManager.setRegisted(true);
                            log.debug(
                                            "Account registered successfully");
                        }
                    }
                }
            };

            xmppManager.getConnection().addPacketListener(packetListener, packetFilter);

            registration.setType(IQ.Type.SET);
            // registration.setTo(xmppHost);
            // Map<String, String> attributes = new HashMap<String, String>();
            // attributes.put("username", rUsername);
            // attributes.put("password", rPassword);
            // registration.setAttributes(attributes);
            Map<String,String> attr   = new HashMap<String, String>();
            attr.put("username", newUsername);
            attr.put("password", newPassword);
            registration.setAttributes(attr);
            xmppManager.getConnection().sendPacket(registration);

        } else {
            log.debug( "Account registered already");
        }
    }

}
