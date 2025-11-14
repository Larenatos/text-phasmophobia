package textphasmophobia

def textWithColour(text: String, color: String): String = color + text + Console.RESET

val commandColour = Console.GREEN
val ghostColour = Console.RED
val roomColour = Console.YELLOW
val itemColour = Console.MAGENTA
val temperatureColour = Console.BLUE

def roundToDecimals(value: Float, decimals: Int) =
  1.0 * Math.round(Math.pow(10, decimals) * value) / Math.pow(10, decimals)