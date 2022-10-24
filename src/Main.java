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

    // First batch directly added to the CPU
    for (int i = 0; i < 4; i++)
      cpu.addProcess(new Process(String.format("p0%02d", i + 1), randomBurstTime()));

    // Create a list of uninitialized processes
    List<UninitializedProcess> uninitialized = new ArrayList<>();

    // Second batch arriving between 10s and 14s
    for (int i = 0; i < 8; i++)
      uninitialized.add(new UninitializedProcess(String.format("p1%02d", i + 1), random10To14(), randomBurstTime()));

    // Third batch arriving between 20s and 24s
    for (int i = 0; i < 16; i++)
      uninitialized.add(new UninitializedProcess(String.format("p2%02d", i + 1), random20To24(), randomBurstTime()));

    // Put second and third batches into a priority queue sorted by arrival time
    MinHeap<UninitializedProcess> queue = new MinHeap<>(uninitialized);
    queue.heapify();

    // Print the waiting queue before starting the CPU
    cpu.printWaitingQueue();

    // Execute the CPU until all processes are done, adding new ones when it's time
    while (!cpu.isIdle() || !queue.isEmpty()) {
      while (!queue.isEmpty() && queue.peek().arrivalTime() == cpu.currentTime()) {
        // Get the next process from the queue, log it and add it to the CPU
        Process process = queue.remove().initialize();
        cpu.log(Color.BLUE_BRIGHT, "New process: " + process);
        cpu.addProcess(process);
      }

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
