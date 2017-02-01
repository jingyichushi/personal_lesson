class GroovyClz extends GroovyAbstractClz implements GroovyTrait, GroovyAnotherTrait {

    int x = 100;

    def updateX(arg1) {
        this.x = arg1;
    }

    void abstractFuncInAbstractClass() {
        println "I'm a abstract function in abstract class need implemented!"
    }

    void displayAnotherInterfaceInfo() {
        println "I'm another function not implemented directly in trait for an interface named GroovyInterface!"
    }

    void abstractFuncInAnotherTrait() {
        println "I'm a abstract function in trait need implemented!"
    }

    static void main(args) {
        println "Hello,I'm groovy class!";

        GroovyClz ex = new GroovyClz();

        println("Before update:" + ex.getX())

        ex.updateX(300);
        println "After update:" + ex.getX()

        ex.setX(200);
        println "After set:" + ex.getX()

        println "The trait get function: ${ex.getName()}";

        println "The trait default function:";

        print "\t"

        ex.displayName();

        println "Call the implemented interface function in trait:";

        print "\t"

        ex.displayInterfaceInfo();

        print "\t"

        ex.displayAnotherInterfaceInfo();

        print "\t"

        ex.abstractFuncInAnotherTrait();

        print "\t"

        ex.abstractFuncInAbstractClass();

        ex.displayTraitParentName();

        ex.displayAnotherTraitName();

        println("The list will only display those numbers which are divisible by 2")
//        lst.each { println it }
        def lst = [1, 2, 3, 4];

        lst.each({
            num ->
                if (num % 2 == 0)
                    println num
        })
        //上述语句等价于下述语句
        //闭包作为唯一的尾参，可以省略调用括号
        lst.each{
            num ->
                if (num % 2 == 0)
                    println num
        }





        def str1 = "Hello";
        def clos = { param -> println "${str1} ${param}" }
        clos.call("World");

        // We are now changing the value of the String str1
        // which is referenced in the closure
        str1 = "Welcome";
        clos.call("World");

    }

}


