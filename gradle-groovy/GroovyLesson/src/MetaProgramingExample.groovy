/**
 * Created by lrq on 2017/1/31.
 */
class MetaProgramingExample {
    static void main(String[] args) {
        Student mst = new Student();
        mst.Name = "Joe";
        mst.ID = 1;

        println(mst.Name);
        println(mst.ID);
    }
}

//    task hello << {
//        println "hello"
//    }
//    //等价于
//    task("hello").leftShift({
//        println "hello"
//    })



interface CustomItf{
    void first();
    void second();
}

class Student implements GroovyInterceptable {
    protected dynamicProps=[:]

    void setProperty(String pName,val) {
        dynamicProps[pName] = val
    }

    def getProperty(String pName) {
        dynamicProps[pName]
    }
}