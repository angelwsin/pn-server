/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.androidpn.client.task.ConnectTask;
import org.androidpn.client.task.LoginTask;
import org.androidpn.client.task.MessageTask;
import org.androidpn.client.task.RegisterTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * This class is to manage the XMPP connection between client and server.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class XmppManager {

    private static final String LOGTAG = LogUtil.makeLogTag(XmppManager.class);
    
    protected final Log log = LogFactory.getLog(getClass());


    private NotificationService.TaskSubmitter taskSubmitter;

    private NotificationService.TaskTracker taskTracker;

    private XMPPConnection connection;

    private ConnectionListener connectionListener;

    private PacketListener notificationPacketListener;


    private BlockingQueue<Runnable> taskList;

    private boolean running = false;

    private Future<?> futureTask;

    private Thread reconnection;
    
    private boolean isRegisted ;

    public XmppManager(NotificationService notificationService) {
        taskSubmitter = notificationService.getTaskSubmitter();
        taskTracker = notificationService.getTaskTracker();
        connectionListener = new PersistentConnectionListener(this);
        notificationPacketListener = new NotificationPacketListener(this);

        taskList = new LinkedBlockingQueue<Runnable>();
        reconnection = new ReconnectionThread(this);
        new Thread(){
        	public void run() {
        		while(true){
        			try {
						Runnable task = taskList.take();
						if(task!=null){
							task.run();
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}
        	}

        }.start();
    }


    public void connect(String xmppHost,int xmppPort) {
        log.debug( "connect()...");
        submitConnectTask(xmppHost,xmppPort);
    }
    
    public void connect() {
        log.debug( "connect()...");
        submitConnectTask("localhost",5222);
    }

    public void disconnect() {
        log.debug( "disconnect()...");
        terminatePersistentConnection();
    }

    public void terminatePersistentConnection() {
        log.debug( "terminatePersistentConnection()...");
        Runnable runnable = new Runnable() {

            final XmppManager xmppManager = XmppManager.this;

            public void run() {
                if (xmppManager.isConnected()) {
                    log.debug( "terminatePersistentConnection()... run()");
                    xmppManager.getConnection().removePacketListener(
                            xmppManager.getNotificationPacketListener());
                    xmppManager.getConnection().disconnect();
                }
            }

        };
        addTask(runnable);
    }

    public XMPPConnection getConnection() {
        return connection;
    }

    public void setConnection(XMPPConnection connection) {
        this.connection = connection;
    }


    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }

    public PacketListener getNotificationPacketListener() {
        return notificationPacketListener;
    }

    public void startReconnectionThread() {
        synchronized (reconnection) {
            if (!reconnection.isAlive()) {
                reconnection.setName("Xmpp Reconnection Thread");
                reconnection.start();
            }
        }
    }

   
    
    public Future<?> getFutureTask() {
        return futureTask;
    }

   

    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    public boolean isAuthenticated() {
        return connection != null && connection.isConnected()
                && connection.isAuthenticated();
    }

    public boolean isRegistered() {
        return isRegisted;
    }

    
    
    public void setRegisted(boolean isRegisted) {
        this.isRegisted = isRegisted;
    }


    private void submitConnectTask(String xmppHost,int xmppPort) {
        log.debug( "submitConnectTask()...");
        addTask(new ConnectTask(this,xmppHost,xmppPort));
        //addTask(new LocalTask());
    }

    public void submitRegisterTask(String username,String password) {
        log.debug( "submitRegisterTask()...");
        //submitConnectTask();
        addTask(new RegisterTask(this,username,password));
    }

    public void submitLoginTask(String username,String password) {
        log.debug("submitLoginTask()...");
        addTask(new LoginTask(this,username,password));
    }
    
    public <T> void  submitMsgTask(T data,String to) {
        log.debug("submitMsgTask()...");
        addTask(new MessageTask<T>(this,data,to));
    }

    private void addTask(Runnable runnable) {
        log.debug("addTask(runnable)...");
        taskList.offer(runnable);
    }



}
