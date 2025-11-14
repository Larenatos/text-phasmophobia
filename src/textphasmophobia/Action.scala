package textphasmophobia

class Action(input: String, val game: Game):
  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  private val startReminder = s"Start an investigation first!\nYou can do that by entering the command ${Console.GREEN}start${Console.RESET}"

  private def test(target: String): String =
    target.toLowerCase() match {
      case "area" => {
        this.game.area.getMapString + "\nAll rooms: " + this.game.area.getAllRooms.mkString(", ")
      }
      case "player" => this.game.player.test
      case "ghost" => {
        if this.game.isGameRunning && this.game.getGhost.isDefined then
          this.game.getGhost match {
            case Some(value) => value.test
            case None => this.startReminder
          }
        else this.startReminder
      }
      case other => s"There is no: ${target} to test"
    }

  def execute(): Option[String] = {
    this.verb match
      case "start"     => Some(this.game.start())
      case "go"        => if this.game.isGameRunning then Some(this.game.player.go(this.modifiers)) else Some(this.startReminder)
      case "finish"    => Some(this.game.leaveInvestigation())
      case "quit"      => Some(this.game.player.quit)
      case "test"      => Some(this.test(this.modifiers))
      case other       => None
  }

  override def toString = s"$verb (modifiers: $modifiers)"
end Action


