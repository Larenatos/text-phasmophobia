package textphasmophobia

class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  def execute(player: Player): Option[String] =
    this.verb match
      case "go"        => Some(player.go(this.modifiers))
      case "quit"      => Some(player.quit)
      case other       => None

  override def toString = s"$verb (modifiers: $modifiers)"

end Action


