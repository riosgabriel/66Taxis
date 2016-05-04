package models

import Util.Movable

/**
  * Created by gabriel on 5/3/16.
  */
case class Taxi(var position: (Int, Int), var state: TaxiState = Free, var passenger: Option[Passenger] = None, var path: List[(Int, Int)]) extends Movable {

  def isFree = state match {
    case Free => true
    case _ => false
  }

  def addPassenger(passenger: Passenger) = {
    this.passenger = Some(passenger)
  }
}

sealed trait TaxiState

case object EnRoute extends TaxiState

case object Free extends TaxiState

case object Occupied extends TaxiState
