package textphasmophobia

sealed trait location {
  val name: String

  override def toString: String = textWithColour(this.name, roomColour)
}

class Truck extends location:
  val name = "truck"

end Truck

class Room(val name: String) extends location:
end Room

class NothingRoom extends location{
  val name = ""
}