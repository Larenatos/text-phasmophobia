package textPhasmophobia

import scala.util.Random

// TODO add evidence class for changing the name only in 1 place
val ghostTypes: Map[String, Vector[String]] = Map.apply(
  "Mare" -> Vector("spirit box", "ghost orb", "ghost writing"),
  "Revenant" -> Vector("ghost orb", "ghost writing", "freezing"),
  "Onryo" -> Vector("spirit box", "ghost orb", "freezing"),
  "Moroi" -> Vector("spirit box", "ghost writing", "freezing"),
  "Spirit" -> Vector("EMF 5", "spirit box", "ghost writing"),
  "Shade" -> Vector("EMF 5", "freezing", "ghost writing"),
  "The Twins" -> Vector("EMF 5", "freezing", "spirit box")
)

class Ghost(private val game: Game):
  private var favRoom = this.game.area.getRoom("").value // initiate with truck and override in reset()
  private var kind = ""
  private var interactionTime: Int = 0
  private var emfLevel: Int = 1

  def reset(): Unit = {
    this.kind = "Spirit" //ghostTypes.keys.toVector(Random.nextInt(ghostTypes.keys.toVector.length))
    this.favRoom = this.game.area.getAllRoomsExceptTruck(Random.nextInt(this.game.area.getAllRoomsExceptTruck.length))
  }

  def evidence = ghostTypes(this.kind)

  def getFavRoom = this.favRoom

  def test = s"Favourite room: ${this.favRoom}\nKind: ${this}"

  def getKind = this.kind

  def getEMFLevel = this.emfLevel

  def ifInteractedThisTurn = this.interactionTime == 2

  def attemptInteraction(): Unit = {
    if this.interactionTime > 0 then
      this.interactionTime -= 1

      if this.interactionTime == 0 then
        this.emfLevel = 1

    if Random.nextFloat() < 0.33 then // TODO adjust chances and durations
      this.interactionTime = 2

      // maybe trigger writing book if it is in the ghost room
      if (this.evidence contains "ghost writing") && this.favRoom.items.exists(_.name == "writing book") && Random.nextFloat < 0.4 then
        this.game.writingBook.trigger()

      // TODO differentiate level 2 and 3 or random
      this.emfLevel = if (this.evidence contains "EMF 5") && Random.nextFloat() < 0.33 then 5 else 2
  }

  override def toString: String = textWithColour(this.kind, ghostColour)
end Ghost