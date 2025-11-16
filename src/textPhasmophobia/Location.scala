package textPhasmophobia

import scala.util.Random

sealed trait Location(game: Game):
  val name: String

  def reset(): Unit

  def getItems: Vector[Item]

  def removeItem(item: Item): Unit

  def addItem(item: Item): Unit

  def updateTemperature(): Unit

  def getTemperature: Double

  def takeItem(itemName: String): Item = {
    this.getItems.find(_.name == itemName) match {
      case Some(item) => {
        this.removeItem(item)
        item
      }
    }
  }

  def hasItem(itemName: String): Boolean = {
    this.getItems.map(_.name) contains itemName
  }



  override def toString: String = textWithColour(this.name, roomColour)
end Location

class Truck(game: Game) extends Location(game):
  val name = "truck"
  private var items: Vector[Item] = Vector.empty
  private var temperature = 15

  def reset() = {
    this.items = Vector(game.thermometer, game.spiritBox, game.videoCamera, game.writingBook)
  }

  def getItems = this.items

  def removeItem(item: Item) = {
    this.items = this.items.filter(_ != item)
  }

  def addItem(item: Item) = {
    this.items = this.items :+ item
  }

  def updateTemperature(): Unit =
    this.temperature = 15

  def getTemperature = this.temperature
end Truck

class Room(val name: String, game: Game) extends Location(game):
  private var items: Vector[Item] = Vector.empty
  private var temperature: Double = roundToDecimals(Random.nextFloat * 3 + 19, 1)

  def reset() = {
    this.items = Vector.empty
  }

  def getItems = this.items

  def removeItem(item: Item) = {
    this.items = this.items.filter(_ != item)
  }

  def addItem(item: Item) = {
    this.items = this.items :+ item
  }

  def getTemperature = this.temperature

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