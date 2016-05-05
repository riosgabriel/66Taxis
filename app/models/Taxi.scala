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

  def addPassenger(passenger: Passenger) = {
    this.passenger = Some(passenger)
  }

  def move = {
    state match {
      case EnRoute => {
        if(path.isEmpty) passenger match {
            case Some(p) => {
              path = Astar.search(City.state, this.position, p.destination)
              doStep
            }
            case None =>
          }
        else {
          doStep
        }
      }
      case Free => {
        if(path.isEmpty) path = randomPath(City.state, this.position)
        else doStep
      }
      case _ => doStep
    }
  }

  private def doStep = {
    this.position = path.head
    path = path.tail
  }
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
