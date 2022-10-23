public class Process implements MinHeap.Item {
  private static int nextId = 0;

  private final int id;
  private final String name;
  private final int burstTime;
  private int remainingTime;
  private final String message;

  public Process(String name, int burstTime) {
    this.id = ++nextId;
    this.name = name;
    this.burstTime = burstTime;
    this.remainingTime = burstTime;
    this.message = "Hello, I'm " + name + " (id " + id + ")!";
  }

  public int id() {
    return id;
  }

  public String name() {
    return name;
  }

  public int burstTime() {
    return burstTime;
  }

  public int remainingTime() {
    return remainingTime;
  }

  public String message() {
    return message;
  }

  public void tick() {
    remainingTime--;
  }

  public boolean isDone() {
    return remainingTime <= 0;
  }

  @Override
  public int priority() {
    return remainingTime;
  }

  @Override
  public String toString() {
    return name + " (" + remainingTime + "s)";
  }
}
