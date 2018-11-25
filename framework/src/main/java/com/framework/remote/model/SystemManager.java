package com.framework.remote.model;

/**
 * 获取存放SystemPresenter中的有关推送相关信息
 * @create linjinyan
 */
public class SystemManager {

    private  static SystemManager instance;
    private SystemManager(){}

    /**
     * 单例弱引用
     * @return
     */
    public static SystemManager getInstance(){
        if (instance ==null){
            instance =  new SystemManager();
        }

        return instance;
    }



}
