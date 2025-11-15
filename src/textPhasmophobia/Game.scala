package textPhasmophobia

class Game:
  val welcomeMessage =
    s"""\nThis is phasmophobia in text form. The game has you enter a haunted hause and your task is to find out what type of ghost it is.
       |You can always type ${textWithColour("help", evidenceColour)} the get list of all the commands you can use and their description. Try it out!
       |Enter command ${textWithColour("tutorial", commandColour)} to get a brief introduction to how the game works and what you need to do in order to succeed in an investigation
       |To start playing you need to start an investigation.
       |You can do that by entering the command: ${textWithColour("start", commandColour)}""".stripMargin
  val helpText =
    s"""Here is a list of ${textWithColour("commands", commandColour)} you can use
       |${textWithColour("help", commandColour)}            - Commnand to print this message
       |${textWithColour("tutorial", commandColour)}        - Commnand to print out a long text with introduction on how to play this game
       |${textWithColour("learn <thing>", commandColour)}   - Show information regarding <thing>. Info only exists for: ghost, evidence
       |${textWithColour("start", commandColour)}           - Start an investigation if there is no ongoing investigation
       |${textWithColour("unlock <target>", commandColour)} - Usually ${textWithColour("unlock", commandColour)} ${textWithColour("house", roomColour)}. This is used to actually start the investigation into the house
       |${textWithColour("go <name>", commandColour)}       - More to room <name> if it exists and is accessible from where you are now
       |${textWithColour("take", commandColour)}            - Will take an item from this room if it is here and put it into your inventory
       |${textWithColour("inventory", commandColour)}       - Show all the items in your inventory at this time
       |${textWithColour("use <item>", commandColour)}      - Uses ${textWithColour("<item>", itemColour)} from players inventory and you will get a result for using that item
       |${textWithColour("finish", commandColour)}          - Finish an investigation if you are in the truck and you have started an investigation
       |${textWithColour("quit", commandColour)}            - Quit the whole game program""".stripMargin
  val tutorialText =
    s"""An investigation starts with you the player arriving to a haunted house in a truck. You are given a key to they house and use that to unlock the house by entering command ${textWithColour("unlock house", commandColour)}
       |Once the house is unlocked the temperatures will start to fluctuate inside the house. It is important because every room starts at the same temperature but in the ghost room temps start to drop
       |So using a thermometer is recommended to find the ghost room. It is also used to find out if the ghost has ${textWithColour("freezing", evidenceColour)} as evidence
       |There are currently 4 ghost types and they each have 3 specific evidences that you need to test for in order to know which ghost it is. Get more info on that by typing the command ${textWithColour("learn ghost", commandColour)}
       |You can pick up items with ${textWithColour("take", commandColour)} ${textWithColour("<item>", itemColour)} and once you have it in your inventory you can type ${textWithColour("use", commandColour)} ${textWithColour("<item>", itemColour)} to get results for using that item
       |To learn more about evidence and items enter the command ${textWithColour("learn evidence", commandColour)}
       |""".stripMargin
  val learnGhostText =
    s"""There are ${ghostTypes.keys.toVector.length} ghost types currently: ${ghostTypes.keys.map(textWithColour(_, ghostColour)).mkString(", ")}
       |Each of them have different evidences that you need to test for. Find out more about each evidence with ${textWithColour("learn evidence", commandColour)}
       |The evidences for each ghost are:
       |${ghostTypes.keys.map(key => s"${textWithColour(key, ghostColour)}: " + ghostTypes(key).map(textWithColour(_, evidenceColour)).mkString(", ")).mkString("\n")}""".stripMargin
  val learnEvidenceText =
    s"""There are 4 evidence types: ${"freezing,ghost orb,writing,spirit box".split(",").map(textWithColour(_, evidenceColour)).mkString(", ")}. Here is a brief introduction to them
       |${textWithColour("freezing", evidenceColour)}   - The temperature in the ghost room can be lower than 1 Celsius. You can check for this with the ${textWithColour("thermometer", itemColour)}. You will have to check the temperature multiple times
       |${textWithColour("ghost orb", evidenceColour)}  - Ghost can have a floating orb in the ghost room. It is only visible through ${textWithColour("video camera", itemColour)}
       |${textWithColour("writing", evidenceColour)}    - While a ${textWithColour("writing book", itemColour)} is in the ghost room the ghost has a chance to write something in it. The book has to be dropped onto the floor first. You have to ${textWithColour("inspect", commandColour)} the ${textWithColour("writing book", itemColour)} to see if the ghost has written in it
       |${textWithColour("spirit box", evidenceColour)} - The ghost will give a response when using ${textWithColour("spirit box", itemColour)} in the ghost room""".stripMargin

  def getLearnText(target: String): String = {
    target match {
      case "ghost"    => this.learnGhostText
      case "evidence" => this.learnEvidenceText
      case other      => s"There is no info on ${target}. There is only info for: ghost, evidence"
    }
  }

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
      "You are already in an investigation"
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
    if outcomeReport.isDefined && this.isGameRunning && Vector("test", "learn", "tutorial", "help").forall(text => !(command contains text)) then
      this.updateRoomTemps()
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")
  }
end Game
