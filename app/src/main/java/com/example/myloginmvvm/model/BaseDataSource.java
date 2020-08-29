package com.example.myloginmvvm.model;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.example.myloginmvvm.bean.DataCommon;
import rx.Subscription;


/**
 * 访问首页接口：调用服务器接口获取列表数据
 */
public class BaseDataSource implements LifecycleObserver {
    private  String TAG = BaseDataSource.class.getSimpleName() ;
    protected Subscription mSubscription;


    /**
     * 使用LifeCycle自动调用：即当Activity进入OnStop生命周期时，会被LifecycleObserver感知到
     * 从而自动调用unSubscription函数来释放资源
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void unSubscription()
    {
        if(mSubscription != null)
        {
            mSubscription.unsubscribe();
            Log.i(TAG,"AACunSubscription");
        }
    }

    /**
     *
     * @param jsonData
     * @param throwable
     * @param <T>
     * @return
     */
    public <T extends DataCommon> T createThrowableData(T jsonData, Throwable throwable)
    {
        jsonData.setStatus(-1);
        jsonData.setThrowable(throwable);
        return jsonData;
    }
}