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

  def getTaxis = taxis

  def getPassengers = passengers

  def addTaxi(taxi: Taxi): Either[String, String] = {
    if(!isBlocked(taxi.position)) {
      taxis = taxis :+ taxi
      Right("Passenger added")
    }
    else Left("Blocked position for taxi")
  }

  def addPassenger(passenger: Passenger): Either[String, String] = {
    if(!isBlocked(passenger.location)) {
      searchForClosestTaxi(passenger.location) match {
        case Some(result) => {
          val (taxi, path) = result
          taxi.enroute(path)
          this.passengers = passengers :+ passenger
          Right("Taxi enroute")

        }
        case _ =>
          this.passengers = passengers :+ passenger
          Right("Passenger added")
      }
    }
    else Left("Blocked position for passenger")
  }

  def moveStep() = {
    this.taxis.foreach { taxi =>
      taxi.state match {
        case EnRoute if taxi.path.isEmpty =>  {
          passengers.find(_.location == taxi.position).foreach { passenger =>
            taxi.pickup(passenger)
            passengers = passengers.filter(_ != passenger)
          }
        }
        case Occupied if taxi.path.isEmpty => {
          taxi.dropOff()
        }

        case _ => taxi.move()
      }

    }
  }

  def restart() = {
    taxis = Nil
    passengers = Nil
  }

  private def isBlocked(position: Position) = state.getOrElse(position, false)

  private def searchForClosestTaxi(startPosition: Position): Option[(Taxi, List[Position])] = {
    val freeTaxis = taxis.filter(_.state == Free).map { t =>
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
          case p if taxis.exists(_.position == p) => {
            taxis.find(_.position == p) match {
              case Some(t) => t.state.symbol
              case _ =>  ' '
            }
          }
          case p if passengers.exists(_.location == p) => 'P'
          case p => state.get(p).map(if (_) 'X' else '_') getOrElse ' '
        }
      }
    } map (_ mkString " ") mkString "\n"
}
