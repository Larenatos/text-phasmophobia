package textPhasmophobia

sealed trait Item(game: Game):
  val name: String
  val evidenceText = s"\nCheck what evidences you have found and what ghost type it could be with ${textWithColour("journal", commandColour)}"

  def use: String

  override def toString: String = textWithColour(this.name, itemColour)
end Item

class Thermometer(game: Game) extends Item(game):
  val name = "thermometer"

  def use: String = {
    val temperature = this.game.player.location.temperature
    var text = s"Temperature in this room is ${textWithColour(temperature.toString + " celsius", temperatureColour)}"

    if temperature < 1 then
      text += s"\nThere is ${textWithColour("freezing", evidenceColour)} temperatures here. ${textWithColour("freezing", evidenceColour)} is an evidence for the ghost." + this.evidenceText
      this.game.player.addEvidence("freezing")

    text
  }
end Thermometer

class VideoCamera(game: Game) extends Item(game):
  val name = "video camera"

  def use: String = {
    if this.game.player.isInGhostRoom && (this.game.getGhost.evidence contains "ghost orb") then
      this.game.player.addEvidence("ghost orb")
      s"There is a ${textWithColour("ghost orb", evidenceColour)} floating in this room. This is an evidence for the ghost." + this.evidenceText
    else
      "There is no ghost orb in this room"
  }
end VideoCamera

class SpiritBox(game: Game) extends Item(game):
  val name = "spirit box"

  def use: String = {
    if this.game.player.isInGhostRoom && (this.game.getGhost.evidence contains "spirit box") then
      this.game.player.addEvidence("spirit box")
      s"You got a response from the ghost in ${textWithColour("spirit box", evidenceColour)}. This is an evidence for the ghost." + this.evidenceText
    else
      "You get no response"
  }
end SpiritBox
