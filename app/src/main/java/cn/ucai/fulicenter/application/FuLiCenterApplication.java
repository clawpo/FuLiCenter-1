package cn.ucai.fulicenter.application;

import android.app.Application;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }

    public static FuLiCenterApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = new FuLiCenterApplication();
        }
        instance = this;
        ShareSDK.initSDK(this);
    }
}
