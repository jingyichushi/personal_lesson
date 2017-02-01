println  "hello groovy"

println "-------------"


def String test(){
	println "there is sth wrong!"
	"Are you ok?"
}



println test()

println "-------------"

println(test())

println "-------------"


def argFunc(arg1,arg2){
	def int var1 = 10;

	def char var2 = 'b';

	def arr = ['a',16];

	arr[5] = "ui hg 90";

	println arr.size() //这只是一个注释

	println arr[4]

	assert arr[4] == null

    def aMap = ['key1':'value1','key2':true]

    def aRange = 'a'..'z'

    println aRange.getClass().getCanonicalName()+"--:-->"+aRange[10];

	aRange = 1..<11

    println aRange.getClass().getCanonicalName()+"--:-->"+aRange[10];

    def tmpArr = 5.101..5.201

    println tmpArr.getClass().getCanonicalName()+"--:-->@"+tmpArr[10];


    println aMap.getClass().getCanonicalName();


	println arr.getClass().getCanonicalName();

	println var1.getClass().getCanonicalName();
     
    println var2.getClass().getCanonicalName();

    def noParClosure = {
		->
			println "This is the no parameter inner closure"
	}

	noParClosure()


	 arg1 +"  "+ arg2+"----:"+var1+"\r\n" + var2 + ":" + var2.getClass().getCanonicalName();
}

println argFunc("one","two")

//def funcTest(arg1,arg2){
//	arg1 + "  ======  "+ arg2()
//}

//def funcTest2(){
//	"the return string is temp"
//}

//funcTest("the first part",funcTest2)

def aClosure = {//闭包是一段代码，所以需要用花括号括起来..
	String param1, int param2 -> //这个箭头很关键。箭头前面是参数定义，箭头后面是代码 

		println "this is code" //这是代码，最后一句是返回值，
	//也可以使用 return，和 Groovy 中普通函数一样 
	param1 + "<--     -->" + param2
}

println aClosure.call('Hello',12)


println aClosure.call("1000",12)

def noParClosure = {
	->
		println "This is the no parameter closure"
}

noParClosure()

def defaultParClosure = {
	println "the default parameter is $it"	
}

defaultParClosure "\"asdb\""

