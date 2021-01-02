package engine;

@SuppressWarnings("unused")
public interface Callback<T> {
    T poll();
}