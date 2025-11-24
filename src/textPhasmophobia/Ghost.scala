package textPhasmophobia

import scala.util.Random

val ghostTypes: Map[String, Vector[Evidence]] = Map.apply(
  "Spirit"    -> Vector(emf5, spiritBox, ghostWriting),
  "Wraith"    -> Vector(emf5, spiritBox, dots),
  "Mare"      -> Vector(spiritBox, ghostOrb, ghostWriting),
  "Revenant"  -> Vector(ghostOrb, ghostWriting, freezing),
  "Shade"     -> Vector(emf5, ghostWriting, freezing),
  "Yurei"     -> Vector(ghostOrb, freezing, dots),
  "Oni"       -> Vector(emf5, freezing, dots),
  "Yokai"     -> Vector(spiritBox, ghostOrb, dots),
  "Onryo"     -> Vector(spiritBox, ghostOrb, freezing),
  "The Twins" -> Vector(emf5, freezing, spiritBox),
  "Raiju"     -> Vector(emf5, ghostOrb, dots),
  "Moroi"     -> Vector(spiritBox, ghostWriting, freezing),
  "Deogen"    -> Vector(spiritBox, ghostWriting, dots),
  "Thaye"     -> Vector(ghostOrb, ghostWriting, dots),
)

class Ghost(private val game: Game):
  private var favRoom = this.game.area.getRoom("").value // initiate with truck and override in reset()
  private var kind = ""
  private var interactionTime: Int = 0
  private var emfLevel: Int = 1
  private val interactionEMFDuration = 3
  private var isDoingDOTS = false
  private var turnsSinceDots = 0

  def reset(): Unit = {
    this.kind = ghostTypes.keys.toVector(Random.nextInt(ghostTypes.keys.toVector.length))
    this.favRoom = this.game.area.getAllRoomsExceptTruck(Random.nextInt(this.game.area.getAllRoomsExceptTruck.length))
  }

  def evidence = ghostTypes(this.kind)

  def getFavRoom = this.favRoom

//  def test = s"Favourite room: ${this.favRoom}\nKind: ${this}\nEvidence:${this.evidence.mkString(", ")}"

  def getKind = this.kind

  def getEMFLevel = this.emfLevel

  def getIsDoingDOTS = this.isDoingDOTS

  def ifInteractedThisTurn = this.interactionTime == this.interactionEMFDuration

  def attemptInteraction(): Unit = {
    if this.interactionTime > 0 then
      this.interactionTime -= 1

    if this.interactionTime == 0 && this.emfLevel != 1 then
      this.emfLevel = 1

    if (this.evidence contains dots) && this.favRoom.hasItem(this.game.dotsProjector.name) && turnsSinceDots > 1 && Random.nextFloat() < 0.34 then
      this.isDoingDOTS = true
      this.turnsSinceDots = 0
    else if this.isDoingDOTS then
      this.isDoingDOTS = false
    else
      this.turnsSinceDots += 1

    if Random.nextFloat() < 0.4 then
      this.interactionTime = this.interactionEMFDuration

      // maybe trigger writing book if it is in the ghost room
      if (this.evidence contains ghostWriting) && this.favRoom.items.exists(_.name == "writing book") && Random.nextFloat() < 0.5 then
        this.game.writingBook.trigger()

      this.emfLevel = if this.evidence contains emf5 then 5 else 2
  }

  override def toString: String = textWithColour(this.kind, ghostColour)
end Ghost