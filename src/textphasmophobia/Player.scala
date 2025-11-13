package textphasmophobia

import scala.collection.mutable.Map

class Player(val game: Game):
  var hasQuit = false
  private var mapLocation = "" // Empty string means the player is in truck
  private var accessibleRooms = this.game.map.getAccessibleRoomNames(this.mapLocation)

  def location: String= this.game.map.getRoom(this.mapLocation).name

  private def updateAccessibleRooms(): Unit =
    this.accessibleRooms = this.game.map.getAccessibleRoomNames(this.mapLocation)

  def getLocationInfo =
    s"""You are now in ${Console.BLUE}${this.location}${Console.RESET}.
Rooms accessible from here are: ${this.accessibleRooms.filter(_.nonEmpty).map(Console.BLUE + _ + Console.RESET).mkString(", ")}""".stripMargin

  def go(newRoom: String) = {
    val lowerCaseAccessibleRooms = this.accessibleRooms.map(_.toLowerCase)
    if game.isGameRunning then
      if (lowerCaseAccessibleRooms contains newRoom) && newRoom.nonEmpty then
        val roomIndex = lowerCaseAccessibleRooms.indexOf(newRoom)
        if roomIndex == 0 then
          this.mapLocation = this.mapLocation.dropRight(1)
          this.updateAccessibleRooms()
        else
          this.mapLocation += (roomIndex - 1).toString
          this.updateAccessibleRooms()
        this.getLocationInfo
      else
        s"There is no such room as ${Console.BLUE}$newRoom${Console.RESET} or you can't access it from here."
    else
      "You need to start the game first"
  }

  def quit = {
    this.hasQuit = true
    "Player has quit the game"
  }

  def test =
    s"MapLocation: ${this.mapLocation}\nCurrent Room: ${this.location}\nAccessible Rooms: ${this.accessibleRooms.map(_.toLowerCase)}"

end Player


