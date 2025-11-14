package textphasmophobia

sealed trait location {
  val name: String

  override def toString: String = Console.YELLOW + this.name + Console.RESET
}

class Truck extends location:
  val name = "truck"

end Truck

class Room(val name: String) extends location:
  val isGhostsFavRoom = false
end Room

class NothingRoom extends location{
  val name = ""
}