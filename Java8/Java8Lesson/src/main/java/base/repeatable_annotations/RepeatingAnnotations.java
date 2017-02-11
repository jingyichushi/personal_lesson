package base.repeatable_annotations;

import java.lang.annotation.*;

/**
 * Created by lrq on 2017/2/11.
 */
public class RepeatingAnnotations {


    @Target( ElementType.TYPE )
    @Retention( RetentionPolicy.RUNTIME )
    //存放Filter注解的容器
    public @interface Filters {
        Filter[] value();
    }

    @Target( ElementType.TYPE )
    @Retention( RetentionPolicy.RUNTIME )
    //使用@Repeatable(Filters.class)注解修饰
    @Repeatable( Filters.class )
    public @interface Filter {
        String value();
    };

    @Filter( "filter1" )
    @Filter( "filter2" )
    public interface Filterable {
    }

    public static void main(String[] args) {
        for( Filter filter: Filterable.class.getAnnotationsByType( Filter.class ) ) {
            System.out.println( filter.value() );
        }
    }
}