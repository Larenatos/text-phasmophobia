package textPhasmophobia

sealed class Evidence(val name: String, val infoText: String):
  override def toString: String = textWithColour(this.name, evidenceColour)

  def learn: String = this.toString + " "*(14 - this.name.length) + "- " + this.infoText
end Evidence

private val spiritBoxInfo = s"The ghost will give a response when using ${textWithColour("spirit box", itemColour)} in the ghost room"
private val freezingInfo = s"The temperature in the ghost room has to be lower than 1 Celsius. You can check for this with the ${textWithColour("thermometer", itemColour)}. You will have to check the temperature multiple times"
private val ghostOrbInfo = s"Ghost can have a floating orb in the ghost room. It is only visible through ${textWithColour("video camera", itemColour)}"
private val ghostWritingInfo = s"While a ${textWithColour("writing book", itemColour)} is in the ghost room the ghost has a chance to write something in it. The book has to be dropped onto the floor first. You can ${textWithColour("inspect writing book", commandColour)} or ${textWithColour("observe", commandColour)} to see if the ghost has written"
private val emf5Info = s"Ghost has a chance to interact with objects in the ghost room which leave behing EMF level (Electromagnetic field). To check for this evidence you can use ${textWithColour("EMF reader", itemColour)} from your inventory or throw it on the ground and listen to it."
private val dotsInfo = s"Ghost can appear as ${textWithColour("DOTS", evidenceColour)} projection in the ghost room if there is ${textWithColour("dots projector", itemColour)} in the ghost room. You can check for this with ${textWithColour("observe", commandColour)}"

val spiritBox = Evidence("spirit box", spiritBoxInfo)
val freezing = Evidence("freezing", freezingInfo)
val ghostOrb = Evidence("ghost orb", ghostOrbInfo)
val ghostWriting = Evidence("ghost writing", ghostWritingInfo)
val emf5 = Evidence("EMF 5", emf5Info)
val dots = Evidence("DOTS", dotsInfo)

val allEvidence = Vector(spiritBox, freezing, ghostOrb, ghostWriting, emf5, dots)