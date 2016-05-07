package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by gabriel on 5/3/16.
  */
case class Passenger(location: (Int, Int), destination: (Int, Int))

object Passenger {

  implicit val passengerReads: Reads[Passenger] = (
    (((JsPath \ "passenger" \ "location" \ "x").read[Int] and (JsPath \ "passenger" \ "location" \ "y").read[Int]).tupled and
      ((JsPath \ "passenger" \ "destination" \ "x").read[Int] and (JsPath \ "passenger" \ "destination" \ "y").read[Int]).tupled)
      (Passenger.apply _))
}
