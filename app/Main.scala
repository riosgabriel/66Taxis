import models.{City, Taxi}

/**
  * Created by gabriel on 5/3/16.
  */

object Main {

  def main(args: Array[String]): Unit = {
    val taxi = Taxi((0, 3))

    val city = City
    city.addTaxi(taxi)

    println(city.render)
  }
}
