package org.biz.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.biz.Context;
import org.biz.Group;
import org.biz.User;
import org.biz.user.GroupUserManager;

import com.alibaba.fastjson.JSON;

public class GroupLocalProcessor implements Proccessor{

    private static final Log log = LogFactory.getLog(GroupLocalProcessor.class);
    @Override
    public void proccess(Object content) {
       log.info(content); 
       Context context = (Context) content;
       User user =  JSON.parseObject((String)context.getContent(), User.class);
       Group group = new Group();
       group.setUserName(user.getUserName());
       group.setGroupName("我的位置");
       group.setLatitude(user.getLatitude());
       group.setLongitude(user.getLongitude());
       group.setLocal("杭州市...");
       //GroupUserManager.addGroupUser(group, user);
       GroupUserManager.addLineUser(user.getUserName(), group);
       
    }

}
