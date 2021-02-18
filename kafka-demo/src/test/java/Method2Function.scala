object Method2Function {
  def main(args: Array[String]): Unit = {
    //使用方法：
    //创建一个对象
    val dog = new Dog
    println(dog.sum(10,20))

    //方法转函数
    val f1 = dog.sum _

    println("f1="+f1)

    println((1 to 10 ).reverse)

  }

}

class  Dog{
  //方法
  def sum(n1:Int,n2:Int):Int={
    n1+n2
  }



}
