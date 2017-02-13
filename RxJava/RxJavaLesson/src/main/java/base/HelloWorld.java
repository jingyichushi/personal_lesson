package base;

import io.reactivex.Flowable;

/**
 * Created by lrq on 2017/2/13.
 */
public class HelloWorld {
    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);
    }
}
