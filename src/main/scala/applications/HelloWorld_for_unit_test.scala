package applications

object HelloWorld_for_unit_test {

  def computeMessage(): String = {

    "Hello, World!"
  }
  def computeNumber(): Int = {
    1000000
  }


  def main(args: Array[String]):Unit = {

    val message_a = computeMessage()
    val number_a = computeNumber()

    println(message_a, number_a)
  }

}
