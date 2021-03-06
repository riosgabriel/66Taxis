package models

import org.specs2.mutable._

/**
  * Created by gabriel on 5/7/16.
  */
class TaxiSpec extends Specification {

  "A Taxi" should {
    val city = City
    val randomPassenger = Passenger((0, 1), (0, 7))

    "be occupied when pickup passenger" in {
      val taxi = Taxi((0, 1))

      taxi.pickup(randomPassenger)

      taxi.state must be(Occupied)
    }

    "be free when dropoff passenger" in {
      val taxi = Taxi((0, 1))
      taxi.pickup(randomPassenger)
      taxi.dropOff()

      taxi.state must be(Free)
      taxi.passenger must be(None)
    }

    "move to new position" in {
      val taxi = Taxi((0, 1))
      val randomPath = List((0, 1), (0, 2), (0, 3))
      taxi.path = randomPath

      taxi.move()
      taxi.position must be_== (0, 1)
      taxi.move()
      taxi.position must not be_== (0, 1)
      taxi.position must be_== (0, 2)
    }

  }
}
