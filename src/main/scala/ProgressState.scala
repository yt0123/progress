package progress.progressbar

import org.joda.time._

case class ProgressState( val task: String, val max: Long ) {
    var current: Long = 0
    val startTime: DateTime = new DateTime()
    private val lock: AnyRef = new AnyRef()

    require(max > 0)

    def init(dt: DateTime): Unit = lock.synchronized { startTime.plus(new Duration(dt, startTime)) }

    def step(n: Long): Unit = lock.synchronized {
        current += n
        if (current > max) current = max
    }

    def jump(n: Long): Unit = lock.synchronized {
        current = n
        if (current > max) current = max
    }

    def getTask(): String = lock.synchronized { task }

    def getMax(): Long = lock.synchronized { max }

    def getCurrent(): Long = lock.synchronized { current }

    def getStartTime(): DateTime = lock.synchronized { startTime }
}
