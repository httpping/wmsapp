package com.framework.remote.config;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.tpnet.gson.GsonBuilderFactory;
import com.utils.log.NetLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by tp on 2018/04/02.
 *
 * 用于做GSON转换订阅，并处理gson 解析异常问题。
 *
 */
public  abstract class XGsonSubscriber<RESPONSE> extends XSubscriber<String> {
    public String token ;

    //默认的gsonbulider 解决普通的异常问题
    GsonBuilder gsonBulder = GsonBuilderFactory.createDefaultBuild();
    public XGsonSubscriber(Context context) {
        super(context);
        this.context = context;
        this.isShowDialog = true;

    }
    public XGsonSubscriber(Context context, boolean isShowDialog){
        super(context,isShowDialog);
        this.context =context;
        this.isShowDialog = isShowDialog;
    }

    public XGsonSubscriber(Context context, boolean isShowDialog,String token){
        super(context,isShowDialog);
        this.context =context;
        this.isShowDialog = isShowDialog;
        this.token = token;

    }
    public XGsonSubscriber(Context context, boolean isShowDialog,Class ... cls){
        super(context,isShowDialog);
        this.context =context;
        this.isShowDialog = isShowDialog;

    }

    /**
     * 注册解析异常情况
     * @param type
     * @param typeAdapter
     * @return
     */
    public XGsonSubscriber registerTypeAdapter(Type type, Object typeAdapter){
        gsonBulder.registerTypeAdapter(type,typeAdapter);
        return  this;
    }


    public void onNext(String s) {
        try {

            if (context!=null && context instanceof  Activity){
                Activity activity = (Activity) context;
                if (activity.isFinishing()){
                    return;
                }
            }

            NetLog.d("netlog-response",s+"");
            Gson gson = createDefaultGson();
            //获取泛型参数class
            Type mType = getSuperclassTypeParameter(getClass());
//            Class<?> rawType = $Gson$Types.getRawType(mType);

            byte[] bt = s.getBytes();
            ByteArrayInputStream bis = new ByteArrayInputStream(bt);
            TypeAdapter<RESPONSE> adapter = (TypeAdapter<RESPONSE>) gson.getAdapter(TypeToken.get(mType));
            Reader reader = new BufferedReader(new InputStreamReader(bis));
            com.google.gson.stream.JsonReader jsonReader = gson.newJsonReader(reader);


            onSuccess(adapter.read(jsonReader));
            reader.close();
            bis.close();
        }  catch (Exception e){
            e.printStackTrace();
            onError(e);
        }finally {

        }
    }


    /**
     * GSON Type 获取类型
     * @param subclass
     * @return
     */
    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    /**
     * 默认注入注入
     * @return
     */
    private Gson createDefaultGson(){
        return gsonBulder.create();
    }


    /**
     * 获取T的实际类型， 泛型获取方法
     */
    public Class getGenericClass() throws SecurityException {
        System.out.print("getGenericSuperclass:");
        Type t = this.getClass().getGenericSuperclass();
        System.out.println(t);
        ParameterizedType p = (ParameterizedType) t;
        Class c = (Class) p.getActualTypeArguments()[0];
        return  c;
    }

    public abstract void onSuccess(RESPONSE response);


    //自定义bean

}
