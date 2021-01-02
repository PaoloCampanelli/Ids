package it.unicam.cs.ids.c3spa.core.astratto;

public interface ICRUD<T> {
    T GetById(int id);
}
