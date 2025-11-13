package textphasmophobia

class Game:
  var welcomeMessage = s"""\nThis is phasmophobia in text form. The game has you enter a haunted hause and your task is to find out what type of ghost it is.
To start playing enter the command: ${Console.GREEN}start${Console.RESET}"""
  private var turnCount = 0
  var isObjectiveDone = false
  var isGameRunning = false

  val map = new Map
  val player = Player(this)

  def isOver = this.player.hasQuit

  def getTurnCount = this.turnCount

  def start() = {
    if this.isGameRunning then
      "Game is already running"
    else
      this.isGameRunning = true
      s"""You are now in a truck outside Tanglewood Drive 6.
The house is haunted by a ghost.
Your goal is to visit the ghost room and leave.
${this.player.getLocationInfo.dropWhile(_ != '.').drop(2)}
You can go to a room by typing ${Console.GREEN}go ${Console.BLUE}foyer${Console.RESET}"""
  }

  def playTurn(command: String): String = {
    val action = Action(command, this)
    val outcomeReport = action.execute()
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")
  }
end Game
