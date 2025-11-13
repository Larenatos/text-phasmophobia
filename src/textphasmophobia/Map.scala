package textphasmophobia

sealed trait Tree[+A]
case class Node[String](name: String, value: location, children: Vector[Node[String]]) extends Tree[String]

class Map:
  val rooms =
    Node("Truck", new Truck, Vector(
      Node("Foyer", new Room("foyer"), Vector(
        Node("Boy Bedroom", new Room("boy bedroom"), Vector.empty),
        Node("Bathroom", new Room("bathroom"), Vector.empty),
        Node("Nursery", new Room("nursery"), Vector.empty),
        Node("Living Room", new Room("living room"), Vector(
          Node("Master Bedroom", new Room("master bedroom"), Vector.empty),
          Node("Basement", new Room("basement"), Vector.empty),
          Node("Utility", new Room("utility"), Vector(
            Node("Garage", new Room("Garage"), Vector.empty)
          )),
          Node("Kitchen", new Room("kitchen"), Vector(
            Node("Dining Room", new Room("dining room"), Vector.empty)
          ))
        ))
      ))
    ))

  def getRoom(location: String) = {
    var roomNode = this.rooms

    if location.nonEmpty then
      for index <- location.split("") do
        roomNode = roomNode._3(index.toInt)

    roomNode
  }

  def getAccessibleRoomNames(location: String): Vector[String] = {
    val parentRoom = if location.nonEmpty then this.getRoom(location.dropRight(1)).name else ""
    val children = this.getRoom(location).children
    Vector(parentRoom) ++ children.map(_.name)
  }

  private def printTree[A](tree: Tree[A], indent: String = ""): Unit = {
    tree match {
      case Node(name, value, children) =>
        println(s"$indent$name")
        children.foreach(printTree(_, indent + "  "))
    }
  }

  def printMap() = {
    println("\nText printout version of the rooms")
    this.printTree(this.rooms, "")
    ""
  }
end Map
