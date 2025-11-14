package textphasmophobia

sealed trait Item:
  val name: String

  override def toString: String = textWithColour(this.name, itemColour)
end Item

class Thermometer extends Item:
  val name = "thermometer"
end Thermometer
