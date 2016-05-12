package converters

import models.Position

/**
  * Created by gabriel on 5/8/16.
  */
object Converters {

  implicit def tuple2Position(tuple: (Int, Int)) = Position(tuple._1, tuple._2)
//
  implicit def position2Tuple(position: Position) = (position.x, position.y)
//
//  implicit def listTuple2ListPosition(list: List[(Int, Int)]) = list.map(t => Position(t._1, t._2))
//
//  implicit def mapPosition2MapTuple(map: Map[Position, Boolean]) = map.map(t => Map(t._1, t._2))

}
