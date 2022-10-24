package scheduler;

import java.util.List;
import java.util.stream.Stream;

public class MinHeap<T extends MinHeap.Item> {
  public interface Item {
    int key();
  }

  private List<T> heap;

  public MinHeap(List<T> list) {
    heap = list;
  }

  private void moveUp(int i) {
    int j = (i - 1) / 2;

    if (j >= 0 && heap.get(i).key() < heap.get(j).key()) {
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
      if (heap.get(j).key() > heap.get(j + 1).key())
        j++;
    }

    if (heap.get(j).key() < heap.get(i).key()) {
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

  public Stream<T> stream() {
    return heap.stream();
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }
}
