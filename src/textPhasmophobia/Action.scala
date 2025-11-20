package textPhasmophobia

class Action(input: String, private val game: Game):
  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim.toLowerCase

  private val startReminder =
    s"""Start an investigation first!
       |You can do that by entering the command ${textWithColour("start", commandColour)}""".stripMargin

  private def test(target: String): String = {
    target.toLowerCase() match {
      case "area" => {
        this.game.area.getMapString + "\nAll rooms: " + this.game.area.getAllRoomsExceptTruck.mkString(", ")
      }
      case "ghost" => this.game.ghost.test
      case other => s"There is no: ${target} to test"
    }
  }

  // Two different methods where one takes in function that take parameters
  private def runGameCommand(command: String => String): Option[String] = {
    if this.game.isGameRunning then
      Some(command(this.modifiers))
    else
      Some(this.startReminder)
  }
  // and this one takes function with no parameters
  private def runGameCommand(command: () => String): Option[String] = {
    if this.game.isGameRunning then
      Some(command())
    else
      Some(this.startReminder)
  }

  def execute(): Option[String] = {
    this.verb match {
      case "help"      => Some(this.game.helpText)
      case "tutorial"  => Some(this.game.tutorialText)
      case "learn"     => Some(this.game.getLearnText(this.modifiers))
      case "start"     => Some(this.game.start())
      case "unlock"    => runGameCommand(this.game.unlock)
      case "go"        => runGameCommand(this.game.player.go)
      case "take"      => runGameCommand(this.game.player.take)
      case "drop"      => runGameCommand(this.game.player.drop)
      case "inventory" => runGameCommand(() => this.game.player.getInventoryText)
      case "journal"   => runGameCommand(() => this.game.player.getEvidenceInfo)
      case "use"       => runGameCommand(this.game.player.use)
      case "equip"     => runGameCommand(this.game.player.equipItem)
      case "inspect"   => runGameCommand(this.game.player.inspect)
      case "observe"   => runGameCommand(() => this.game.player.observe)
      case "finish"    => runGameCommand(() => this.game.leaveInvestigation())
      case "quit"      => Some(this.game.player.quit)
      case "test"      => Some(this.test(this.modifiers))
      case other       => None
    }
  }
end Action


