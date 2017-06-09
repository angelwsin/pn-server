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
package org.androidpn.demoapp;

import org.androidpn.client.ServiceManager;
import org.jivesoftware.smack.packet.Message;

/**
 * This is an androidpn client demo application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DemoAppActivity {

    
    public static void onCreate() {
               // Start the service
        ServiceManager serviceManager = new ServiceManager();
                serviceManager.startService();
    }

    
    public static void main(String[] args) throws Exception{
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.startService();      
        Thread.sleep(5000);
        Message msg = new Message();
        msg.setBody("zhangs");
        msg.setFrom("zhgansan");
        msg.setTo("lisi");
        serviceManager.Msgservice.get().getXmppManager().getConnection().sendPacket(msg);
    }
}