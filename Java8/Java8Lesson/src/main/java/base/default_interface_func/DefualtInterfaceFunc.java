package base.default_interface_func;

import static base.Util.*;

/**
 * Created by lrq on 2017/2/11.
 */
public class DefualtInterfaceFunc {

    interface Formula {
        double calculate(int a);

        void desPrint();

        //有了default关键词，java中的Interface有点类似于Groovy中的trait
        default double sqrt(int a) {
            //默认实现中允许调用interface中的抽象方法
            desPrint();
            return Math.sqrt(a);
        }
    }

    public static void main(String[] args) {
        Formula formula = new Formula() {

            @Override
            public void desPrint() {
                System.out.println("先\"✖️ 100 再 求 平方根操作\"");
            }

            @Override
            public double calculate(int a) {
                //调用接口中的默认方法
                return sqrt(a * 100);
            }
        };
        p("调用抽象方法实现求平方根",formula.calculate(100));
        p("调用接口默认的实现方法",formula.sqrt(16));
    }

}
