package textphasmophobia

import scala.collection.mutable.Map

class Player:

  private var currentLocation = "Truck"
  var hasQuit = false

  def location = this.currentLocation

  def go(newRoom: String) =
    this.currentLocation = newRoom
    s"Players current location is: ${newRoom}"

  def quit =
    this.hasQuit = true
    "Player has quit the game"

end Player


