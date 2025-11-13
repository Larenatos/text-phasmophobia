package textphasmophobia

sealed trait location {
  val name: String
}

class Truck extends location:
  val name = "truck"

end Truck

class Room(val name: String) extends location:

end Room