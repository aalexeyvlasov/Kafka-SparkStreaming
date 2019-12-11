package applications.unit

import org.scalatest.{FlatSpec, Matchers}
import applications.HelloWorld_for_unit_test

class ExampleUnitTest extends FlatSpec with Matchers{

  behavior of "HelloWorld_for_unit_test"

  it should "compute text message" in{
    HelloWorld_for_unit_test.computeMessage() shouldEqual "Hello, World!"
  }

  it should "compute result number" in{
    HelloWorld_for_unit_test.computeNumber() shouldEqual 1000000
  }
}
