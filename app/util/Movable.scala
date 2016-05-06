package util

import core.Astar

import scala.util.Random

/**
  * Created by gabriel on 5/4/16.
  */
trait Movable {

  type Position = (Int, Int)

  def randomPath(map: Map[Position, Boolean], location: Position): List[Position] = {
    Astar.search(map, location, randomPosition(map))
  }

  private def randomPosition(map: Map[Position, Boolean]): Position = {
    Random.shuffle(map.filter(p => p._2 == false)).head._1
  }

}
