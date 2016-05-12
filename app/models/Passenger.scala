package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import converters.Converters._

/**
  * Created by gabriel on 5/3/16.
  */
case class Passenger(location: Position, destination: Position)

object Passenger {
  implicit val passengerReads: Reads[Passenger] = (
    (JsPath \ "passenger" \ "location").read[Position] and
      (JsPath \ "passenger" \ "destination").read[Position]
    )(Passenger.apply _)
//
//  implicit val passengerReads: Reads[Passenger] = (
//    (((JsPath \ "passenger" \ "location" \ "x").read[Int] and (JsPath \ "passenger" \ "location" \ "y").read[Int]).tupled and
//      ((JsPath \ "passenger" \ "destination" \ "x").read[Int] and (JsPath \ "passenger" \ "destination" \ "y").read[Int]).tupled)
//      (Passenger.apply _))
}
