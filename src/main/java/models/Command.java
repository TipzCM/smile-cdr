package models;

public interface Command<T> {
    void exec(T res);
}
