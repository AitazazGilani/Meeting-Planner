package com.example.app.database;

public interface TableObject<E> {

    @Override
    public String toString();

    @Override
    public boolean equals(Object o);
}
