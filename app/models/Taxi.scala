package models

import util.Movable
import core.Astar
import play.api.libs.json._
import play.api.libs.functional.syntax._
import converters.Converters._

/**
  * Created by gabriel on 5/3/16.
  */
case class Taxi(var position: Position,
                var state: TaxiState = Free,
                var passenger: Option[Passenger] = None,
                var path: List[Position] = Nil) extends Movable {

  def pickupPassenger(): Unit = {
    val p = passenger.get
    val pathToDestination = Astar.search(City.state, p.location, p.destination)
    state = Occupied
    path = pathToDestination
  }

  def dropOff(): Unit = {
    state = Free
    passenger = None
    path = newRoute
  }

  def enRouteTo(passenger: Passenger, path: List[Position]): Unit = {
    this.state = EnRoute
    this.passenger = Some(passenger)
    this.path = path
  }

  def move(): Unit = {
    this.path match {
      case Nil => this.path = newRoute
      case _ => {
        position = path.head
        path = path.tail
      }

    }
  }

  def aboard: Boolean = {
    passenger match {
      case Some(p) => this.position == p.location
      case None => false
    }
  }

  def arrived: Boolean = {
    passenger match {
      case Some(p) => this.position == p.destination
      case None => false
    }
  }

  private def newRoute = randomPath(City.state, this.position)
}

object Taxi {
  //implicit val taxiFormat = Json.format[Taxi]

  implicit val taxiReads: Reads[Taxi] =
    ((JsPath \ "taxi" \ "x").read[Int] and (JsPath \ "taxi" \ "y").read[Int]).tupled.map(Taxi(_))
}

sealed trait TaxiState {
  def symbol: Char
}

case object EnRoute extends TaxiState {
  def symbol = 'E'
}

case object Free extends TaxiState {
  def symbol = 'F'
}

case object Occupied extends TaxiState {
  def symbol = 'O'
}
