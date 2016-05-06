package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import converters.JsonConverters._

/**
  * Created by gabriel on 5/3/16.
  */
case class Passenger(location: (Int, Int), destination: (Int, Int))

object Passenger {

  implicit val passengerFormat = Json.format[Passenger]

//  implicit val passengerReads = (
//    ((__ \ "location" \ "x").read[Int] and
//      (__ \ "location" \ "y").read[Int]).tupled and
//    ((__ \ "destination" \ "x").read[Int] and
//      (__ \ "destination" \ "y").read[Int]).tupled)
//  (Passenger.apply _)
//
//  implicit val passengerWrites = new Writes[Passenger] {
//    def writes(p: Passenger): JsValue = {
//      Json.obj("passenger" ->
//        Json.obj(
//          "location" -> Json.obj(
//            "x" -> p.position._1,
//            "y" -> p.position._2),
//          "destination" -> Json.obj(
//            "x" -> p.destination._1,
//            "y" -> p.destination._2)
//          )
//        )
//    }
//  }
//
//  implicit val passengerFormat = Format(passengerReads, passengerWrites)
}
