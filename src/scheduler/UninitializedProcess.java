package scheduler;

public record UninitializedProcess(String name, int arrivalTime, int burstTime) implements MinHeap.Item {
  public Process initialize() {
    return new Process(name, burstTime);
  }

  @Override
  public int key() {
    return arrivalTime;
  }
}
