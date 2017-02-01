class GroovyClosure { 
   def static Display(clo) {
      // This time the $param parameter gets replaced by the string "Inner"         
      clo.call("Inner");
   }

   def static MultipleArgs(String one,int second,last){
      println "The first two parameters is : ${one} and ${second}"
      last.call(one,second);
   }
	
   static void main(String[] args) {
      def str1 = "Hello";
      def clos = { param -> println "${str1} ${param}" }
      clos.call("World");

      // We are now changing the value of the String str1 which is referenced in the closure
      str1 = "Welcome";
      clos.call("World");

      // Passing our closure to a method
      GroovyClosure.Display(clos);

      MultipleArgs("The first parameter!",100){
         String first,int second ->
            println "In closure called, I get " +
                    "two parameters as:${first} and ${second}"
      }
   }
}