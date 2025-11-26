package textPhasmophobia

import scala.util.Random

sealed trait Item(private val game: Game):
  val name: String
  val evidence: Evidence
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

  def addEvidence() = {
    this.game.player.addEvidence(this.evidence)
  }

  override def toString: String = textWithColour(this.name, itemColour)
end Item

class Thermometer(game: Game) extends Item(game):
  val name = "thermometer"
  val evidence = freezing

  def use: String = {
    val temperature = this.game.player.location.getTemperature
    var text = s"Temperature in this room is ${textWithColour(temperature.toString + " celsius", temperatureColour)}"

    if temperature < 1 then
      text += s"\nThere is ${this.evidence} temperatures here. ${textWithColour("freezing", evidenceColour)} is an evidence for the ghost." + this.evidenceText
      this.addEvidence()

    text
  }
end Thermometer

class VideoCamera(game: Game) extends Item(game):
  val name = "video camera"
  val evidence = ghostOrb

  def use: String = {
    if this.game.player.isInGhostRoom && (this.game.ghost.evidence contains this.evidence) then
      this.addEvidence()
      s"There is a ${this.evidence} floating in this room. This is an evidence for the ghost." + this.evidenceText
    else
      "There is no ghost orb in this room"
  }
end VideoCamera

class SpiritBox(game: Game) extends Item(game):
  val name = "spirit box"
  val evidence = spiritBox

  def use: String = {
    if this.game.player.isInGhostRoom && (this.game.ghost.evidence contains this.evidence) then
      this.addEvidence()
      s"You got a response from the ghost in ${this.evidence}. This is an evidence for the ghost." + this.evidenceText
    else
      s"You get no response in ${this.toString}"
  }
end SpiritBox

class WritingBook(game: Game) extends Item(game):
  val name = "writing book"
  val evidence = ghostWriting
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
      this.addEvidence()
      s"The ghost has written in this ${textWithColour("book", itemColour)}. This is a type of evidence for the ghost" + this.evidenceText
    else
      s"The ghost has not written on this book"
  }
end WritingBook

class EMFReader(game: Game) extends Item(game):
  val name = "EMF reader"
  val evidence = emf5
  override def tutorialText = {
    s"""You can use this item either with ${textWithColour("use " + this.name, commandColour)} or ${textWithColour("equip " + this.name, commandColour)} or ${textWithColour("drop emf reader", commandColour)} and then ${textWithColour("observer", commandColour)}.
       |If you drop the reader in a room where the ghost has done an interaction in, you will hear it beep if you are in that room.
       |There are different sounds for each level of reading so you will know if it is ${this.evidence} evidence""".stripMargin
  }

  def use: String = {
    val emfLevel = if this.game.player.isInGhostRoom || this.game.ghost.getFavRoom.hasItem("emf reader") then this.game.ghost.getEMFLevel else 1
    if emfLevel > 1 then
      if emfLevel == 5 then
        this.addEvidence()
        s"The ghost has interacted and there is an ${this.evidence} reading. This is a type of evidence for the ghost" + this.evidenceText
      else
        s"The ghost has interacted and there is an EMF reading of ${emfLevel}"
    else
      s"There is an EMF reading of 1"
  }
end EMFReader

class DotsProjector(game: Game) extends Item(game):
  val name = "dots projector"
  val evidence = dots

  override def tutorialText = {
    s"""This item has to be dropped on the ground first and then you can use ${textWithColour("observe", commandColour)} to check for this evidence
       |because this is a visual thing that happens in the room. If the ${this.evidence} projection happens while you are in the room you will be notified of it""".stripMargin
  }

  def use: String = {
    if this.game.ghost.getIsDoingDOTS then
      var text = s"You see a ${this.evidence} projection of the ghost in the room. This is a type of evidence for the ghost."
      if !(this.game.player.evidence contains dots) then
        text += this.evidenceText

      this.addEvidence()
      text
    else
      ""
  }
end DotsProjector

