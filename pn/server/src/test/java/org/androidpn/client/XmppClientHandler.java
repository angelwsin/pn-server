package org.androidpn.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.androidpn.server.xmpp.net.XmppServerIoHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.openfire.nio.XMLLightweightParser;

public class XmppClientHandler extends ChannelHandlerAdapter {
    
    private static final Log log = LogFactory.getLog(XmppClientHandler.class);
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String s = "<?xml version='1.0'?><stream:stream ";
        ByteBuf buf = Unpooled.buffer(s.getBytes().length);
        buf.writeBytes(s.getBytes());
        ctx.channel().writeAndFlush(buf);
        String x = "to='example_com' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0'>";
        ByteBuf  b = Unpooled.buffer(x.getBytes().length);
        b.writeBytes(x.getBytes());
        ctx.channel().writeAndFlush(b);
      
    }
    
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client:"+msg);
        String body = "<message from='juliet_example_com' to='username' xml:lang='en'> <body>Art thou not Romeo, and a Montague?</body></message>";
        ByteBuf  b = Unpooled.buffer(body.getBytes().length);
        b.writeBytes(body.getBytes());
        ctx.channel().writeAndFlush(b);
        //ctx.fireChannelRead(msg);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.channel().close();
    }
    
    
    public static void main(String[] args)throws Exception {
        String s = "<message from='juliet_example_com' to='username' xml:lang='en'> <body>Art thou not Romeo, and a Montague?</body></message>";

        ByteBuf buf = Unpooled.buffer(s.getBytes().length);
        buf.writeBytes(s.getBytes());
        XMLLightweightParser pas   = new XMLLightweightParser("utf-8");
        byte[] dst = new byte[1024];
        buf.getBytes(0, dst, 0, s.getBytes().length);
        System.out.println(new String(dst));
        pas.read(buf);
    }
    

}
