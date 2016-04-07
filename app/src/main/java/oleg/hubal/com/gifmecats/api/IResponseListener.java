package oleg.hubal.com.gifmecats.api;

/**
 * Created by User on 06.04.2016.
 */
public interface IResponseListener<T> {
    void onStart();
    void onFinish(final boolean isSuccess, final T response);
}
