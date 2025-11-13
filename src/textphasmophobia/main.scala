package textphasmophobia

import scala.io.StdIn.*
import textphasmophobia.*

object TextPhasmophobiaTextUI extends App:

  val game = Game()

  this.run()

  private def run() =
    println(this.game.welcomeMessage)
    while !this.game.isOver do
      this.playTurn()
  
  private def playTurn() =
    println()
    val command = readLine("Command: ")
    val turnReport = this.game.playTurn(command)
    if turnReport.nonEmpty then
      println(turnReport)

end TextPhasmophobiaTextUI
