package com.alexrnv.easyconf;


import java.io.IOException;

/**
 * Trial application entry point. Demonstrates configuration engine use case.
 * @author Alex
 */
public class Main {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addModule(new Module("fake"));
        configuration.init();

        for(String arg : args) {
            try {
                configuration.addSource(arg);
            } catch (IOException e) {
                //generally it is better to exit here, but this is just sample app
                System.err.println("Failed to load source " + arg);
            }
        }

        configuration.getAllProperties().stream()
                .forEach(p -> System.out.println(
                                p.getName() + ", " + p.getValue().getClass().getName() + ", " + p.getValue())
                );
    }
}
