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
    println(s"Game lasted for ${this.game.getTurnCount} turns!")
  
  private def playTurn() =
    println()
    val command = readLine("Command: ")
    val turnReport = this.game.playTurn(command)
    if turnReport.nonEmpty then
      println("-------------------------------------------------------------")
      println(turnReport)

end TextPhasmophobiaTextUI
