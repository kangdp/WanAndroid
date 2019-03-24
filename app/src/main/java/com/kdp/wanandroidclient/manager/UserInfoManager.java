package com.kdp.wanandroidclient.manager;

import android.text.TextUtils;
import android.util.Base64;

import com.kdp.wanandroidclient.bean.User;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.utils.AesEncryptionUtils;
import com.kdp.wanandroidclient.utils.PreUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.spec.SecretKeySpec;

/**
 * 用户信息管理类
 * author: 康栋普
 * date: 2018/3/1
 */

public class UserInfoManager {

    /**
     * 获取用户信息
     * @return
     */
    public static User getUserInfo() {
        SecretKeySpec keySpec = getAesKey();
        String userInfo = AesEncryptionUtils.decrypt(keySpec, (String) PreUtils.get(Const.USERINFO_KEY.USER_INFO, ""));
        if (TextUtils.isEmpty(userInfo)) return null;
        try {
            return translateStringTOUserInfo(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存用户信息
     * @param user
     */
    public static void saveUserInfo(User user){
        try {
            String  userInfo = translateUserInfoTOString(user);
            SecretKeySpec key = AesEncryptionUtils.createKey();
            String aesContent = AesEncryptionUtils.encrypt(key, userInfo);
            //保存用户信息
            PreUtils.put(Const.USERINFO_KEY.USER_INFO, aesContent);
            //保存密钥
            saveAesKey(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void saveAesKey(SecretKeySpec keySpec){
        PreUtils.put(Const.USERINFO_KEY.AES, Base64.encodeToString(keySpec.getEncoded(),Base64.DEFAULT));
    }

    private static SecretKeySpec getAesKey(){
        String keyStr = (String) PreUtils.get(Const.USERINFO_KEY.AES, "");
        return AesEncryptionUtils.getSecretKey(Base64.decode(keyStr, Base64.DEFAULT));
    }

    public static boolean isLogin() {
        return (boolean) PreUtils.get(Const.USERINFO_KEY.IS_LOGIN, false);
    }

    public static void saveIsLogin(boolean isLogin){
        PreUtils.put(Const.USERINFO_KEY.IS_LOGIN,isLogin);
    }

    /**
     * User 转 String
     * @param user
     * @return
     * @throws IOException
     */
    private static String translateUserInfoTOString(User user) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(user);
        return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
    }

    /**
     * String 转 User
     * @param userStr
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static User translateStringTOUserInfo(String userStr) throws IOException, ClassNotFoundException {
        if (userStr == null) return null;
        byte[] base64Bytes = Base64.decode(userStr,Base64.DEFAULT);
        ByteArrayInputStream bis = new ByteArrayInputStream(base64Bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (User) ois.readObject();
    }
}
