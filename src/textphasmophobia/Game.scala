package textphasmophobia

class Game {
  var welcomeMessage = "This is phasmophobia in text form. The game has you enter a haunted hause and your task is to find out what type of ghost it is. "
  var turnCount = 0
  var isObjectiveDone = false

  val player = Player()

  def isOver = this.player.hasQuit

  def playTurn(command: String): String =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")
}
