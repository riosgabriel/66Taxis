package models

import play.api.libs.json.Json

/**
  * Created by gabriel on 5/8/16.
  */
case class Position(x: Int, y: Int)

object Position {

  implicit val positionFormat = Json.format[Position]
}
