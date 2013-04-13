package com.dummy;

import com.dummy.movie.Movie;
import com.dummy.movie.MovieLister;
import com.expressioc.ConfigurationLoader;
import com.expressioc.Container;

import java.io.FileNotFoundException;

public class Demo {
    public static void main(String[] args) throws FileNotFoundException {
        Container parentContainer = ConfigurationLoader.load("config/ParentContainer.ioc");
        Container container = ConfigurationLoader.load("config/ChildContainer.ioc", parentContainer);

        MovieLister movieLister = container.getBean("movieLister", MovieLister.class);

        for (Movie m : movieLister.moviesDirectedBy("directorA")) {
            System.out.println(m);
        }
    }
}
