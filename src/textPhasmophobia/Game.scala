package textPhasmophobia

class Game:
  val welcomeMessage =
    s"""\nThis is phasmophobia in text form. The game has you enter a haunted hause and your task is to find out what type of ghost it is.
       |You can always type ${textWithColour("help", commandColour)} the get list of all the commands you can use and their description. Try it out!
       |Enter command ${textWithColour("tutorial", commandColour)} to get a brief introduction on how the game works and what you need to do in order to succeed in an investigation
       |${textWithColour("Please read tutorial before playing", importantColour)}
       |
       |Start an investigation with command: ${textWithColour("start", commandColour)}""".stripMargin
  val helpText =
    s"""Here is a list of ${textWithColour("commands", commandColour)} you can use
       |${textWithColour("help", commandColour)}            - Print this message
       |${textWithColour("tutorial", commandColour)}        - Print a long text with introduction on how to play this game
       |${textWithColour("learn <thing>", commandColour)}   - Show information regarding <thing>. Info only exists for: ${textWithColour("ghost", ghostColour)}, ${textWithColour("evidence", evidenceColour)}
       |${textWithColour("start", commandColour)}           - Start an investigation if there is no ongoing investigation
       |${textWithColour("unlock <target>", commandColour)} - Usually ${textWithColour("unlock", commandColour)} ${textWithColour("house", roomColour)}. This is used to actually start the investigation into the house
       |${textWithColour("go <name>", commandColour)}       - More to room <name> if it exists and is accessible from where you are now
       |${textWithColour("take", commandColour)}            - Will take an item from this room if it is here and put it into your inventory
       |${textWithColour("drop", commandColour)}            - Place an item in your current location
       |${textWithColour("inventory", commandColour)}       - Show all the items in your inventory at this time
       |${textWithColour("journal", commandColour)}         - Show what ${textWithColour("evidence", evidenceColour)} you have found and possible ${textWithColour("ghost types", ghostColour)}. It also shows you if you have completed the objective or not
       |${textWithColour("use <item>", commandColour)}      - Uses ${textWithColour("<item>", itemColour)} from players inventory and you will get a result for using that item
       |${textWithColour("equip <item>", commandColour)}    - Equips ${textWithColour("<item>", itemColour)} and after that when ever you go to a room you will use that item and get results. Easy way to find where the ghost is
       |${textWithColour("inspect <item>", commandColour)}  - Inspects ${textWithColour("<item>", itemColour)} and you get info of that item. You can only inspect ${textWithColour("writing book", itemColour)} to see if the ghost has written to it. This is one type of ${textWithColour("evidence", evidenceColour)}
       |${textWithColour("observe", commandColour)}         - Adds a turn and use it when waiting for something to happen like ghost interaction for emf and writing book
       |${textWithColour("finish", commandColour)}          - Finish an investigation if you are in the truck and you have started an investigation
       |${textWithColour("quit", commandColour)}            - Quit the whole game program
       |
       |Here is a list of the colours you will see and what they mean: ${textWithColour("command", commandColour)}, ${textWithColour("room", roomColour)}, ${textWithColour("ghost", ghostColour)}, ${textWithColour("item", itemColour)}, ${textWithColour("temperature", temperatureColour)}, ${textWithColour("evidence", evidenceColour)}""".stripMargin
  val tutorialText =
    s"""${this.helpText}
       |
       |An investigation starts with you the player arriving to a haunted house in a truck. You are given a key to the house and use that to unlock the house with ${textWithColour("unlock house", commandColour)}.
       |Once the house is unlocked the temperatures will start to fluctuate inside. It is important because every room starts at the same temperature but in the ghost room the temperature starts to drop.
       |It is recommended to use a thermometer to find the ghost room. It is also used to find out if the ghost has ${textWithColour("freezing", evidenceColour)} as ${textWithColour("evidence", evidenceColour)}.
       |There are currently 4 ghost types and they each have 3 specific ${textWithColour("evidence", evidenceColour)} that you need to test for in order to know which ghost it is. Get more info on that by typing the command ${textWithColour("learn ghost", commandColour)}
       |You can pick up items with ${textWithColour("take", commandColour)} ${textWithColour("<item>", itemColour)} and once you have it in your inventory you can type ${textWithColour("use", commandColour)} ${textWithColour("<item>", itemColour)} or ${textWithColour("equip", commandColour)} ${textWithColour("<item>", itemColour)} to get information out of them.
       |You get notified if you find ${textWithColour("evidence", evidenceColour)} while using an item and they will be marked in your journal. Journal contains your found ${textWithColour("evidence", evidenceColour)} and what ${textWithColour("ghost types", ghostColour)} it could be. You can view your journal with ${textWithColour("journal", commandColour)}
       |To learn more about evidence and items enter the command ${textWithColour("learn evidence", commandColour)}
       |
       |Here is a small example of commands that you can run to start finding the ghost room
       |${textWithColour("start", commandColour)}                - start investigation
       |${textWithColour("unlock house", commandColour)}         - opens the locked door to the house
       |${textWithColour("take thermometer", commandColour)}     - picks up ${textWithColour("thermometer", itemColour)} from the truck
       |${textWithColour("take writing book", commandColour)}    - You can also take another item because you have 2 inventory slots
       |${textWithColour("equip thermometer", commandColour)}    - Equip the item so that you get information from it everytime you enter a room
       |${textWithColour("go foyer", commandColour)}             - Goes to ${textWithColour("foyer", roomColour)} and prints out the ${textWithColour("temperature", temperatureColour)} in that room you went to. Visit rooms like this until you find the ghost room
       |${textWithColour("go living room", commandColour)}       - Track which rooms you have already been to and go to other ones. You are looking for a room with temperature below ${textWithColour("15 celsius", temperatureColour)}
       |${textWithColour("drop writing book", commandColour)}    - When you find the ghost room, place writing book there and the ghost has chance to write to it on every turn if it is one of the ${textWithColour("evidence", evidenceColour)} for that ghost
       |${textWithColour("inspect writing book", commandColour)} - See if the ghost has written on the book
       |You will need to go and take more items from the truck to find all 3 ${textWithColour("evidence", evidenceColour)}. You can leave the current items in the ghost room with ${textWithColour("drop <item>", commandColour)}""".stripMargin
  val learnGhostText =
    s"""There are ${ghostTypes.keys.toVector.length} ghost types currently: ${ghostTypes.keys.map(textWithColour(_, ghostColour)).mkString(", ")}
       |Each of them have different ${textWithColour("evidence", evidenceColour)} that you need to test for. Find out more about each ${textWithColour("evidence", evidenceColour)} with ${textWithColour("learn evidence", commandColour)}
       |The ${textWithColour("evidence", evidenceColour)} for each ghost are:
       |${ghostTypes.keys.map(key => s"${textWithColour(key, ghostColour)}: " + ghostTypes(key).map(textWithColour(_, evidenceColour)).mkString(", ")).mkString("\n")}""".stripMargin
  val learnEvidenceText =
    s"""There are 4 ${textWithColour("evidence", evidenceColour)} types: ${"freezing,ghost orb,writing,spirit box".split(",").map(textWithColour(_, evidenceColour)).mkString(", ")}. Here is a brief introduction to them
       |${textWithColour("freezing", evidenceColour)}   - The temperature in the ghost room can be lower than 1 Celsius. You can check for this with the ${textWithColour("thermometer", itemColour)}. You will have to check the temperature multiple times
       |${textWithColour("ghost orb", evidenceColour)}  - Ghost can have a floating orb in the ghost room. It is only visible through ${textWithColour("video camera", itemColour)}
       |${textWithColour("writing", evidenceColour)}    - While a ${textWithColour("writing book", itemColour)} is in the ghost room the ghost has a chance to write something in it. The book has to be dropped onto the floor first. You have to ${textWithColour("inspect", commandColour)} the ${textWithColour("writing book", itemColour)} to see if the ghost has written in it
       |${textWithColour("spirit box", evidenceColour)} - The ghost will give a response when using ${textWithColour("spirit box", itemColour)} in the ghost room""".stripMargin

  def getLearnText(target: String): String = {
    target match {
      case "ghost"    => this.learnGhostText
      case "evidence" => this.learnEvidenceText
      case other      => s"There is no info on ${target}. There is only info for: ${textWithColour("ghost", ghostColour)}, ${textWithColour("evidence", evidenceColour)}"
    }
  }

  private var turnCount = 0
  private var unlockHouseTurn = 0
  private var isObjectiveDone = false
  private var isGameStarted = false
  private var isHouseUnlocked = false
  private var isObjectiveCompleted = false
  private var isQuit = false

  val area = Area(this)
  val player = Player(this)
  val ghost = Ghost(this)

  // items for the investigation
  val thermometer = Thermometer(this)
  val spiritBox = SpiritBox(this)
  val videoCamera = VideoCamera(this)
  val writingBook = WritingBook(this)
  val emfReader = EMFReader(this)

  val investigationItems = Vector(this.thermometer, this.spiritBox, this.videoCamera, this.writingBook, this.emfReader)

  def isGameRunning = this.isGameStarted

  def hasHouseBeenUnlocked = this.isHouseUnlocked

  def completeObjective() =
    this.isObjectiveCompleted = true

  def playerQuit(): Unit =
    this.isQuit = true

  def isOver = this.isQuit

  def getTurnCount = this.turnCount

  def turnsSinceHouseUnlock = this.turnCount - this.unlockHouseTurn

  def resetVariables() = {
    this.area.reset()
    this.player.reset()
    this.ghost.reset()
    this.isObjectiveCompleted = false
    this.writingBook.reset()
    this.turnCount = 0
  }

  def start() = {
    if this.isGameStarted then
      "You are already in an investigation"
    else
      this.isGameStarted = true
      this.resetVariables()
      s"""You are now in ${textWithColour("truck", roomColour)} outside Tanglewood Drive 6.
         |Your goal is to find out the type of the ghost haunting this house and leave.
         |Please read tutorial if you didn't do so yet.
         |You can move to a different room by typing ${textWithColour("go",commandColour)} ${textWithColour("<room name>", roomColour)}
         |${this.player.getLocationInfo.dropWhile(_ != '.').drop(3)}""".stripMargin
  }

  def unlock(target: String): String = {
    if target == "house" then
      this.unlockHouseTurn = this.turnCount
      this.isHouseUnlocked = true
      s"""House is now unlocked. Now the ghost will start to interact with stuff and the ${textWithColour("temperature", temperatureColour)} will start to drop in the ghost room
         |${this.player.getLocationInfo}""".stripMargin
    else
      "House is already unlocked"
  }

  def leaveInvestigation(): String = {
    if this.player.location.name == "truck" then
      val objectiveText = if this.isObjectiveCompleted then "You completed the objective. Good job!" else "You did not complete the objective"
      this.isGameStarted = false
        s"""You finished an invenstigation!
           |${objectiveText}
           |Your investigation lasted for ${this.turnCount} turns
           |The ghost was ${this.ghost} and it's favourite room was ${this.ghost.getFavRoom}
           |You can start a new investigation by entering the ${textWithColour("start", commandColour)} again!""".stripMargin
    else
      "You need to be in the truck to be able to leave"
  }

  def updateRoomTemps() = {
    if this.isHouseUnlocked then
      this.area.getAllRoomsExceptTruck.foreach(_.updateTemperature())
  }

  private def checkTurnHearing(actionReport: Option[String], command: String): String = {
    actionReport match {
      case Some(message) => {
        var text = ""

        if this.ghost.ifInteractedThisTurn && this.player.isInGhostRoom then
          text = "You hear the ghost interacting with something. Check it out with EMF reader!\n"

        if this.player.isHearingEMFReader && !(command contains "observe") then
          text += s"You hear ${this.emfReader.toString} beeping for level ${this.ghost.getEMFLevel}\n"
          if this.ghost.getEMFLevel == 5 then
            this.player.addEvidence("EMF 5")

        text + message
      }
      case None => s"""Unknown command: "$command"."""
    }
  }

  def playTurn(command: String): String = {
    if this.isGameStarted then
      this.updateRoomTemps()
      this.ghost.attemptInteraction()

    val outcomeReport = Action(command, this).execute()

    if outcomeReport.isDefined && Vector("test", "learn", "tutorial", "help").forall(text => !(command contains text)) then
      this.turnCount += 1

    this.checkTurnHearing(outcomeReport, command)
  }
end Game
