package engine;

public interface Callback<T> {
    T poll();
}