package textphasmophobia

class Game:
  var welcomeMessage =
    s"""\nThis is phasmophobia in text form. The game has you enter a haunted hause and your task is to find out what type of ghost it is.
       |You can always type ${textWithColour("help", commandColour)} the get list of all the commands you can use and their description. Try it out!
       |To start playing you need to start an investigation.
       |You can do that by entering the command: ${textWithColour("start", commandColour)}""".stripMargin
  var helpText =
    s"""Here is a list of ${textWithColour("commands", commandColour)} you can use
       |${textWithColour("help", commandColour)}            - Commnand to print this message
       |${textWithColour("start", commandColour)}           - Start an investigation if there is no ongoing investigation
       |${textWithColour("unlock <target>", commandColour)} - Usually ${textWithColour("unlock", commandColour)} ${textWithColour("house", roomColour)}. This is used to actually start the investigation into the house
       |${textWithColour("go <name>", commandColour)}       - More to room <name> if it exists and is accessible from where you are now
       |${textWithColour("take", commandColour)}            - Will take an item from this room if it is here and put it into your inventory
       |${textWithColour("inventory", commandColour)}       - Show all the items in your inventory at this time
       |${textWithColour("use <item>", commandColour)}      - Uses ${textWithColour("<item>", itemColour)} from players inventory and you will get a result for using that item
       |${textWithColour("finish", commandColour)}          - Finish an investigation if you are in the truck and you have started an investigation
       |${textWithColour("quit", commandColour)}            - Quit the whole game program""".stripMargin

  private var turnCount = 0
  private var unlockHouseTurn = 0
  var isObjectiveDone = false
  var isGameRunning = false
  var isHouseUnlocked = false

  var area = Area(this)
  val player = Player(this)
  private var ghost: Ghost = Ghost(this)

  def getGhost: Ghost = this.ghost

  def isOver = this.player.hasQuit

  def getTurnCount = this.turnCount

  def turnsSinceHouseUnlock = this.turnCount - this.unlockHouseTurn

  def start() = {
    if this.isGameRunning then
      "Game is already running"
    else
      this.isGameRunning = true
      this.ghost = Ghost(this)
      this.area = Area(this)
      s"""You are now in ${textWithColour("truck", roomColour)} outside Tanglewood Drive 6.
         |The house is haunted by a ghost.
         |Your goal is to visit the ghost room and leave.
         |You can move to a different room by typing ${textWithColour("go",commandColour)} ${textWithColour("<room name>", roomColour)}
         |${this.player.getLocationInfo.dropWhile(_ != '.').drop(3)}""".stripMargin
  }

  def unlock(target: String): String = {
    if target == "house" then
      this.unlockHouseTurn = this.turnCount
      this.isHouseUnlocked = true
      s"House is now unlocked. Now the ghost will start to interact with stuff and the ${textWithColour("temperature", temperatureColour)} will start to drop in the ghost room"
    else
      "House is already unlocked"
  }

  def leaveInvestigation(): String = {
    if this.isGameRunning then
      if this.player.location.name == "truck" then
        val report =
          s"""You finished an investigation. Your investigation lasted for ${this.turnCount} turns
             |The ghost was ${this.ghost} and it's favourite room was ${this.ghost.favRoom}
             |You can start a new investigation by entering the ${textWithColour("start", commandColour)} again!""".stripMargin
        this.turnCount = 0
        this.isGameRunning = false
        report
      else
        "You need to be in the truck to be able to leave"
    else
      "You need to have started the investigation first"
  }

  def updateRoomTemps() = {
    if this.isHouseUnlocked then
      this.area.getAllRooms.foreach(_.updateTemperature())
  }

  def playTurn(command: String): String = {
    val outcomeReport = Action(command, this).execute()
    if outcomeReport.isDefined && this.isGameRunning && !(command contains "test") then
      this.updateRoomTemps()
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")
  }
end Game
