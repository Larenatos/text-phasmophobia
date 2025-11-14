package textphasmophobia

class Action(input: String, val game: Game):
  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim.toLowerCase

  private val startReminder =
    s"""Start an investigation first!
       |You can do that by entering the command ${textWithColour("start", commandColour)}""".stripMargin

  private def test(target: String): String = {
    target.toLowerCase() match {
      case "area" => {
        this.game.area.getMapString + "\nAll rooms: " + this.game.area.getAllRooms.mkString(", ")
      }
      case "player" => this.game.player.test
      case "ghost" => this.game.getGhost.fold(this.startReminder)(_.test)
      case other => s"There is no: ${target} to test"
    }
  }

  def execute(): Option[String] = {
    this.verb match
      case "help"      => Some(this.game.helpText)
      case "start"     => Some(this.game.start())
      case "go"        => if this.game.isGameRunning then Some(this.game.player.go(this.modifiers)) else Some(this.startReminder)
      case "take"      => if this.game.isGameRunning then Some(this.game.player.take(this.modifiers)) else Some(this.startReminder)
      case "inventory" => if this.game.isGameRunning then Some(this.game.player.getInventoryText) else Some(this.startReminder)
      case "finish"    => Some(this.game.leaveInvestigation())
      case "quit"      => Some(this.game.player.quit)
      case "test"      => Some(this.test(this.modifiers))
      case other       => None
  }

  override def toString = s"$verb (modifiers: $modifiers)"
end Action


