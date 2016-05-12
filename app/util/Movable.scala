package util

import core.Astar
import models.Position

import scala.util.Random
import converters.Converters._

/**
  * Created by gabriel on 5/4/16.
  */
trait Movable {

  def randomPath(city: Map[Position, Boolean], location: Position): List[Position] = {
    Astar.search(city, location, randomPosition(city))
  }

  private def randomPosition(map: Map[Position, Boolean]): Position = {
    Random.shuffle(map.filter(p => p._2 == false)).head._1
  }

}
