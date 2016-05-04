package parser

/**
  * Created by gabriel on 4/30/16.
  */
object Parser {

  private def isBlocked(s: String) = "x".equalsIgnoreCase(s)

  def parse(fileName: String): Map[(Int, Int), Boolean] = {
    val bufferedSource = io.Source.fromFile(fileName)

    val positions = bufferedSource.getLines.zipWithIndex.flatMap {
      case(line, i) =>
        line.split(",").zipWithIndex.map {
          case(col, j) => ((i, j), isBlocked(col))
        }
    }.toMap

    bufferedSource.close

    positions
  }

}