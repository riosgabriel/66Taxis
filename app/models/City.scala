package models

import core.Astar
import parser.Parser
import converters.Converters._

/**
  * Created by gabriel on 5/3/16.
  */

object City {

  private var _taxis: List[Taxi] = Nil
  private var _passengers: List[Passenger] = Nil

  def taxis = _taxis
  def passengers = _passengers

  val state = Parser.parse(getClass.getClassLoader.getResource("map-min.csv").getPath)

  val maxX = state.keys.maxBy(_.x).x
  val maxY = state.keys.maxBy(_.y).y

  def addTaxi(taxi: Taxi): Either[String, Taxi] = {
    if(isBlocked(taxi.position)) Left("Blocked position for taxi")
    else {
      _taxis = _taxis :+ taxi
      Right(taxi)
    }
  }

  private def freeTaxis = _taxis.filter(_.state == Free)

  def addPassenger(passenger: Passenger): Either[String, Passenger] = {
    if (!isBlocked(passenger.location)) {
      if (freeTaxis.isEmpty) Left("There is no taxi in the city")
      else {
        searchForClosestTaxi(passenger.location) match {
          case Some(result) => {
            val (taxi, path) = result
            taxi enRouteTo(passenger, path)
            this._passengers = _passengers :+ passenger
            Right(passenger)

          }
          case _ =>
            this._passengers = _passengers :+ passenger
            Right(passenger)
        }
      }
    }
    else Left("Blocked position for passenger")
  }

  def stepForward() = {
    _taxis.foreach { taxi =>
      taxi.state match {

        case EnRoute =>
          if(taxi.aboard) {
            taxi.pickupPassenger()
            _passengers = _passengers.filter(_ != taxi.passenger.get)
          }
          else taxi.move()

        case Occupied => {
          if(taxi.arrived) taxi.dropOff()
          else taxi.move()
        }

        case _ => taxi.move()
      }
    }

  }

  def restart() = {
    _taxis = Nil
    _passengers = Nil
  }

  private def isBlocked(position: Position) = state.getOrElse(position, false)

  private def searchForClosestTaxi(startPosition: Position): Option[(Taxi, List[Position])] = {
    val freeTaxis = _taxis.filter(_.state == Free).map { t =>
      (t, Astar.search(City.state, t.position, startPosition))
    }

    if(freeTaxis.isEmpty) None
    else {
      freeTaxis.minBy(_._2.size) match {
        case (taxi, positions) => Some(taxi, positions)
      }
    }
  }

  def renderHtml = render.split("\n")

  def render =
    (0 to maxX) map { row =>
      (0 to maxY) map { col =>
        (row, col) match {
          case p if _taxis.exists(_.position == Position(p._1, p._2)) => {
            _taxis.find(_.position == Position(p._1, p._2)) match {
              case Some(t) => t.state.symbol
              case _ =>  ' '
            }
          }
          case p if _passengers.exists(_.location == Position(p._1, p._2)) => 'P'
          case p => state.get(Position(p._1, p._2)).map(if (_) 'x' else '_') getOrElse ' '
        }
      }
    } map (_ mkString " ") mkString "\n"
}
