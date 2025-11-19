package textPhasmophobia

import scala.util.Random

sealed trait Item(private val game: Game):
  val name: String
  val evidenceText = s"\nCheck what evidence you have found and what ghost type it could be with ${textWithColour("journal", commandColour)}"
  protected var isTutorialTextShown = false

  protected def tutorialText: String = s"You can use this item either with ${textWithColour("use " + this.name, commandColour)} or ${textWithColour("equip " + this.name, commandColour)}"

  def use: String

  def getTutorialText: String = {
    if !this.isTutorialTextShown then
      this.isTutorialTextShown = true
      "\n" + this.tutorialText
    else
      ""
  }

  override def toString: String = textWithColour(this.name, itemColour)
end Item

class Thermometer(game: Game) extends Item(game):
  val name = "thermometer"

  def use: String = {
    val temperature = this.game.player.location.getTemperature
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
    if this.game.player.isInGhostRoom && (this.game.ghost.evidence contains "ghost orb") then
      this.game.player.addEvidence("ghost orb")
      s"There is a ${textWithColour("ghost orb", evidenceColour)} floating in this room. This is an evidence for the ghost." + this.evidenceText
    else
      "There is no ghost orb in this room"
  }
end VideoCamera

class SpiritBox(game: Game) extends Item(game):
  val name = "spirit box"

  def use: String = {
    if this.game.player.isInGhostRoom && (this.game.ghost.evidence contains "spirit box") then
      this.game.player.addEvidence("spirit box")
      s"You got a response from the ghost in ${textWithColour("spirit box", evidenceColour)}. This is an evidence for the ghost." + this.evidenceText
    else
      "You get no response"
  }
end SpiritBox

class WritingBook(game: Game) extends Item(game):
  val name = "writing book"
  override def tutorialText = s"You have to first enter: ${textWithColour("drop " + this.name, commandColour)} and then to check if the ghost has written on it: ${textWithColour("inspect " + this.name, commandColour)} while in the same room"
  private var isWrittenTo = false

  def reset(): Unit = {
    this.isWrittenTo = false
  }

  def trigger() = {
    if !this.isWrittenTo then
      this.isWrittenTo = true
  }

  def use: String = {
    if this.isWrittenTo then
      this.game.player.addEvidence("ghost writing")
      s"The ghost has written in this ${textWithColour("book", itemColour)}. This is a type of evidence for the ghost" + this.evidenceText
    else
      s"The ghost has not written on this book"
  }
end WritingBook

class EMFReader(game: Game) extends Item(game):
  val name = "EMF reader"
  override def tutorialText = {
    s"""You can use this item either with ${textWithColour("use " + this.name, commandColour)} or ${textWithColour("equip " + this.name, commandColour)} or ${textWithColour("drop emf reader", commandColour)}.
       |If you drop the reader in a room where the ghost has done an interaction in, you will hear it beep if you are in that room.
       |There are different sounds for each level of reading so you will know if it is ${textWithColour("EMF 5", evidenceColour)} evidence""".stripMargin
  }
  def use: String = {
    val emfLevel = this.game.ghost.getEMFLevel
    if emfLevel > 1 then
      if emfLevel == 5 then
        this.game.player.addEvidence("EMF 5")
        s"The ghost has interacted and there is an EMF reading of 5. This is a type of evidence for the ghost" + this.evidenceText
      else
        s"The ghost has interacted and there is an EMF reading of ${emfLevel}"
    else
      s"There is an EMF reading of 1"
  }
end EMFReader

