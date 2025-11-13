package textphasmophobia

class Action(input: String, val game: Game):
  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  def execute(): Option[String] = {
    this.verb match
      case "start"     => Some(this.game.start())
      case "go"        => Some(this.game.player.go(this.modifiers))
      case "quit"      => Some(this.game.player.quit)
      case "print"     => Some(this.game.map.printMap())
      case "player"    => Some(this.game.player.test)
      case other       => None
  }

  override def toString = s"$verb (modifiers: $modifiers)"
end Action


