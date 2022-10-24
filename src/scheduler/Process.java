package scheduler;

public class Process implements MinHeap.Item {
  private static int nextId = 1;

  private final int id;
  private final String name;
  private int remainingTime;
  private final String message;

  public Process(String name, int burstTime) {
    this.id = nextId++;
    this.name = name;
    this.remainingTime = burstTime;
    this.message = "Hi, I'm process " + name + " (id " + id + ").";
  }

  public int id() {
    return id;
  }

  public String name() {
    return name;
  }

  public int remainingTime() {
    return remainingTime;
  }

  public String message() {
    return message;
  }

  public void tickDown() {
    remainingTime--;
  }

  public boolean isDone() {
    return remainingTime <= 0;
  }

  @Override
  public int key() {
    return remainingTime;
  }

  @Override
  public String toString() {
    return name + " (" + remainingTime + "s)";
  }
}
