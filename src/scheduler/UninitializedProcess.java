package scheduler;

public record UninitializedProcess(String name, int arrivalTime, int burstTime) implements MinHeap.Item {
  public Process initialize() {
    return new Process(name, burstTime);
  }

  public String nameAndTime() {
    return name + " (" + burstTime + "s)";
  }

  @Override
  public int priority() {
    return arrivalTime;
  }
}
