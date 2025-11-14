package textphasmophobia

import scala.util.Random

sealed trait Item(game: Game):
  val name: String
  def use: String

  override def toString: String = textWithColour(this.name, itemColour)
end Item

class Thermometer(val game: Game) extends Item(game):
  val name = "thermometer"

  def use = {
    if this.game.player.location == this.game.getGhost.favRoom then
      s"""Temperature in this room is ${1.0 + roundToDecimals(9 * Random.nextFloat(), 1)} celsius
         |This seems to be the ghost room""".stripMargin

    else
      s"Temperature in this room is ${17.0 + roundToDecimals(6 * Random.nextFloat(), 1) } celsius"
  }
end Thermometer
