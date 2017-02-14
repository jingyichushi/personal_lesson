import com.sun.tools.javac.util.StringUtils;
import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import org.junit.Assert;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * RxJava发射列表时的用法
     * 假设 Flowable 发射的是一个列表，接收者要把列表内容依次输出。
     */
    @Test
    public void fromIterable(){

        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(1);
        list.add(5);

        //1th part.一般做法
        Flowable.just(list)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> list) throws Exception {
                        Assert.assertEquals(3, list.size());

                        Assert.assertEquals(10, list.get(0).intValue());
                    }
                });

        //1th part 缺陷：丧失了变化数据流的能力。一旦想要更改列表中的每一个数据，只能
        //在订阅者中做。当然可以使用map来中间处理，但是这样做也需要遍历整个list。

        //2th part.
        //RxJava 2.0 提供了fromIterable方法，可以接收一个 Iterable 容器作为输入，每次发射一个元素

        System.out.print("Flowable.fromIterable:\r\n\t");
        Flowable.fromIterable(list)
                .subscribe(num -> System.out.println(num));


        System.out.print("Observable.fromIterable:\r\n\t");
        Observable.fromIterable(list)
                .subscribe(num -> System.out.println(num));


    }


    /**
     * Flowable.flatMap 可以把一个 Flowable 转换成另一个 Flowable
     * flatMap 返回的是一个 Flowable 对象，可以把从List发射出来的一个
     * 一个的元素发射出去
     */
    @Test
    public void flatMap(){
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(1);
        list.add(5);

        Flowable.just(list)
                .flatMap(new Function<List<Integer>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                        return Flowable.fromIterable(integers);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }


    /**
     * filter 是用于过滤数据的，返回false表示拦截此数据
     */
    @Test
    public void filter(){
        Flowable.fromArray(1, 20, 5, 0, -1, 8)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer.intValue() > 5;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }


    /**
     * take 用于指定订阅者最多收到多少数据
     */
    @Test
    public void take(){
        Flowable.fromArray(1, 2, 3, 4)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }


    /**
     * doOnNext 允许我们在每次输出一个元素之前做一些额外的事情，比如记录日志
     */
    @Test
    public void doOnNext(){
        Flowable.just(1, 2, 3)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("保存:" + integer);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }


    /**
     * 参数 0 或者 1
     * @param value
     */
    private void handleFinish(int value){
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("exception:" + (1 / value));
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("on complete");
                    }
                });
    }


    /**
     * 这样的设计有以下几个优点:
     * 
     * 只要发生错误，onError()一定会被调用。
     * 这极大的简化了错误处理。只需要在一个地方处理错误即可以。
     *
     * 操作符不需要处理异常。
     * 将异常处理交给订阅者来做，一旦有调用链中有一个抛出了异常，就会直接执行onError()方法，停止数据传送。
     *
     * 你能够知道什么时候订阅者已经接收了全部的数据。
     */

    @Test
    public void onError(){
        handleFinish(0);
    }


    @Test
    public void onComplete(){
        handleFinish(1);
    }
}

