package scheduler;

import java.util.ArrayList;

import debug.Debug;
import debug.Debug.Color;

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

  public void log(String string) {
    Debug.println(Color.BLUE, String.format("[%03ds] %s", currentTime, string));
  }

  public void log(Color color, String string) {
    Debug.println(Color.BLUE, String.format("[%03ds] %s%s", currentTime, color, string));
  }

  // log the waiting queue
  public void printWaitingQueue() {
    var names = String.join(", ", waitingQueue.stream().map(Process::name).toArray(String[]::new));
    var times = String.join(", ",
        waitingQueue.stream().map(p -> String.format("%03ds", p.burstTime())).toArray(String[]::new));
    Debug.println(Color.PURPLE, "Waiting queue: / " + names + " \\");
    Debug.println(Color.PURPLE, "               \\ " + times + " /");
  }

  public void execute() {
    // If there is no current process, get the next process from the waiting queue
    if (currentProcess == null) {
      if (currentTime == 0)
        log("Starting CPU.");
      else
        log("CPU is idle.");

      printWaitingQueue();
      currentProcess = waitingQueue.remove();
      processTimeCounter = 0;
    } else
    // If the current process is done, remove it from the CPU and get the next
    if (currentProcess.isDone()) {
      log(currentProcess.nameAndId() + " is done.");
      printWaitingQueue();
      currentProcess = waitingQueue.remove();
      processTimeCounter = 0;
    } else
    // If the current process was executed 3 times, remove it from the CPU, add it
    // to the waiting queue and get the next process from the waiting queue to the
    // CPU
    if (processTimeCounter == 3) {
      waitingQueue.insert(currentProcess);
      printWaitingQueue();
      currentProcess = waitingQueue.remove();
      processTimeCounter = 0;
    }

    // Execute the current process
    if (currentProcess != null) {
      // If the current process just started, print its message
      if (processTimeCounter == 0) {
        log("Selecting " + currentProcess.nameAndTime());
        log(Color.GREEN, currentProcess.message());
      }

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
