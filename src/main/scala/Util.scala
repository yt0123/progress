package progress.progressbar

import org.joda.time._

object Util {

    def repeat(c: Char, n: Int): String = {
        c match {
            case c if n <= 0 => ""
            case _ =>
                val sb: StringBuilder = new StringBuilder()
                for (i <- 0 until n) sb.append(c)
                sb.toString
        }
    }

    def formatDuration(d: Duration): String = {
        val s: Long = d.getStandardSeconds
        "%d:%02d:%02d".format(s / 3600, (s % 3600) / 60, s % 60);
    }

}
