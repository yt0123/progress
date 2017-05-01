package progressbar

import org.joda.time._


class ProgressState(val task: String, var max: Long = 0) {

    var indefinite: Boolean = _
    var extraMessage: String = _
    var current: Long = 0
    var startTime: DateTime = _
    private val lock: AnyRef = new AnyRef()

    def setAsDefinite(): Unit = lock.synchronized {
        indefinite = false
    }

    def setAsIndefinite(): Unit = lock.synchronized {
        indefinite = true
    }

    def maxHint(n: Long): Unit = lock.synchronized {
        max = n
    }

    def stepBy(n: Long): Unit = lock.synchronized {
        current += n
        if (current > max) max = current
    }

    def stepTo(n: Long): Unit = lock.synchronized {
        current = n
        if (current > max) max = current
    }

    def setExtraMessage(msg: String): Unit = lock.synchronized {
        extraMessage = msg
    }

    def getTask(): String = { task }

    def getExtraMessage(): String = lock.synchronized { extraMessage }

    def getCurrent(): Long = lock.synchronized { current }

    def getMax(): Long = lock.synchronized { max }

}
