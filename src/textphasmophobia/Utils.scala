package textphasmophobia

def textWithColour(text: String, color: String): String = color + text + Console.RESET

// format for custom colours"\u001b[38;2;${red};${green};${blue}m"
val commandColour = Console.GREEN
val ghostColour = Console.RED
val roomColour = Console.YELLOW
val itemColour = Console.MAGENTA
val temperatureColour = Console.BLUE
val evidenceColour = "\u001b[38;2;236;114;16m"

def roundToDecimals(value: Float, decimals: Int) =
  1.0 * Math.round(Math.pow(10, decimals) * value) / Math.pow(10, decimals)