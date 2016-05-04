package models

import parser.Parser

/**
  * Created by gabriel on 5/3/16.
  */
object City {

  type Position = (Int, Int)

  private val state: Map[Position, Boolean] = Parser.parse(getClass.getClassLoader.getResource("map-min.csv").getPath)

  private var taxis: List[Taxi] = Nil
  private var passengers: List[Passenger] = Nil

  val maxX = state.keys.maxBy(_._1)._1
  val maxY = state.keys.maxBy(_._2)._2

  def addTaxi(taxi: Taxi) = {
    //Utilizar Either?
    if(!isBlocked(taxi.position)) taxis = taxis :+ taxi
  }

  def addPassenger(passenger: Passenger) = {
    if(!isBlocked(passenger.position)) {
      searchForClosestTaxi(passenger.position) match {
        case (Some(taxi), path) => {
          taxi.addPassenger(passenger)
          taxi.path = path
          taxi.state = EnRoute
        }
        case (None, _) => "Não há taxis disponíveis"
      }
    }
  }

  def moveStep = {
    ???
  }

  private def searchForClosestTaxi(pos: Position): (Option[Taxi], List[Position]) = {
    ???
  }

  def restart = {
    taxis = Nil
    passengers = Nil
  }

  private def isBlocked(position: Position) = state.get(position).getOrElse(false)

  private def existsTaxi(pos: Position) = taxis.exists(_.position == pos)

  def render =
    (0 to maxX) map { row =>
      (0 to maxY) map { col =>
        (row, col) match {
          case p if taxis.exists(_.position == p) => 'T'
          case p if passengers.exists(_.position == p) => 'P'
          case p => state.get(p).map(if (_) 'X' else '_') getOrElse ' '
        }
      }
    } map (_ mkString " ") mkString "\n"


}
