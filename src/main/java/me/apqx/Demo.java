package me.apqx;


import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import me.apqx.util.Tools;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Demo {
    private static Disposable disposable;

    public static void main(String[] args) throws Exception {
        System.out.println(Tools.getMD5("900100001577810f47-ab78-4c21-b1be-262f7a176c72"));
        System.out.println(Thread.currentThread());
        Flowable.range(0, 10)
                .subscribe(new Subscriber<Integer>() {
                    Subscription sub;
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("onSubscribe");
                        sub =s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext " + integer);
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete ");
                    }
                });
        Thread.sleep(10000);
    }

    private void doSth() {

    }


}
