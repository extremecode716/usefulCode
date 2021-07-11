// JVM 최적화로 인해 코드로 시간측정은 정확성이 떨어진다. 그러므로 다른 방법을 사용해야 함
// 충분히 준비동작을 시킬 것.

public class StopWatch {

    private long startTime;
    private long stopTime;

    public static final double NANOS_PER_SEC = 1000000000.0;

    /**
     * start the stop watch.
     */
    public void start() {
        startTime = System.nanoTime();
    }

    /**
     * stop the stop watch.
     */
    public void stop() {
        stopTime = System.nanoTime();
    }

    /**
     * elapsed time in seconds.
     *
     * @return the time recorded on the stopwatch in seconds
     */
    public double time() {
        return (stopTime - startTime) / NANOS_PER_SEC;
    }

    public String toString() {
        return "elapsed time: " + time() + " seconds.";
    }

    /**
     * elapsed time in nanoseconds.
     *
     * @return the time recorded on the stopwatch in nanoseconds
     */
    public long timeInNanoseconds() {
        return (stopTime - startTime);
    }
}
