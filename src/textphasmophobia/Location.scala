package textphasmophobia

sealed trait location:
  val name: String
  var items: Vector[Item]

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
end location

class Truck(game: Game) extends location:
  val name = "truck"
  var items: Vector[Item] = Vector(Thermometer(game))
end Truck

class Room(val name: String) extends location:
  var items: Vector[Item] = Vector.empty
end Room