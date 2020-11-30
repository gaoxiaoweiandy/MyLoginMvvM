package com.example.myloginmvvm.bean;

/**
 * Created by xw.gao
 * 二次包装了服务器接口返回的data数据，并添加了各状态（如LOADING状态一般显示进度框，SUCCESS状态更新UI），在activity和BaseActivity
 * 里实现了各状态对应的回调函数(如callback.onLoading(errorMsg)和callback.onSuccess(data)).
 */

public class Result<T> {
    public static final int LOADING = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;       //网络异常等
    public static final int FAIL = 3;       //业务失败
    public static final int PROGRESS = 4;   //下载上传文件
    public int state;                       //状态的值即上面的常量
    public String errorMsg;             //业务失败时返回的失败原因
    public T data;                      //业务数据
    public Throwable error;             //ERROR状态下的异常信息

    public int precent;//文件下载百分比
    public long total;//文件总大小

    public Result(int state, T data, String errorMsg) {
        this.state = state;
        this.errorMsg = errorMsg;
        this.data = data;
    }
    public Result(int state, Throwable error) {
        this.state = state;
        this.error = error;
    }
    public Result(int state, int precent, long total) {
        this.state = state;
        this.precent = precent;
        this.total = total;
    }
    public static <T> Result<T> loading(String showMsg) {
        return new Result<>(LOADING, null, showMsg);
    }


    public static <T> Result<T> response(JsonDataCommon<T> data) {
        if (data != null) {
            if (data.isSuccess()) {
                return new Result<>(SUCCESS, data.getData(), null);
            }
            return new Result<>(FAIL, null, data.getMsg());
        }
        return new Result<>(ERROR, null, null);
    }
    public static <T> Result<T> response2(JsonDataCommon<T> data) {
        if (data != null) {
            if (data.isSuccess()) {
                return new Result<>(SUCCESS, data.getObjectbean(), null);
            }
            return new Result<>(FAIL, null, data.getMsg());
        }
        return new Result<>(ERROR, null, null);
    }

    public static <T> Result<T> failure(String msg) {
        return new Result<>(ERROR, null, msg);
    }

    public static <T> Result<T> error(Throwable t) {
        return new Result<>(ERROR, t);
    }

    public static <T> Result<T> progress(int precent, long total) {
        return new Result<>(PROGRESS, precent, total);
    }

    public void handler(OnHandleCallback<T> callback) {
        switch (state) {
            case LOADING:
                callback.onLoading(errorMsg);
                break;
            case SUCCESS:
                callback.onSuccess(data);
                break;
            case FAIL:
                callback.onFailure(errorMsg);
                break;
            case ERROR:
                callback.onError(error);
                break;
            case PROGRESS:
                callback.onProgress(precent,total);
                break;
        }

        if (state != LOADING) {
            callback.onCompleted();
        }
    }

    public interface OnHandleCallback<T> {
        void onLoading(String showMessage);

        void onSuccess(T data);

        void onFailure(String msg);

        void onError(Throwable error);

        void onCompleted();

        void onProgress(int precent,long total);
    }


}
