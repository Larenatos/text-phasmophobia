package textphasmophobia

case class Node[location](value: location, children: Vector[Node[location]])

class Area:
  val rooms: Node[location] =
    Node(new Truck, Vector(
      Node(new Room("foyer"), Vector(
        Node(new Room("boy bedroom"), Vector.empty),
        Node(new Room("bathroom"), Vector.empty),
        Node(new Room("nursery"), Vector.empty),
        Node(new Room("living room"), Vector(
          Node(new Room("master bedroom"), Vector.empty),
          Node(new Room("basement"), Vector.empty),
          Node(new Room("utility"), Vector(
            Node(new Room("Garage"), Vector.empty)
          )),
          Node(new Room("kitchen"), Vector(
            Node(new Room("dining room"), Vector.empty)
          ))
        ))
      ))
    ))

  def getRoom(location: String) = {
    var roomNode = this.rooms

    if location.nonEmpty then
      for index <- location.split("") do
        roomNode = roomNode.children(index.toInt)

    roomNode
  }

  def getAccessibleRooms(location: String): Vector[location] = {
    val parentRoom: Vector[location] = if location.nonEmpty then
      Vector(this.getRoom(location.dropRight(1)).value)
    else
      Vector.empty

    val children = this.getRoom(location).children
    parentRoom ++ children.map(_.value)
  }

  private def getRoomsInVector(current: Node[location]): Vector[location] = {
    var childVector: Vector[location] = Vector.empty
    current.children.foreach(child => childVector = childVector ++ this.getRoomsInVector(child))
    current.value +: childVector
  }

  def getAllRooms: Vector[location] = this.getRoomsInVector(this.rooms.children(0))

  private def printTree(current: Node[location], indent: String = ""): Vector[String] = {
    var childStrings: Vector[String] = Vector.empty
    current.children.foreach(child => {
      childStrings = childStrings ++ this.printTree(child, indent + "  ")
    })
    (indent + current.value.toString) +: childStrings
  }

  def getMapString = {
    s"""Map test printout:
       |Text version of the rooms:
       |${this.printTree(this.rooms, "").mkString("\n")}""".stripMargin
  }
end Area
