package models

import org.specs2.mutable.Specification
import converters.Converters._

/**
  * Created by gabriel on 5/7/16.
  */
class CitySpec extends Specification {

  "A city" should {

    "not add taxis in a blocked position" in {
      val position = City.state.filter(_._2).head._1

      City.addTaxi(Taxi(position)) must beLeft
    }

    "restart application" in {
      City.restart()

      City.taxis must beEmpty
      City.passengers must beEmpty
    }

    "not add passenger without taxi in the city" in {
      val passenger = Passenger((2, 2), (5, 7))
      City.restart()
      City.addPassenger(passenger) must beLeft
    }
  }
}
