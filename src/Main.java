import java.util.*;

// Write code in functional style

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
    // Create a CPU
    CPU cpu = new CPU();

    // Add 4 processes with arrival time 0 and random burst time
    for (int i = 0; i < 4; i++) {
      cpu.addProcess(new Process(String.format("p0%02d", i + 1), randomBurstTime()));
    }

    // Create a list of uninitialized processes
    List<UninitializedProcess> uninitialized = new ArrayList<>();

    // Add 8 uninitialized processes with arrival time from 10 to 14 and random
    // burst time
    for (int i = 0; i < 8; i++) {
      uninitialized.add(new UninitializedProcess(String.format("p1%02d", i + 1), random10To14(), randomBurstTime()));
    }

    // Add 16 uninitialized processes with arrival time from 20 to 24 and random
    // burst time
    for (int i = 0; i < 16; i++) {
      uninitialized.add(new UninitializedProcess(String.format("p2%02d", i + 1), random20To24(), randomBurstTime()));
    }

    // Create a priority queue of uninitialized processes (sorted by arrival time)
    MinHeap<UninitializedProcess> queue = new MinHeap<>(uninitialized);
    queue.heapify();

    // Execute the CPU until all processes are done, adding new processes to the CPU
    // when they arrive
    while (!cpu.isIdle() || !queue.isEmpty()) {
      boolean added = false;
      while (!queue.isEmpty() && queue.peek().arrivalTime() == cpu.currentTime()) {
        // Get the next process from the queue, print it and add it to the CPU
        UninitializedProcess process = queue.remove();
        System.out.println("+ New process: " + process);
        cpu.addProcess(process.initialize());
        added = true;
      }
      if (added) {
        // Print the waiting queue
        var names = String.join(", ", cpu.waitingQueue().stream().map(p -> p.name()).toArray(String[]::new));
        var times = String.join(", ",
            cpu.waitingQueue().stream().map(p -> String.format("%03ds", p.burstTime())).toArray(String[]::new));
        System.out.println("- Waiting queue: [" + names + "]");
        System.out.println("                 [" + times + "]");
      }

      cpu.execute();

      // // Print the uninitialized processes
      // System.out.println("Uninitialized processes: " + queue);

      // Sleep for 1 second
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.out.println("All processes are done!");
  }

}
