package base;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by lrq on 2017/2/13.
 */
public class HelloWorld {



    public static void main(String[] args) {

        Flowable.just("Hello world").subscribe(System.out::println);

    }

    /**
     * Subscriber接口同Observer接口的异同
     * @return
     */
    private static Object observerVsSubscriber() {

        //Observer和Subscriber的接口基本一致，不同的是onSubscribe的签名
        //Observer<String> observer = new Observer<String>() {
        Subscriber<String> subscriber = new Subscriber<String>(){

            @Override
            public void onNext(String s) {
                System.out.println("Item: " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error!");
            }

            //  接口Observer的方法
            //  @Override
            //  public void onSubscribe(Disposable d) {

            //  }
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("I'm subscribed!");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed!");
            }
        };

        return subscriber;
    }
}
