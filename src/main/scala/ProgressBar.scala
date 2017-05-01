package progressbar

import org.joda.time._

class ProgressBar(task: String, initialMax: Long, updateIntervalMillis: Int, style: ProgressBarStyle) {

    private val state: ProgressState = new ProgressState(task, initialMax)
    private val target: ProgressThread = new ProgressThread(state, style, updateIntervalMillis)
    private val thread: Thread = new Thread(target)

    def this(task: String, initialMax: Long) {
        this(task, initialMax, 1000, ProgressBarStyle.UNICODE_BLOCK)
    }

    def this(task: String, initialMax: Long, style: ProgressBarStyle) {
        this(task, initialMax, 1000, style)
    }

    def this(task: String, initialMax: Long, updateIntervalMillis: Int) {
        this(task, initialMax, updateIntervalMillis, ProgressBarStyle.UNICODE_BLOCK)
    }


    def start(): ProgressBar = {
        state.startTime = new DateTime()
        thread.start()
        this
    }

    def stepBy(n: Long): ProgressBar = {
        state.stepBy(n)
        this
    }

    def stepTo(n: Long): ProgressBar = {
        state.stepTo(n)
        this
    }

    def step(): ProgressBar = {
        state.stepBy(1)
        this
    }

    def maxHint(n: Long): ProgressBar = {
        if (n < 0) {
            state.setAsIndefinite()
        }
        else {
            state.setAsDefinite()
            state.maxHint(n)
        }
        this
    }

    def stop(): ProgressBar = {
        target.kill()
        try {
            thread.join()
            print('\n')
            Console.flush()
        } catch {
            case ex: InterruptedException => { }
        }
        this
    }

    def setExtraMessage(msg: String): ProgressBar = {
        state.setExtraMessage(msg)
        this
    }

    def getCurrent(): Long = {
        state.getCurrent()
    }

    def getMax(): Long = {
        state.getMax()
    }

    def getTask(): String = {
        state.getTask()
    }

    def getExtraMessage(): String = {
        state.getExtraMessage()
    }

}
