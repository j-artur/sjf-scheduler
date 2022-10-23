import java.util.ArrayList;

public class CPU {
  private MinHeap<Process> waitingQueue;
  private Process currentProcess = null;
  private int processTimeCounter = 0;
  private int currentTime = 0;

  public CPU() {
    waitingQueue = new MinHeap<>(new ArrayList<>());
  }

  // Add a process to the waiting queue
  public void addProcess(Process process) {
    waitingQueue.insert(process);
  }

  public void execute() {
    // If there is no current process, get the next process from the waiting queue
    if (currentProcess == null) {
      currentProcess = waitingQueue.remove();
      System.out
          .println("Time: " + String.format("%03ds", currentTime) + ". CPU was idle, now executing " + currentProcess);
      processTimeCounter = 0;
    } else
    // If the current process is done, remove it from the CPU and get the next
    if (currentProcess.isDone()) {
      System.out.println("Time: " + String.format("%03ds", currentTime) + ". " + currentProcess
          + " is done, now executing " + waitingQueue.peek());
      currentProcess = waitingQueue.remove();
      processTimeCounter = 0;
    } else
    // If the current process was executed 3 times, remove it from the CPU, add it
    // to the waiting queue and get the next process from the waiting queue to the
    // CPU
    if (processTimeCounter == 3) {
      waitingQueue.insert(currentProcess);
      currentProcess = waitingQueue.remove();
      System.out.println("Time: " + String.format("%03ds", currentTime) + ". Now executing " + currentProcess);
      processTimeCounter = 0;
    }

    // Execute the current process
    if (currentProcess != null) {
      // If the current process just started, print its message
      if (processTimeCounter == 0)
        System.out.println(currentProcess.message());

      currentProcess.tick();
      processTimeCounter++;
    }

    currentTime++;
  }

  public int currentTime() {
    return currentTime;
  }

  public MinHeap<Process> waitingQueue() {
    return waitingQueue;
  }

  public boolean isIdle() {
    return currentProcess == null;
  }
}
