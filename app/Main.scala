import models.{City, Passenger, Taxi}

/**
  * Created by gabriel on 5/3/16.
  */

object Main {

  def main(args: Array[String]): Unit = {
    val taxi1 = Taxi((0, 3))
    val taxi2 = Taxi((5, 4))
    val taxi3 = Taxi((4, 9))

    val passenger1 = Passenger((8,8), (5, 5))

    val city = City
    city.addTaxi(taxi1)
    city.addTaxi(taxi2)
    city.addTaxi(taxi3)

    city.addPassenger(passenger1)

    println("Start City\n")
    println(city.render)
    println

    for(i <- 1 to 8) {
//      if(i == 5) city.restart
      println(s"City on time ${i}\n")
      city.moveStep
      println(city.render)
      Thread.sleep(2000)
    }
  }
}
