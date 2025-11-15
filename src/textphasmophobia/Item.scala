package textphasmophobia


sealed trait Item(game: Game):
  val name: String
  def use: String

  override def toString: String = textWithColour(this.name, itemColour)
end Item

class Thermometer(game: Game) extends Item(game):
  val name = "thermometer"

  def use: String = {
    s"Temperature in this room is ${this.game.player.location.temperature.toString} celsius"
  }
end Thermometer
