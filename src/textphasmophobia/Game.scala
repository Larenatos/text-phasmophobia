package textphasmophobia

class Game:
  var welcomeMessage =
    s"""\nThis is phasmophobia in text form. The game has you enter a haunted hause and your task is to find out what type of ghost it is.
    |You can always type ${textWithColour("help", commandColour)} the get list of all the commands you can use and their description. Try it out!
    |To start playing you need to start an investigation.
    |You can do that by entering the command: ${textWithColour("start", commandColour)}""".stripMargin
  var helpText =
    s"""Here is a list of ${textWithColour("commands", commandColour)} you can use
    |${textWithColour("help", commandColour)}      - Commnand to print this message
    |${textWithColour("start", commandColour)}     - Start an investigation if there is no ongoing investigation
    |${textWithColour("go <name>", commandColour)} - More to room <name> if it exists and is accessible from where you are now
    |${textWithColour("finish", commandColour)}    - Finish an investigation if you are in the truck and you have started an investigation
    |${textWithColour("quit", commandColour)}      - Quit the whole game program""".stripMargin

  private var turnCount = 0
  var isObjectiveDone = false
  var isGameRunning = false

  val area = new Area
  val player = Player(this)
  private var ghost: Option[Ghost] = None

  def getGhost: Option[Ghost] = this.ghost

  def isOver = this.player.hasQuit

  def getTurnCount = this.turnCount

  def start() = {
    if this.isGameRunning then
      "Game is already running"
    else
      this.isGameRunning = true
      this.ghost = Some(Ghost(this))
      s"""You are now in ${textWithColour("truck", roomColour)} outside Tanglewood Drive 6.
      |The house is haunted by a ghost.
      |Your goal is to visit the ghost room and leave.
      |${this.player.getLocationInfo.dropWhile(_ != '.').drop(3)}
      |You can go to a room by typing ${textWithColour("go",commandColour)} ${textWithColour("foyer", roomColour)}""".stripMargin
  }

  def leaveInvestigation(): String = {
    if this.isGameRunning then
      if this.player.location.name == "truck" then
        val report =
          s"""You finished an investigation. Your investigation lasted for ${this.turnCount}
          |The ghost was ${this.ghost.fold("")(_.kind)} and it's favourite room was ${this.ghost.fold("")(_.favRoom)}""".stripMargin
        this.turnCount = 0
        this.isGameRunning = false
        this.ghost = None
        report
      else
        "You need to be in the truck to be able to leave"
    else
      "You need to have started the investigation first"
  }

  def playTurn(command: String): String = {
    val action = Action(command, this)
    val outcomeReport = action.execute()
    if outcomeReport.isDefined && this.isGameRunning then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")
  }
end Game
