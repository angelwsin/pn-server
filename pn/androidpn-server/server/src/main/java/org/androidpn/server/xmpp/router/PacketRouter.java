/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.router;

import java.util.HashMap;
import java.util.Map;

import org.xmpp.packet.Packet;
import org.xmpp.packet.Roster;

/** 
 * This class is to handle incoming packets and route them to their corresponding handler.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class PacketRouter{

    static final Map<Class<?>,Router>  routers = new HashMap<Class<?>, Router>(4);
    
    /**
     * Routes the packet based on its type.
     * 
     * @param packet the packet to route
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void route(Packet packet) {
        Router router=   routers.get(packet.getClass());
        if(router==null){
            throw new IllegalArgumentException();  
        }
        router.route(packet);
    }


     static {
        Router  messageRouter = new MessageRouter();
        Router presenceRouter = new PresenceRouter();
        Router iqRouter = new IQRouter();
        routers.put(messageRouter.interest(), messageRouter);
        routers.put(presenceRouter.interest(), presenceRouter);
        routers.put(iqRouter.interest(), iqRouter);
        routers.put(Roster.class, iqRouter);
    }

}
