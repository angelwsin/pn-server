package org.biz.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.biz.Group;
import org.biz.User;

public class GroupUserManager {
    
    //传统的以人 为维度   此时基于 地理位置为维度
    
    /**
     * 每一个人 都有一个当前位置 这样一个 坐标
     * 
     */
    
    private static ReentrantLock lock = new ReentrantLock();
    private static final ConcurrentHashMap<Group, List<User>> groupUser = new ConcurrentHashMap<Group, List<User>>();
    private static final ConcurrentHashMap<String, Group>  users = new ConcurrentHashMap<String, Group>();
    
    
    public static void addGroupUser(Group group,User user){
           lock.lock();
         try {
             List<User> users =   groupUser.get(group);
             if(users==null){
                 users = new ArrayList<User>();
                 groupUser.put(group, users);
             }
             users.add(user);
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
