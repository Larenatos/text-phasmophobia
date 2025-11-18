package textPhasmophobia

import scala.util.Random
import scala.collection.mutable.Buffer

sealed trait Location(private val game: Game):
  val name: String
  val items: Buffer[Item] = Buffer.empty
  protected var temperature: Double

  def reset(): Unit

  def updateTemperature(): Unit

  def getTemperature: Double = this.temperature

  def takeItem(itemName: String): Item = {
    this.items.find(_.name == itemName) match {
      case Some(item) => {
        this.removeItem(item)
        item
      }
    }
  }

  def removeItem(item: Item) = {
    this.items.remove(this.items.indexOf(item))
  }

  def addItem(item: Item) = {
    this.items.append(item)
  }

  def hasItem(itemName: String): Boolean = {
    this.items.map(_.name) contains itemName
  }

  override def toString: String = textWithColour(this.name, roomColour)
end Location


class Truck(game: Game) extends Location(game):
  val name = "truck"
  var temperature: Double = 15

  def reset() = {
    this.items.clear()
    Vector(game.thermometer, game.spiritBox, game.videoCamera, game.writingBook).foreach(this.items.append(_))
  }

  def updateTemperature(): Unit =
    this.temperature = 15

end Truck

class Room(val name: String, game: Game) extends Location(game):
  var temperature: Double = roundToDecimals(Random.nextFloat * 3 + 19, 1)

  def reset() = {
    this.items.clear()
  }

  private def changeTemp(addition: Float) = {
    this.temperature = roundToDecimals(this.temperature.toFloat + addition, 1)
  }

  def updateTemperature() = {
    val isNegative = Random.nextBoolean()

    if this.game.ghost.getFavRoom.name == this.name then
      // ghost room
      if this.game.ghost.evidence contains "freezing" then
        // ghost has freezing as evidence
        if this.temperature > 2 || (isNegative && this.temperature > -15) then
          this.changeTemp(-2.toFloat * Random.nextFloat() - 2)
        else
          this.changeTemp(2.toFloat * Random.nextFloat() + 1)
      else
        // no freezing evidence
        if this.temperature > 8 || (isNegative && this.temperature > 2) then
          this.changeTemp(-2.toFloat * Random.nextFloat() - 1)
        else
          this.changeTemp(2.toFloat * Random.nextFloat() + 1)
        if this.temperature < 1 then
          this.temperature = 1
    else
      // not ghost room
      if this.temperature > 22.0 || (isNegative && this.temperature > 18) then
        this.changeTemp(-1.5.toFloat * Random.nextFloat())
      else
        this.changeTemp(1.5.toFloat * Random.nextFloat())
  }
end Room