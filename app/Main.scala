import models.{City, Taxi}

/**
  * Created by gabriel on 5/3/16.
  */

object Main {

  def main(args: Array[String]): Unit = {
    val taxi1 = Taxi((0, 3))
    val taxi2 = Taxi((5, 4))
    val taxi3 = Taxi((4, 9))

    val city = City
    city.addTaxi(taxi1)
    city.addTaxi(taxi2)
    city.addTaxi(taxi3)

    println("Start City\n")
    println(city.render)
    println

    for(i <- 1 to 6) {
      if(i == 5) city.restart
      println(s"City on time ${i}\n")
      city.moveStep
      println(city.render)
      Thread.sleep(4000)
    }
  }
}
