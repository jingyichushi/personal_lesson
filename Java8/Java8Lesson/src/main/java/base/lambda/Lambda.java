package base.lambda;

import java.util.Arrays;

/**
 * Created by lrq on 2017/2/11.
 */
public class Lambda {

    public static void main(String[] args) {

        //最简单的Lambda表达式包括:
        //   1）逗号分隔的参数列表
        //   2）->符号
        //   3）语句块

        //参数类型自动推断
        Arrays.asList("a", "b", "d").forEach(
                e -> System.out.println(e)
        );

        //声明参数类型
        //当声明了参数类型时，参数列表括号——"("或")"——不能省略
        Arrays.asList("a", "b", "d").forEach(
                (String e) -> System.out.println(e)
        );

        String separator = ",";
        final String prefix = "   ";

        //当有"{...}"时：
        // 1.每条语句后的";"不能省略
        // 2.若表达式要求有返回值——非void，则必须明确声明return
        Arrays.asList("a", "b", "d").forEach(
                e -> {
                    //Lambda表达式可以引用类成员和局部变量（
                    // 会将这些变量隐式得转换成final的）
                    System.out.println(prefix + e + separator);

                    //表达式无返回值，因而下述return可以省略
                    return;
                }
        );

        //Lambda表达式有返回值，返回值的类型也由编译器推理得出

        //如语句块只有一条，则可以不用使用return语句
        Arrays.asList("a", "b", "d").sort(
                (e1, e2) -> e1.compareTo(e2)
        );

        //1.当多于一个参数时，参数列表括号——"("或")"——不能省略
        //2.当语句块中的多于一条语句时必须使用->{...}
        Arrays.asList("a", "b", "d").sort((e1, e2) -> {
            int result = e1.compareTo(e2);
            return result;
        });

    }

}
