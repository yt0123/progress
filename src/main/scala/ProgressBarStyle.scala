package progressbar

class ProgressBarStyle(
    val leftBracket: String,
    val rightBracket: String,
    val block:  Char,
    val space: Char,
    val fractionSymbols: String
)

object ProgressBarStyle {
    /** Use Unicode block characters to draw the progress bar. */
    case object UNICODE_BLOCK extends ProgressBarStyle("│", "│", '█', ' ', " ▏▎▍▌▋▊▉")
    /** Use only ASCII characters to draw the progress bar. */
    case object ASCII extends ProgressBarStyle("[", "]", '=', ' ', ">")
}
