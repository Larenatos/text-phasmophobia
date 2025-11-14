package textphasmophobia

import scala.collection.mutable.Map

class Player(val game: Game):
  var hasQuit = false
  private var mapLocation = "" // Empty string means the player is in truck
  private var accessibleRooms = this.game.area.getAccessibleRooms(this.mapLocation)

  def location: location = this.game.area.getRoom(this.mapLocation).value

  private def updateAccessibleRooms(): Unit =
    this.accessibleRooms = this.game.area.getAccessibleRooms(this.mapLocation)

  private def accessibleRoomNames = this.accessibleRooms.map(_.toString)

  def getLocationInfo =
    s"""You are now in ${Console.BLUE}${this.location}${Console.RESET}.
Rooms accessible from here are: ${this.accessibleRoomNames.filter(_.nonEmpty).mkString(", ")}"""

  def go(newRoom: String) = {
    val roomNames = this.accessibleRooms.map(_.name)
    if game.isGameRunning then
      if (roomNames contains newRoom) && newRoom.nonEmpty then
        val roomIndex = roomNames.indexOf(newRoom)
        if roomIndex == 0 then
          this.mapLocation = this.mapLocation.dropRight(1)
          this.updateAccessibleRooms()
        else
          this.mapLocation = if roomNames.length > 1 then this.mapLocation + (roomIndex - 1).toString else this.mapLocation + roomIndex.toString
          this.updateAccessibleRooms()
        this.getLocationInfo
      else
        s"There is no such room as ${Console.YELLOW}$newRoom${Console.RESET} or you can't access it from here."
    else
      "You need to start the game first"
  }

  def quit = {
    this.hasQuit = true
    "Player has quit the game"
  }

  def test =
    s"MapLocation: ${this.mapLocation}\nCurrent Room: ${this.location}\nAccessible Rooms: ${this.accessibleRoomNames}"

end Player


