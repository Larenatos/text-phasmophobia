package textPhasmophobia

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
      case "ghost" => this.game.getGhost.test
      case other => s"There is no: ${target} to test"
    }
  }

  def execute(): Option[String] = {
    this.verb match
      case "help"      => Some(this.game.helpText)
      case "tutorial"  => Some(this.game.tutorialText)
      case "learn"     => Some(this.game.getLearnText(this.modifiers))
      case "start"     => Some(this.game.start())
      case "unlock"    => if this.game.isGameRunning then Some(this.game.unlock(this.modifiers)) else Some(this.startReminder)
      case "go"        => if this.game.isGameRunning then Some(this.game.player.go(this.modifiers)) else Some(this.startReminder)
      case "take"      => if this.game.isGameRunning then Some(this.game.player.take(this.modifiers)) else Some(this.startReminder)
      case "drop"      => if this.game.isGameRunning then Some(this.game.player.drop(this.modifiers)) else Some(this.startReminder)
      case "inventory" => if this.game.isGameRunning then Some(this.game.player.getInventoryText) else Some(this.startReminder)
      case "journal"   => if this.game.isGameRunning then Some(this.game.player.getEvidenceInfo) else Some(this.startReminder)
      case "use"       => if this.game.isGameRunning then Some(this.game.player.use(this.modifiers)) else Some(this.startReminder)
      case "finish"    => if this.game.isGameRunning then Some(this.game.leaveInvestigation()) else Some(this.startReminder)
      case "quit"      => Some(this.game.player.quit)
      case "test"      => Some(this.test(this.modifiers))
      case other       => None
  }

  override def toString = s"$verb (modifiers: $modifiers)"
end Action


