package progress.progressbar

import org.joda.time._
import scala.sys.process._

class ProgressThread( val state: ProgressState, val style: ProgressBarStyle, val updateInterval: Long ) extends Runnable {

    @volatile private var running: Boolean = false
    private var length: Int = 0

    def progress(): Double = state.current.toDouble / state.max

    def progressIntegralPart(): Int = (progress * length).toInt

    def progressFractionalPart(): Int = {
        val p: Double = progress * length;
        val fraction: Double = (p - Math.floor(p)) * style.fractionSymbols.length
        Math.floor(fraction).toInt
    }

    def eta(elapsed: Duration): String = {
        state match {
            case s if s.current == 0 => "?"
            case s => Util.formatDuration(elapsed.dividedBy(s.current).multipliedBy(s.max - s.current))
        }
    }

    def percentage(): String = {
        val res: String = Math.floor(100.0 * state.current / state.max).toInt.toString + "%"
        Util.repeat(' ', 4 - res.length) + res
    }

    def ratio(): String = {
        val m: String = state.max.toString
        val c: String = state.current.toString
        Util.repeat(' ', m.length - c.length) + c + "/" + m
    }

    def consoleWidth(): Int = {
        val re = "([0-9]+)".r
        val size: List[String] = re.findAllMatchIn("resize".!!).toList.map(_.toString)
        size(0).toInt
    }

    def refresh(): Unit = {
        print('\r')

        val currentTime: DateTime = new DateTime()
        val elapsed: Duration = new Duration(state.startTime, currentTime)

        val prefix: String = state.task + " " + percentage + " " + style.leftBracket

        val maxSuffixLength: Int = consoleWidth - ProgressThread.consoleRightMargin - prefix.length - 10;
        var suffix: String = style.rightBracket + " " + ratio + " (" + Util.formatDuration(elapsed) + " / " + eta(elapsed) + ") "
        if (suffix.length > maxSuffixLength) suffix = suffix.substring(0, maxSuffixLength)

        length = consoleWidth - ProgressThread.consoleRightMargin - prefix.length - suffix.length

        val sb: StringBuilder = new StringBuilder()
        sb.append(prefix)

        sb.append(Util.repeat(style.block, progressIntegralPart))
        if (state.current < state.max) {
            sb.append(style.fractionSymbols.charAt(progressFractionalPart))
            sb.append(Util.repeat(style.space, length - progressIntegralPart - 1))
        }

        sb.append(suffix)
        val line: String = sb.toString

        print(line)
    }

    def kill(): Unit = {
        running = false
    }

    def run(): Unit = {
        running = true
        try {
            while (running) {
                refresh()
                Thread.sleep(updateInterval)
            }
            refresh()
        } catch {
            case ex: InterruptedException => Unit
        }
    }
}

object ProgressThread {

    val consoleRightMargin: Int = 3

}
