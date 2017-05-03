import progress.progressbar._

object Test {

    def main(args: Array[String]): Unit = {
        val loop = 10
        val p = new ProgressBar("Loading", loop).start
        for (i <- 0 to loop) {
            Thread.sleep(1000)
            p.step()
        }
        p.stop
    }

}
