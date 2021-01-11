package com.huajia.daily.common;

public class UserService implements IUserService{
    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
