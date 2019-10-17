package com.gofishfarm.htkj.base.retrofit;


import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.exception.ApiException;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 描述:RxUtils
 */
@SuppressWarnings("Convert2Lambda")
public class RxHelper {

    public static <T>ObservableTransformer<T, T> lifeTransForme(){
        ObservableTransformer<T, T> observableTransformer = new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return  upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
        return observableTransformer;
    }

    /**
     * 线程处理
     * @return
     */
  public static FlowableTransformer io_main() {
    return new FlowableTransformer() {
      @Override
      public Publisher apply(Flowable upstream) {
        return upstream.subscribeOn(Schedulers.io())
                //使用observerOn()指定订阅者运行的线程
                .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程 统一处理线程
        //noinspection Convert2Lambda
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> observable) {
                //使用subscribeOn()指定被观察者代码运行的线程
                return observable.subscribeOn(Schedulers.io())
                        //使用observerOn()指定订阅者运行的线程
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<BaseBean> createData(final BaseBean t) {
        return Flowable.create(new FlowableOnSubscribe<BaseBean>() {
            @Override
            public void subscribe(FlowableEmitter<BaseBean> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<List<T>> createData(final List<T> t) {
        return Flowable.create(new FlowableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(FlowableEmitter<List<T>> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }


    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseBean, T> handleMsgResult() {
        return new FlowableTransformer<BaseBean, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseBean> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseBean baseBean) throws Exception {
                        if (baseBean.getCode()==200) {
                            if (baseBean != null)
                                return (Flowable<T>) createData(baseBean);
                            return Flowable.error(new ApiException("服务器返回error"));
                        } else {
                            return Flowable.error(new ApiException(baseBean.getMessage()));
                        }
                    }
                });
            }
        };
    }


    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseBean<T>, T> handleResult() {
        return new FlowableTransformer<BaseBean<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<BaseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseBean<T> httpResponse) throws Exception {
                        if (httpResponse.getCode()==200) {
                            if (httpResponse.getData() != null)
                                return createData(httpResponse.getData());
                            return Flowable.error(new ApiException("服务器返回error"));
                        } else {
                            return Flowable.error(new ApiException(httpResponse.getMessage()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseBean<List<T>>, List<T>> handleListResult() {
        return new FlowableTransformer<BaseBean<List<T>>, List<T>>() {
            @Override
            public Publisher<List<T>> apply(Flowable<BaseBean<List<T>>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean<List<T>>, Flowable<List<T>>>() {
                    @Override
                    public Flowable<List<T>> apply(BaseBean<List<T>> httpResponse) throws Exception {
                        if (httpResponse.getCode()==200) {
                            if (httpResponse.getData() != null)
                                return createData(httpResponse.getData());
                            return Flowable.error(new ApiException("服务器返回error"));
                        } else {
                            return Flowable.error(new ApiException(httpResponse.getMessage()));
                        }
                    }
                });
            }
        };
    }


}