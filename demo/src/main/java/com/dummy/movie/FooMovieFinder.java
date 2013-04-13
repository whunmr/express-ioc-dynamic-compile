package com.dummy.movie;

import java.util.Arrays;
import java.util.List;

public class FooMovieFinder implements MovieFinder {
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Movie> findAll() {
        return Arrays.asList(new Movie("movieNameA", "directorA"),
                new Movie("movieNameB", "directorA"),
                new Movie("movieNameC", "directorB"));
    }
}
