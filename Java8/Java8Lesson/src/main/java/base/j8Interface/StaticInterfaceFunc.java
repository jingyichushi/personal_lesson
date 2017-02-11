package base.j8Interface;

import static base.Util.*;

import java.util.function.Supplier;

/**
 * Created by lrq on 2017/2/11.
 */
public class StaticInterfaceFunc {

    interface IDuck{
        static void wellcome(){
            System.out.println("I'm a duck,but only a template!");
        }

        String type();
    }

    public static void main(String[] args) {
        System.out.println("调用IDuck接口的静态方法");
        IDuck.wellcome();
    }

}
