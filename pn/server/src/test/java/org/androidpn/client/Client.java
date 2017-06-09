package org.androidpn.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.androidpn.server.xmpp.codec.XmppDecoder;

public class Client{

    
    
    
    
     public static void main(String[] args) throws Exception{
        new Client().connect("localhost",5222);
    }

    private void connect(String host, int port) throws Exception{
        //
        NioEventLoopGroup  bossGroup = new NioEventLoopGroup();
        try{
            Bootstrap  boot = new Bootstrap();
            boot.group(bossGroup)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                   ch.pipeline().addLast(new XmppDecoder()); 
                   ch.pipeline().addLast(new XmppClientHandler());
                }
            });
            
            ChannelFuture future =  boot.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }finally{
            //优雅的退出
            
            bossGroup.shutdownGracefully();
        }
    }
}
