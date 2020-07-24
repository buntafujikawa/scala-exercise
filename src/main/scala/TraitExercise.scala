object TraitExercise {

  trait TraitA {
    val name: String

    def printName(): Unit = println(name)
  }

  trait TraitB

  class ClassA

  class ClassC(val name: String) extends ClassA with TraitA with TraitB

  trait A {
    val foo: String
  }

  trait B extends A {
    //    val bar = foo + "World" // nullWorld
    lazy val bar = foo + "World" // HelloWorld
  }

  class C extends B {
    val foo = "Hello"

    def printBar(): Unit = println(bar)
  }

}
