package progress.progressbar

import org.joda.time._

final class ProgressBar( task: String, max: Long, updateIntervalMillis: Int, style: ProgressBarStyle ) {

    private val state: ProgressState = new ProgressState(task, max)
    private val target: ProgressThread = new ProgressThread(state, style, updateIntervalMillis)
    private val thread: Thread = new Thread(target)

    def this(task: String, max: Long) {
        this(task, max, 1000, ProgressBarStyle.UNICODE_BLOCK)
    }

    def this(task: String, max: Long, style: ProgressBarStyle) {
        this(task, max, 1000, style)
    }

    def this(task: String, max: Long, updateIntervalMillis: Int) {
        this(task, max, updateIntervalMillis, ProgressBarStyle.UNICODE_BLOCK)
    }

    def start(): ProgressBar = {
        state.init(new DateTime())
        thread.start()
        this
    }

    def step(n: Long = 1): Unit = state.step(n)

    def jump(n: Long): Unit = state.jump(n)

    def stop(): Unit = {
        target.kill()
        try {
            thread.join()
            print('\n')
            Console.flush()
        } catch {
            case ex: InterruptedException => Unit
        }
    }

}
