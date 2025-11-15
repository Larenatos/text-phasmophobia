package textPhasmophobia

import scala.util.Random

val ghostTypes: Map[String, Vector[String]] = Map.apply(
  "Mare" -> Vector("spirit box", "ghost orb", "ghost writing"),
  "Revenant" -> Vector("ghost orb", "ghost writing", "freezing"),
  "Onryo" -> Vector("spirit box", "ghost orb", "freezing"),
  "Moroi" -> Vector("spirit box", "ghost writing", "freezing")
)

class Ghost(val game: Game):
  val favRoom = this.game.area.getAllRooms(Random.nextInt(this.game.area.getAllRooms.length))
  val kind = ghostTypes.keys.toVector(Random.nextInt(ghostTypes.keys.toVector.length))
  val evidence = ghostTypes(this.kind)

  def test = s"Favourite room: ${this.favRoom}\nKind: ${this}"

  override def toString: String = Console.RED + this.kind + Console.RESET
end Ghost