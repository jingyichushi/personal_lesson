import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by lrq on 2017/2/14.
 */
public class ResearchRxTest {

    /**
     * RxJava的一般使用方式
     */
    @Test
    public void normalRx(){
        // create a flowable
        Flowable<String> flowable =Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("hello RxJava 2");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        // create a subscriber
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");

                /**
                 * 我们需要调用request去请求资源，参数就是要请求的数量，
                 * 一般如果不限制请求数量，可以写成Long.MAX_VALUE。如果
                 * 你不调用request，Subscriber的onNext和onComplete方
                 * 法将不会被调用。
                 */
                s.request(Long.MAX_VALUE);
            }

            /**
             * 传入的参数就是Flowable中发射出来的
             * @param s
             */
            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        //组装
        flowable.subscribe(subscriber);
    }
    
}
