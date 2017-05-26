/*
 * First file execute when starts the program.
 * Contains the comprovations of everything the software needs.
 */
package io.github.mateolegi.Artisan.controllers;

import io.github.mateolegi.Artisan.util.xml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author mateo
 */
public class Main 
{
    static xml pref = new xml();

    public static void checkFirstTime() {
        System.out.println(pref.getProp("configurations", "php-path"));
    } 
    static String checkPHP() {
        String version = null;
        try
        {
            final Process p = Runtime.getRuntime().exec("php -v");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = input.readLine();
            String[] phpBuild = line.split(" ");
            version = phpBuild[1];
            float ver = Float.parseFloat(version.substring(0, 2));
            if (ver >= 5.6) {
                System.out.println("Sapbe");
            }
            System.out.println(ver);
            
        } catch (IOException e) {
        }
        return version;
    }

    static void getPHPModules() {
        pref.saveProp("configurations", "php-path", "php");
        LinkedList<String> modules = new LinkedList<>();
        LinkedList<String> modulesRequired = new LinkedList<>();
        try {
            final Process p = Runtime.getRuntime().exec("php -m");

            new Thread(() -> {
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                try {
                    while ((line = input.readLine()) != null) {
                        modules.add(line);
                    }
                    String missingExtensions = "";
                    if (modules.indexOf("openssl") == -1) {
                        missingExtensions += "openssl, ";
                    }
                    if (modules.indexOf("mbstring") == -1) {
                        missingExtensions += "mbstring, ";
                    }
                    if (modules.indexOf("PDO") == -1) {
                        missingExtensions += "PDO, ";
                    }
                    if (modules.indexOf("tokenizer") == -1) {
                        missingExtensions += "tokenizer, ";
                    }
                    if (modules.indexOf("xml") == -1) {
                        missingExtensions += "xml";
                    }
                    if (!missingExtensions.equals("")) {
                        JOptionPane.showMessageDialog(null, "La(s) extensión(es) " + missingExtensions + " no se encuentra(n) activada(s)", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    modules.forEach((module) -> {
                        if (module.equals("") || module.equals("")) {
                            modulesRequired.add(module);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        checkPHP();
        getPHPModules();
    }
}
