package cn.nukkit.utils.queue;

// php....
public interface Queue<T> {

    Queue<T> enqueue(T ele);

    T dequeue();

    boolean isEmpty();
    
}
