package textphasmophobia

import scala.collection.mutable.Map

class Player(val game: Game):
  var hasQuit = false
  private var mapLocation = "" // Empty string means the player is in truck
  private var accessibleRooms = this.game.area.getAccessibleRooms(this.mapLocation)
  private var inventory: Vector[Item] = Vector.empty
  private var currentLocation: location = this.game.area.getRoom(this.mapLocation).value

  def location = currentLocation

  def getInventory = this.inventory

  private def updateRoomData(): Unit =
    this.accessibleRooms = this.game.area.getAccessibleRooms(this.mapLocation)
    this.currentLocation = this.game.area.getRoom(this.mapLocation).value

  private def accessibleRoomNames = this.accessibleRooms.filter(_.name.nonEmpty).map(_.toString)

  def getLocationInfo =
    s"""You are now in ${this.location.toString}.
       |Rooms accessible from here are: ${this.accessibleRoomNames.mkString(", ")}
       |This room has ${if this.location.items.nonEmpty then this.location.items.map(_.toString).mkString(", ") else "no items"}""".stripMargin

  def go(newRoom: String) = {
    val roomNames = this.accessibleRooms.map(_.name)
    if game.isGameRunning then
      if this.game.isHouseUnlocked then
        if (roomNames contains newRoom) && newRoom.nonEmpty then
          val roomIndex = roomNames.indexOf(newRoom)
          if roomIndex == 0 && this.location.name != "truck" then
            this.mapLocation = this.mapLocation.dropRight(1)
            this.updateRoomData()
          else
            this.mapLocation = if roomNames.length > 1 then this.mapLocation + (roomIndex - 1).toString else this.mapLocation + roomIndex.toString
            this.updateRoomData()
          this.getLocationInfo
        else
          s"There is no such room as ${textWithColour(newRoom, roomColour)} or you can't access it from here."
      else
        s"You need to unlock the house first. You can do that by entering ${textWithColour("unlock", commandColour)} ${textWithColour("house", roomColour)}"
    else
      "You need to start the game first"
  }

  def take(itemName: String): String = {
    if this.location.hasItem(itemName) then
      val item = this.location.takeItem(itemName)
      this.inventory = this.inventory :+ item
      s"""You picked up $item
         |${this.getLocationInfo}""".stripMargin
    else
      s"""Item ${textWithColour(itemName, itemColour)} is not here. Did you type the item correctly?
         |${this.getLocationInfo}""".stripMargin
  }

  def use(itemName: String): String = {
    if this.location.name == "truck" then
      "You can't use items while in the truck"
    else
      this.inventory.find(_.name == itemName) match {
        case Some(item) => {
          item.use + "\n" + this.getLocationInfo
        }
        case None => s"You"
      }
  }

  def getInventoryText = {
    val text = if this.getInventory.nonEmpty then s"Your inventory contains (${this.inventory.length}/2): ${this.getInventory.mkString(", ")}\n" else "Your inventory is empty (0/2)\n"
    text + this.getLocationInfo
  }

  def quit = {
    this.hasQuit = true
    "Player has quit the game"
  }

  def test = {
    s"""MapLocation: ${this.mapLocation}
       |Current Room: ${this.location}
       |Accessible Rooms: ${this.accessibleRoomNames}""".stripMargin
  }

end Player


