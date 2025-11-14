package textphasmophobia

import scala.util.Random

private val ghostTypes: Map[String, Vector[String]] = Map.apply(
  "Mare" -> Vector("Spirit Box", "Ghost Orbs", "Writing"),
  "Revenant" -> Vector("Ghost Orbs", "Writing", "Freezing"),
  "Onryo" -> Vector("Spirit Box", "Ghost Orbs", "Freezing"),
  "Moroi" -> Vector("Spirit Box", "Writing", "Freezing")
)

class Ghost(val game: Game):
  val favRoom = this.game.area.getAllRooms(Random.nextInt(this.game.area.getAllRooms.length))
  val kind = ghostTypes.keys.toVector(Random.nextInt(ghostTypes.keys.toVector.length))

  def test = s"Favourite room: ${this.favRoom}\nKind: ${this}"

  override def toString: String = Console.RED + this.kind + Console.RESET
end Ghost