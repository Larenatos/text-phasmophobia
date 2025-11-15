package textPhasmophobia

import scala.collection.mutable.Map

class Player(val game: Game):
  var hasQuit = false
  private var mapLocation = "" // Empty string means the player is in truck
  private var accessibleRooms = this.game.area.getAccessibleRooms(this.mapLocation)
  private var inventory: Vector[Item] = Vector.empty
  private var currentLocation: Location = this.game.area.getRoom(this.mapLocation).value
  private var evidences: Vector[String] = Vector.empty
  private var equippedItem: Option[Item] = None

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

  def addEvidence(evidence: String) = {
    if !(this.evidences contains evidence) then
      this.evidences = this.evidences :+ evidence
  }

  def equipItem(itemName: String): String = {
    if this.inventory.exists(_.name == itemName) && (Vector("thermometer", "video camera", "spirit box") contains itemName) then
      val item = this.inventory.filter(_.name == itemName)(0)
      this.equippedItem = Some(item)
      s"""You have now equipped ${textWithColour(itemName, itemColour)}. When you enter a room you will get info from this item.
         |${this.getLocationInfo}""".stripMargin
    else
      s"""${textWithColour(itemName, itemColour)} is not equippaple or you don't have that item in your inventory
         |${this.getLocationInfo}""".stripMargin
  }

  def getEvidenceInfo: String = {
    if this.evidences.isEmpty then
      s"""You not found any evidence yet. The ghost could be anything
         |${this.getLocationInfo}""".stripMargin
    else if this.evidences.length == 3 then
      val ghostType = ghostTypes.filter((name, evidence) => this.evidences.forall(evidence contains _)).keys.toVector(0)
      this.game.isObjectiveCompleted = true
      s"""Evidence you have found: ${this.evidences.map(textWithColour(_, evidenceColour)).mkString(", ")} and the ghost is ${textWithColour(ghostType, ghostColour)}
         |You can now finish investigation from the truck with ${textWithColour("finish", commandColour)}
         |${this.getLocationInfo}""".stripMargin
    else
      s"""Evidence you have found: ${this.evidences.map(textWithColour(_, evidenceColour)).mkString(", ")}. The ghost could be ${ghostTypes.filter((name, evidence) => this.evidences.forall(evidence contains _)).keys.map(textWithColour(_, ghostColour)).mkString(", ")}
         |${this.getLocationInfo}""".stripMargin
  }

  def go(newRoom: String) = {
    val roomNames = this.accessibleRooms.map(_.name)

    if this.game.isHouseUnlocked then
      if (roomNames contains newRoom) && newRoom.nonEmpty then
        val roomIndex = roomNames.indexOf(newRoom)
        if roomIndex == 0 && this.location.name != "truck" then
          this.mapLocation = this.mapLocation.dropRight(1)
          this.updateRoomData()
        else
          this.mapLocation = if roomNames.length > 1 then this.mapLocation + (roomIndex - 1).toString else this.mapLocation + roomIndex.toString
          this.updateRoomData()

        this.equippedItem.fold(this.getLocationInfo)(item => s"Your equipped item tells you: ${item.use}\n" + this.getLocationInfo)
      else
        s"""There is no such room as ${textWithColour(newRoom, roomColour)} or you can't access it from here.
           |${this.getLocationInfo}""".stripMargin
    else
      s"""You need to unlock the house first. You can do that by entering ${textWithColour("unlock", commandColour)} ${textWithColour("house", roomColour)}
         |${this.getLocationInfo}""".stripMargin
  }

  def isInGhostRoom: Boolean =
    this.location.name == this.game.getGhost.favRoom.name

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

  def drop(itemName: String): String = {
    if this.getInventory.exists(_.name == itemName) then
      val item = this.getInventory.filter(_.name == itemName)(0)
      this.inventory = this.inventory.filter(_.name != itemName)
      this.location.addItem(item)
      s"""You place ${textWithColour(itemName, itemColour)} in ${textWithColour(this.location.name, roomColour)}
         |${this.getLocationInfo}""".stripMargin
    else
      s"""You don't have ${textWithColour(itemName, itemColour)} in your inventory
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
        case None => s"You don't have ${itemName} in your inventory" + "\n" + this.getLocationInfo
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


