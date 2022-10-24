package com.example.app.database;

import java.util.ArrayList;

public interface TableObject<E> {
    public E getObject();

    @Override
    public String toString();
}
