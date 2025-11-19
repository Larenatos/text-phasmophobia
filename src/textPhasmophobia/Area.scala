package textPhasmophobia

import scala.collection.mutable.Buffer

case class Node[location](value: location, children: Vector[Node[location]])

class Area(private val game: Game):
  val rooms: Node[Location] =
    Node(Truck(this.game), Vector(
      Node(Room("foyer", this.game), Vector(
        Node(Room("boy bedroom", this.game), Vector.empty),
        Node(Room("bathroom", this.game), Vector.empty),
        Node(Room("nursery", this.game), Vector.empty),
        Node(Room("living room", this.game), Vector(
          Node(Room("master bedroom", this.game), Vector.empty),
          Node(Room("basement", this.game), Vector.empty),
          Node(Room("utility", this.game), Vector(
            Node(Room("garage", this.game), Vector.empty)
          )),
          Node(Room("kitchen", this.game), Vector(
            Node(Room("dining room", this.game), Vector.empty)
          ))
        ))
      ))
    ))

  def reset() = {
    this.getRoomsInVector(this.rooms).foreach(_.reset())
  }

  def getRooms = this.rooms

  def getRoom(location: String) = {
    var roomNode = this.rooms

    if location.nonEmpty then
      for index <- location.split("") do
        roomNode = roomNode.children(index.toInt)

    roomNode
  }

  def getAccessibleRooms(location: String): Vector[Location] = {
    val rooms: Buffer[Location] = Buffer.empty

    if location.nonEmpty then
      rooms.append(this.getRoom(location.dropRight(1)).value)

    val children = this.getRoom(location).children.map(_.value)

    if !(rooms contains this.rooms.value) && location.nonEmpty then
      rooms.toVector ++ children :+ this.rooms.value
    else
      rooms.toVector ++ children

  }

  private def getRoomsInVector(current: Node[Location]): Vector[Location] = {
    var childVector: Vector[Location] = Vector.empty
    current.children.foreach(child => childVector = childVector ++ this.getRoomsInVector(child))
    current.value +: childVector
  }

  def getAllRoomsExceptTruck: Vector[Location] = this.getRoomsInVector(this.rooms.children(0))

  private def printTree(current: Node[Location], indent: String = ""): Vector[String] = {
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
