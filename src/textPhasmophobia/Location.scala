package textPhasmophobia

import scala.util.Random

sealed trait Location(game: Game):
  val name: String
  var items: Vector[Item]
  var temperature: Double

  def updateTemperature(): Unit

  def takeItem(itemName: String): Item = {
    this.items.find(_.name == itemName) match {
      case Some(item) => {
        this.items = this.items.filter(_.name != itemName)
        item
      }
    }
  }

  def hasItem(itemName: String): Boolean = {
    this.items.map(_.name) contains itemName
  }

  def addItem(item: Item): Unit = {
    this.items = this.items :+ item
  }

  override def toString: String = textWithColour(this.name, roomColour)
end Location

class Truck(game: Game) extends Location(game):
  val name = "truck"
  var items: Vector[Item] = Vector(Thermometer(game), VideoCamera(game), SpiritBox(game))
  var temperature = 15

  def updateTemperature() = {
    this.temperature = 15
  }
end Truck

class Room(val name: String, game: Game) extends Location(game):
  var items: Vector[Item] = Vector.empty
  var temperature: Double = roundToDecimals(Random.nextFloat * 3 + 19, 1)

  private def changeTemp(addition: Float) = {
    this.temperature = roundToDecimals(this.temperature.toFloat + addition, 1)
  }

  def updateTemperature() = {
    val isNegative = Random.nextBoolean()


    if this.game.getGhost.favRoom.name == this.name then
      // ghost room
      if this.game.getGhost.evidence contains "freezing" then
        // ghost has freezing as evidence
        if this.temperature > 3 || (isNegative && this.temperature > -15) then
          this.changeTemp(-2.toFloat * Random.nextFloat() - 1)
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