/*
 * First file execute when starts the program.
 * Contains the comprovations of everything the software needs.
 */
package io.github.mateolegi.Artisan.controllers;

import io.github.mateolegi.Artisan.util.Preferences;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author mateo
 */
public class Main {
    
    static Preferences pref = new Preferences();
    
    public static void checkFirstTime() {
        if ("".equals(pref.getProp("configurations.php-path"))) {
            
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {

        final Process p = Runtime.getRuntime().exec("composer -V");

        new Thread(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            
            try {
                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        p.waitFor();
    }
}
