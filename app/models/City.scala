package models

import core.Astar
import parser.Parser

/**
  * Created by gabriel on 5/3/16.
  */
object City {

  type Position = (Int, Int)

  private var taxis: List[Taxi] = Nil
  private var passengers: List[Passenger] = Nil

  val state: Map[Position, Boolean] = Parser.parse(getClass.getClassLoader.getResource("map-min.csv").getPath)

  val maxX = state.keys.maxBy(_._1)._1
  val maxY = state.keys.maxBy(_._2)._2

  def addTaxi(taxi: Taxi) = {
    //Utilizar Either?
    if(!isBlocked(taxi.position)) taxis = taxis :+ taxi
  }

  def addPassenger(passenger: Passenger) = {
    //Utilizar Either?
    if(!isBlocked(passenger.position)) {
      searchForClosestTaxi(passenger.position) match {
        case (Some(taxi), path) => {
          taxi.path = path
          taxi.state = EnRoute
          passengers = passengers :+ passenger
        }
        case _ => "Não há taxis disponíveis"
      }
    }
  }

  def moveStep = {
    taxis.foreach(_.move)
  }

  private def searchForClosestTaxi(startPosition: Position): (Option[Taxi], List[Position]) = {
    taxis.filter(_.state == Free).map { t =>
      (t, Astar.search(City.state, t.position, startPosition))
    }.minBy(_._2.size) match {
      case (t, p) => (Some(t), p)
      case _ => (None, Nil)
    }
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
          case p if taxis.exists(_.position == p) => {
            taxis.find(_.position == p) match {
              case Some(t) => t.state.symbol
            }
          }
          case p if passengers.exists(_.position == p) => 'P'
          case p => state.get(p).map(if (_) 'X' else '_') getOrElse ' '
        }
      }
    } map (_ mkString " ") mkString "\n"
}
