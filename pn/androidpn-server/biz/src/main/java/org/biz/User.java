package org.biz;

import java.math.BigDecimal;

public class User {
    
    
    private String userName;
    
    //经度
    private BigDecimal longitude;
    //纬度
    private BigDecimal latitude;
    @Override
    public String toString() {
        return "User [userName=" + userName + ", longitude=" + longitude + ", latitude=" + latitude + "]";
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public BigDecimal getLongitude() {
        return longitude;
    }
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    public BigDecimal getLatitude() {
        return latitude;
    }
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    
    

}
