package models

/**
  * Created by gabriel on 5/3/16.
  */
case class Taxi(position: (Int, Int), state: TaxiState = Free, passenger: Option[Passenger] = None) {

  def isFree = state match {
    case Free => true
    case _ => false
  }
}

sealed trait TaxiState
case object EnRoute extends TaxiState
case object Free extends TaxiState
case object Occupied extends TaxiState
