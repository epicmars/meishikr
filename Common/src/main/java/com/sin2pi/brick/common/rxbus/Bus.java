package com.sin2pi.brick.common.rxbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yinhang on 16/7/7.
 */
public class Bus {

    //private final PublishSubject<Object> _bus = PublishSubject.create();
    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    private final Subject<Object, Object> publisher = new SerializedSubject<>(PublishSubject.create());
    private final CompositeSubscription subscriber = new CompositeSubscription();

    public void register(Object object) {
        if (null == object) {
            throw new NullPointerException("Object to register must not be null.");
        }
        final Object obj = object;
        Class<?> cls = obj.getClass();
        Method[] methods = cls.getDeclaredMethods();
        if (methods.length == 0) {
            return;
        }
        for (Method m : methods) {
            if(!m.isAnnotationPresent(Subscribe.class))
                continue;
            Type[] types = m.getParameterTypes();
            if(types.length != 1)
                continue;
            Type type = types[0];
            final Method method = m;
            method.setAccessible(true);
            subscriber.add(
                    publisher.observeOn(Schedulers.immediate())
                            .subscribe(new Action1<Object>() {
                                @Override
                                public void call(Object event) {
                                    try{
                                        method.invoke(obj, event);
                                    } catch (IllegalAccessException e){
                                        
                                    } catch (IllegalArgumentException e){

                                    } catch (InvocationTargetException e){

                                    }
                                }
                            }));
        }

    }

    public void unregister(Object object) {
        if(subscriber.hasSubscriptions()){
            subscriber.clear();
        }
    }

    public void post(final Object event) {
        publisher.onNext(event);
    }
}
