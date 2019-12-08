package org.alie.aliehermes;


public class UserManager implements IUserManager{
    String name;
    private static UserManager sInstance = null;

    private UserManager() {

    }
//约定这个进程A  单例对象的     规则    getInstance()
    public static synchronized UserManager getInstance() {
        if (sInstance == null) {
            sInstance = new UserManager();
        }
        return sInstance;
    }


    @Override
    public String getUser() {
        return name;
    }
}
