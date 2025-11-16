package textPhasmophobia

import scala.util.Random

val ghostTypes: Map[String, Vector[String]] = Map.apply(
  "Mare" -> Vector("spirit box", "ghost orb", "ghost writing"),
  "Revenant" -> Vector("ghost orb", "ghost writing", "freezing"),
  "Onryo" -> Vector("spirit box", "ghost orb", "freezing"),
  "Moroi" -> Vector("spirit box", "ghost writing", "freezing")
)

class Ghost(val game: Game):
  private var favRoom = this.game.area.getRoom("").value // initiate with truck and override in reset()
  private var kind = ""

  def reset(): Unit = {
    this.kind = ghostTypes.keys.toVector(Random.nextInt(ghostTypes.keys.toVector.length))
    this.favRoom = this.game.area.getAllRoomsExceptTruck(Random.nextInt(this.game.area.getAllRoomsExceptTruck.length))
  }

  def evidence = ghostTypes(this.kind)

  def getFavRoom = this.favRoom

  def test = s"Favourite room: ${this.favRoom}\nKind: ${this}"

  def getKind = this.kind

  override def toString: String = textWithColour(this.kind, ghostColour)
end Ghost