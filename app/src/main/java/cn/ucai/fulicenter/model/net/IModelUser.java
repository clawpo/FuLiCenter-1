package cn.ucai.fulicenter.model.net;

import android.content.Context;

/**
 * Created by Administrator on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String username, String password, onCompleteListener<String> listener);

    void register(Context context, String username, String usernick, String password, onCompleteListener<String> listener);

}
