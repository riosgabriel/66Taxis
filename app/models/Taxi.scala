package models

import util.Movable
import core.Astar
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by gabriel on 5/3/16.
  */
case class Taxi(var position: (Int, Int)) extends Movable {

  var state: TaxiState = Free
  var path: List[(Int, Int)] = Nil
  var passenger: Option[Passenger] = None

  def isFree = state match {
    case Free => true
    case _ => false
  }

  def enroute(path: List[(Int, Int)]) = {
    state = EnRoute
    this.path = path
  }

  def pickup(passenger: Passenger) = {
    this.passenger = Some(passenger)
    this.state = Occupied
    path = Astar.search(City.state, passenger.location, passenger.destination)
  }

  def dropOff() = {
    this.passenger = None
    this.state = Free

    getNewRoute()
  }

  def move() = {
    path match {
      case Nil => getNewRoute()
      case _ => doStep()
    }
  }

  private def doStep() = {
    this.position = path.head
    this.path = path.tail
  }

  private def getNewRoute() = path = randomPath(City.state, this.position)
}

object Taxi {

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
