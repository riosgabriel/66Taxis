package models

import Util.Movable
import core.Astar

/**
  * Created by gabriel on 5/3/16.
  */
case class Taxi(var position: (Int, Int),
                var state: TaxiState = Free,
                var passenger: Option[Passenger] = None,
                var path: List[(Int, Int)] = Nil) extends Movable {

  def isFree = state match {
    case Free => true
    case _ => false
  }

  def pickup(passenger: Passenger) = {
    this.passenger = Some(passenger)
    this.state = Occupied
    path = Astar.search(City.state, passenger.position, passenger.destination)
  }

  def dropOff = {
    this.passenger = None
    this.state = Free

    getNewRoute
  }

  def move = {
    path match {
      case Nil => getNewRoute
      case _ => doStep
    }
  }

  private def doStep = {
    this.position = path.head
    this.path = path.tail
  }

  private def getNewRoute = path = randomPath(City.state, this.position)
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
