package org.androidpn.server.xmpp.net;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.androidpn.server.xmpp.build.BootStrapServerBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.XMPPPacketReader;
import org.jivesoftware.openfire.net.MXParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmppServerIoHandler extends ChannelHandlerAdapter {
    
    private static final Log log = LogFactory.getLog(XmppServerIoHandler.class);

    private static Map<Integer, XMPPPacketReader> parsers = new ConcurrentHashMap<Integer, XMPPPacketReader>();
    

    private static XmlPullParserFactory factory = null;

    static {
        try {
            factory = XmlPullParserFactory.newInstance(
                    MXParser.class.getName(), null);
            factory.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            log.error("Error creating a parser factory", e);
        }
    }
    
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("messageReceived()...");
        log.debug("RCVD: " + msg);

        // Get the stanza handler
        StanzaHandler handler =  ctx.channel()
                .attr(BootStrapServerBuilder.STANZA_HANDLER).get();

        // Get the XMPP packet parser
        int hashCode = Thread.currentThread().hashCode();
        XMPPPacketReader parser = parsers.get(hashCode);
        if (parser == null) {
            parser = new XMPPPacketReader();
            parser.setXPPFactory(factory);
            parsers.put(hashCode, parser);
        }

        // The stanza handler processes the message
        try {
            handler.process((String) msg, parser);
        } catch (Exception e) {
            log.error(
                    "Closing connection due to error while processing message: "
                            + msg, e);
            Connection connection =   ctx.channel()
                    .attr(BootStrapServerBuilder.CONNECTIONKEY).get();;
            connection.close();
        }

    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("exceptionCaught()...");
        log.error(cause);
    }

    
    

}
