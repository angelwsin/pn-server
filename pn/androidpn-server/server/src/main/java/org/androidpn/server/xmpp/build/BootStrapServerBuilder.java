package org.androidpn.server.xmpp.build;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.androidpn.server.xmpp.XmppServer;
import org.androidpn.server.xmpp.net.Connection;
import org.androidpn.server.xmpp.net.StanzaHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class BootStrapServerBuilder implements InitializingBean{
    
    protected final Log log = LogFactory.getLog(getClass());
    
    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    
    private int  port;
    
    private ChannelHandler  handler;
    
    private String[]  handlers;

    @Override
    public void afterPropertiesSet() throws Exception {
         new Thread(new ServerStartThread(),"xmpp server Thread").start();
        
    }
    
    
    
    public EventLoopGroup getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(EventLoopGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    public EventLoopGroup getChildGroup() {
        return childGroup;
    }

    public void setChildGroup(EventLoopGroup childGroup) {
        this.childGroup = childGroup;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelHandler getHandler() {
        return handler;
    }

    public void setHandler(ChannelHandler handler) {
        this.handler = handler;
    }


    

    public String[] getHandlers() {
        return handlers;
    }



    public void setHandlers(String[] handlers) {
        this.handlers = handlers;
    }




    class ServerStartThread implements Runnable{

        @Override
        public void run() {
            
            try {
                ServerBootstrap  boot = new ServerBootstrap();
                boot.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class) //利用反射生成指定的 channel
                //内核为套接字连接创建的队列，内核中维护着 已连接队列和未连接队列
                .option(ChannelOption.SO_BACKLOG, 1024)//channel的参数设置
                // serversocket的handler
                .handler(handler)
                //ServerBootstrapAcceptor 触发 ChildChannelHandler 的调用   socketchannel的handler
                .childHandler(new ChildChannelHandler());
                
                //绑定端口 同步等待
                //在 bind 调用时 注册三个 handler
                //头handler HeadHandler  完成  nio  ServerSocketChannel.open()
                //第二个 ServerBootstrapAcceptor 中注册的handler childHandler
                // io.netty.channel.socket.nio.NioServerSocketChannel.doReadMessages(List<Object>) 触发
                //ServerBootstrapAcceptor 在客户端连接第一个调用  把客户端的channel 等参数传递给childHandler
                //ServerBootstrapAcceptor 使命就是在服务端启动后 注册自定的 handler
                //第三个 尾 TailHandler
                
                /*
                 * 服务端创建过程
                 * 1.reactor线程模型的指定 ServerBootstrap 服务端启动的辅助类
                 * 2.根据指定类型创建channel的工厂类
                 * 3.设置参数
                 * 4.设置 handler
                 * 5.bind启动
                 *  1)根据channel的工厂类创建channel 从NioEventLoopGroup选择一个EventLoop
                 *  2)channel注册到EventLoop selector中 返回SelectionKey 用于更新感兴趣的事件
                 *  3)channel  isActive 服务端判断是否已经绑定(对客户端是否连接成功)  绑定成功 触发  handler的Active
                 *  4)绑定端口  循环 3)
                 */
                ChannelFuture future =   boot.bind(port).sync().addListener(new GenericFutureListener<Future<? super Void>>() {

                  public void operationComplete(Future<? super Void> future) throws Exception {
                      if(future.isSuccess()){
                          log.info("启动成功");
                      }
                  }
              });
                //等待服务端 端口关闭
                //最终的 调用的委托给 Unsafe
                future.channel().closeFuture().sync();
          }catch(Exception e){
              
          }finally {
              //优雅的关闭
              if(parentGroup!=null){
                parentGroup.shutdownGracefully();  
              }
              if(childGroup!=null){
               childGroup.shutdownGracefully();  
              }
              
          }

            
        }
        
        
    }
    
    class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            log.debug("sessionOpened()...");
            log.debug("remoteAddress=" + ch.remoteAddress().getAddress().toString());
            for(int i=0;i<handlers.length;i++){
               Class<?> hand =  Thread.currentThread().getContextClassLoader().loadClass(handlers[i]);
               ch.pipeline().addLast((ChannelHandler)hand.newInstance());
            }
            Connection conn = new Connection(ch);
            ch.attr(CONNECTIONKEY).set(new Connection(ch));
            ch.attr(STANZA_HANDLER).set(
                new StanzaHandler(XmppServer.getInstance().getServerName(), conn));
           
        }
        
        
    }
    
    public  static final AttributeKey<Connection> CONNECTIONKEY = AttributeKey.valueOf(Connection.class,"CONNECTION");

    public static final AttributeKey<StanzaHandler> STANZA_HANDLER =  AttributeKey.valueOf(StanzaHandler.class,"STANZA_HANDLER");

}
