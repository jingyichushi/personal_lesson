import com.sun.tools.javac.util.StringUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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


    /**
     * RxJava的极简使用方式
     * Flowable.just 和 Consumer
     */
    @Test
    public void simplestRx(){

        //1.可以用just传入生成一到九个同类型的Object快速创建Flowable”发射器”
        Flowable<String> flowable = Flowable.just("hello RxJava 2");

        //2.Consumer是仅仅关系onNext方法的Subscriber"接收器"
        Consumer consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
                Assert.assertEquals(s,"hello RxJava 2");
            }
        };

        //组装
        flowable.subscribe(consumer);


        //简化
        Flowable.just("hello RxJava 2").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        //极简
        Flowable.just("hello RxJava 2").subscribe(str->{
            System.out.println(str);
        });
    }


    /**
     * 操作符是为了解决 Flowable 对象变换问题而设计的，
     * 操作符可以在传递的途中对数据进行修改
     */
    @Test
    public void mapOperatorRx(){
        Flowable.just("map")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return StringUtils.toUpperCase(s);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Assert.assertEquals(s,"MAP");
                    }
                });
    }

    /**
     * map 操作符更神奇的地方是，你可以返回任意类型的 Flowable，
     * 也就是说你可以使用 map 操作符发射一个新的数据类型的 Flowable 对象。
     */
    @Test
    public void moreComplexMapOperatorRx(){

        Flowable.just("map1")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Assert.assertEquals(s,String.valueOf("map1".hashCode()));
                    }
                });
    }
}
