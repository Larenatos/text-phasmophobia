package textphasmophobia

def textWithColour(text: String, color: String): String = color + text + Console.RESET
val commandColour = Console.GREEN
val ghostColour = Console.RED
val roomColour = Console.YELLOW