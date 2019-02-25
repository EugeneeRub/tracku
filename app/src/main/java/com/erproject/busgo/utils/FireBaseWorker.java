package com.erproject.busgo.utils;

import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FireBaseWorker {

    public static List<UserModel> getList(Map<String, FbConnectedUser> mapOfUsers) {
        HashMap<String, FbConnectedUser> map = new HashMap<>(mapOfUsers);
        List<UserModel> userModeList = new ArrayList<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            UserModel userModel = new UserModel();
            userModel.setName((String) pair.getKey());
            userModel.setUser((FbConnectedUser) pair.getValue());

            userModeList.add(userModel);

            it.remove();// avoids a ConcurrentModificationException
        }
        return userModeList;
    }

}
