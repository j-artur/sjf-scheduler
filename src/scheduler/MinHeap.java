package scheduler;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MinHeap<T extends MinHeap.Item> {
  public interface Item {
    int priority();
  }

  private List<T> heap;

  public MinHeap(List<T> list) {
    this.heap = list;
  }

  private void moveUp(int i) {
    int j = (i - 1) / 2;

    if (j >= 0 && heap.get(i).priority() < heap.get(j).priority()) {
      T temp = heap.get(i);
      heap.set(i, heap.get(j));
      heap.set(j, temp);

      moveUp(j);
    }
  }

  private void moveDown(int i) {
    int j = 2 * i + 1;

    if (j >= heap.size())
      return;

    if (j < heap.size() - 1) {
      if (heap.get(j).priority() > heap.get(j + 1).priority())
        j++;
    }

    if (heap.get(j).priority() < heap.get(i).priority()) {
      T temp = heap.get(i);
      heap.set(i, heap.get(j));
      heap.set(j, temp);

      moveDown(j);
    }
  }

  public void heapify() {
    for (int i = (heap.size() - 1) / 2; i >= 0; i--) {
      moveDown(i);
    }
  }

  public void insert(T it) {
    if (heap.isEmpty()) {
      heap.add(it);
    } else {
      heap.add(it);
      moveUp(heap.size() - 1);
    }
  }

  public T remove() {
    if (heap.isEmpty())
      return null;

    T it = heap.get(0);
    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);

    moveDown(0);

    return it;
  }

  public T peek() {
    if (heap.isEmpty())
      return null;

    return heap.get(0);
  }

  public void forEach(Consumer<T> consumer) {
    heap.forEach(consumer);
  }

  public Stream<T> stream() {
    return heap.stream();
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  @Override
  public String toString() {
    return heap.toString();
  }
}