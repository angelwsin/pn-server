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

import java.math.BigDecimal;

import org.androidpn.client.ServiceManager;
import org.biz.User;
import org.jivesoftware.smack.packet.Message;

import com.alibaba.fastjson.JSON;

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

    
    public static void main(String[] args) {
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.startService();      
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Message msg = new Message();
        User user = new User();
        user.setUserName("haha");
        user.setLatitude(new BigDecimal(10));
        user.setLongitude(new BigDecimal(20));
        msg.setBody("local   "+JSON.toJSONString(user));
        msg.setFrom("haha");
        msg.setTo("System");
        serviceManager.Msgservice.get().getXmppManager().getConnection().sendPacket(msg);
        
        /*Thread.sleep(50000);
        Message send = new Message();
        send.setBody("send    "+" to haha ");
        send.setFrom("zhgansan");
        send.setTo("System");
        serviceManager.Msgservice.get().getXmppManager().getConnection().sendPacket(send);*/
    }
}