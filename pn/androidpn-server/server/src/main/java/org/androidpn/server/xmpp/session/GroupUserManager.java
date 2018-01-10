package org.androidpn.server.xmpp.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.androidpn.server.service.UserNotFoundException;
import org.biz.Group;

public class GroupUserManager {
    
    //传统的以人 为维度   此时基于 地理位置为维度
    
    /**
     * 每一个人 都有一个当前位置 这样一个 坐标
     * 
     */
    
    private static ReentrantLock lock = new ReentrantLock();
    private static final ConcurrentHashMap<Group, Map<String,ClientSession>> groupUser = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Group>  users = new ConcurrentHashMap<String, Group>();
    
    
    public static void addGroupUser(Group group,ClientSession session){
           lock.lock();
         try {
        	 Map<String,ClientSession> users =   groupUser.get(group);
             if(users==null){
                 users = new HashMap<>();
                 groupUser.put(group, users);
             }
             try {
				users.put(session.getUsername(),session);
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
        } finally {
          lock.unlock();
        }
        
    }
    
    
    public static void addLineUser(String userName,Group group){
        users.put(userName, group);
    }
    
    public static Group getLineUser(String userName){
         return users.get(userName);
    }
    
    
    

}
