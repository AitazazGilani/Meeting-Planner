package com.example.app.database;

public interface TableObject<E> {

    /**
     * Overriden toString method that formats the correct string for the object.
     * @return the formatted string for the object
     */
    @Override
    public String toString();

    /**
     * Overriden equals method to check equality between this object and another.
     * Does not check if ids are equal
     * @param o the object to compare to
     * @return whether the input object is equal to this object
     */
    @Override
    public boolean equals(Object o);
}
