package Interfaces;

import java.util.List;
import java.util.UUID;

public interface Store<T> {
    void add(T obj);
    void remove(T obj);
    T getByID(UUID ID);
    List<T> get();
    void serializer();
    void desserializer();
}