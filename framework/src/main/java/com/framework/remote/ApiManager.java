package com.framework.remote;

import com.framework.remote.api.Api;
import com.tpnet.VpHttpClient;
import com.tpnet.remote.BasicDataManager;

import java.util.HashMap;


/**
 * @author tp
 * @date 2018/07/31
 * 管理product所有的接口列表
 */
public class ApiManager extends BasicDataManager {
    public static final String BASE_URL = "https://httpbin.org/";

    private static ApiManager dataManager;

    private static HashMap<String,VpHttpClient> objectHashMap = new HashMap();

    ApiManager() {
        super();
    }

    public synchronized static ApiManager newInstance() {
        if (dataManager == null) {
            dataManager = new ApiManager();
            dataManager.init(BASE_URL);
        }
        return dataManager;
    }

    @Override
    public void init(String baseUrl) {
        vpNewtWork = new VpHttpClient.Builder().addBaseUrl(baseUrl).build();
    }

    public static Api Api() {
        return newInstance().getInterIml(Api.class);
    }


}
