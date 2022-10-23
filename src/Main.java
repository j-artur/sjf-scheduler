import java.util.*;

import debug.Debug.Color;
import scheduler.*;
import scheduler.Process;

public class Main {
  static Random random = new Random();

  // Generate a random process burst time between 1 and 20
  static int randomBurstTime() {
    return random.nextInt(20) + 1;
  }

  // Generate a random arrival time between 10 and 14
  static int random10To14() {
    return random.nextInt(5) + 10;
  }

  // Generate a random arrival time between 20 and 24
  static int random20To24() {
    return random.nextInt(5) + 20;
  }

  public static void main(String[] args) {
    // Create a new CPU
    CPU cpu = new CPU();

    // Create a list of uninitialized processes
    List<UninitializedProcess> uninitialized = new ArrayList<>();

    // First batch
    for (int i = 0; i < 4; i++)
      cpu.addProcess(new Process(String.format("p0%02d", i + 1), randomBurstTime()));

    // Second batch arriving between 10 and 14
    for (int i = 0; i < 8; i++)
      uninitialized.add(new UninitializedProcess(String.format("p1%02d", i + 1), random10To14(), randomBurstTime()));

    // Third batch arriving between 20 and 24
    for (int i = 0; i < 16; i++)
      uninitialized.add(new UninitializedProcess(String.format("p2%02d", i + 1), random20To24(), randomBurstTime()));

    // Sort the uninitialized processes into a priority queue by arrival time
    MinHeap<UninitializedProcess> queue = new MinHeap<>(uninitialized);
    queue.heapify();

    cpu.printWaitingQueue();

    // Execute the CPU until all processes are done, adding new processes to the CPU
    // when they arrive
    while (!cpu.isIdle() || !queue.isEmpty()) {
      boolean added = false;
      while (!queue.isEmpty() && queue.peek().arrivalTime() == cpu.currentTime()) {
        // Get the next process from the queue, log it and add it to the CPU
        UninitializedProcess process = queue.remove();
        cpu.log(Color.BLUE_BRIGHT, "New process: " + process.nameAndTime());
        cpu.addProcess(process.initialize());
        added = true;
      }
      // If a process was added, print the waiting queue
      if (added)
        cpu.printWaitingQueue();

      cpu.execute();

      // Sleep for 1 second
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    cpu.log("All processes are done!");
  }

}
