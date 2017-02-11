package base.ref_method;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by lrq on 2017/2/11.
 */
public class RefMethodExample {

    public static class Car {
        public static Car create( final Supplier< Car > supplier ) {
            return supplier.get();
        }

        public static void collide( final Car car ) {
            System.out.println( "Collided " + car.toString() );
        }

        public void follow( final Car another ) {
            System.out.println( "Following the " + another.toString() );
        }

        public void repair() {
            System.out.println( "Repaired " + this.toString() );
        }
    }

    public static void main(String[] args) {

        //构造器引用，Class::new，或更一般的形式：Class<T>::new
        //构造器没有参数
        final Car car = Car.create( Car::new );
        final List< Car > cars = Arrays.asList( car );

        //静态方法引用，Class::static_method
        //方法接受一个Car类型的参数
        cars.forEach( Car::collide );


        //类的成员方法的引用，Class::method
        //方法没有定义入参
        cars.forEach( Car::repair );

        final Car police = Car.create( Car::new );

        //实例对象的成员方法，instance::method
        //方法接受一个Car类型的参数
        cars.forEach( police::follow );
    }

}
