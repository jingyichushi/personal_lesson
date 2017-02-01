trait GroovyTrait extends GroovyTraitParent
	implements GroovyInterface,GroovyAnotherInterface
{
	private String name = "I'm a trait!";

	void displayName(){
		
		//trait没有默认实现的setX()和getX()
		//以下语句导致编译错误
		//setName("Got a new name");
		
		println name
	}


	//Groovy并没有为trait默认实现存取方法setX()和getX()
	String getName(){
		return name;
	}

	void displayInterfaceInfo(){
		println '''I'm a function implemented in trait for an interface named GroovyInterface!'''
	}

	//void displayAnotherInterfaceInfo(){
	//	println "I'm another function implemented in trait for an interface named GroovyInterface!"	
	//}


}