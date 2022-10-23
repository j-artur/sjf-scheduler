public class UninitializedProcess implements MinHeap.Item {
  private String name;
  private int arrivalTime;
  private int burstTime;

  public UninitializedProcess(String name, int arrivalTime, int burstTime) {
    this.name = name;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
  }

  public String name() {
    return name;
  }

  public int arrivalTime() {
    return arrivalTime;
  }

  public int burstTime() {
    return burstTime;
  }

  public Process initialize() {
    return new Process(name, burstTime);
  }

  @Override
  public int priority() {
    return arrivalTime;
  }

  @Override
  public String toString() {
    return name + " (" + burstTime + "s)";
  }
}
