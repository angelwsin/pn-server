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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.ConnectionListener;



/** 
 * A listener class for monitoring connection closing and reconnection events.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class PersistentConnectionListener implements ConnectionListener {

    private static final String LOGTAG = LogUtil
            .makeLogTag(PersistentConnectionListener.class);

    private final XmppManager xmppManager;
    protected final Log log = LogFactory.getLog(getClass());

    public PersistentConnectionListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    @Override
    public void connectionClosed() {
        log.debug( "connectionClosed()...");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        log.debug(  "connectionClosedOnError()...");
        if (xmppManager.getConnection() != null
                && xmppManager.getConnection().isConnected()) {
            xmppManager.getConnection().disconnect();
        }
        xmppManager.startReconnectionThread();
    }

    @Override
    public void reconnectingIn(int seconds) {
        log.debug(  "reconnectingIn()...");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        log.debug(  "reconnectionFailed()...");
    }

    @Override
    public void reconnectionSuccessful() {
        log.debug(  "reconnectionSuccessful()...");
    }

}
