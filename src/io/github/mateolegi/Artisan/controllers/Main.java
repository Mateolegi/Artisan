/*
 * The MIT License
 *
 * Copyright 2017 mateo.leal.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.mateolegi.Artisan.controllers;

import io.github.mateolegi.Artisan.util.Artisan;
import io.github.mateolegi.Artisan.util.Preferences;
import io.github.mateolegi.Artisan.views.FirstTime;
import io.github.mateolegi.Artisan.views.MainWindow;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author mateo
 */
public class Main {

    Preferences pref = new Preferences();

    static boolean checkPHP() throws HeadlessException, IOException, NumberFormatException {

        final Process p = Runtime.getRuntime().exec(Artisan.PHPVERSION);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = input.readLine();
        String[] phpBuild = line.split(" ");
        String version = phpBuild[1];
        System.out.println(version);
        float ver = Float.parseFloat(version.substring(0, 2));
        if (ver >= 5.6) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "A version greater than 5.6.4 is required", "Update PHP", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    static boolean getPHPModules() {

        LinkedList<String> modules = new LinkedList<>();
        try {
            final Process p = Runtime.getRuntime().exec(Artisan.PHPMODULES);

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
                    return false;
                } else {
                    return true;
                }

            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
        return false;
    }

    static boolean checkComposer() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(Arrays.asList(Artisan.COMPOSERVERSION));
        pb.directory(new File("C:\\Artisan\\Composer"));
        Process p = pb.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = input.readLine();
        String[] composerBuild = line.split(" ");
        try {
            boolean flag = Float.parseFloat(composerBuild[2].substring(0, 3)) > 1.0;
            System.out.println(composerBuild[2]);
            return flag;
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            File file = new File(Preferences.PATH);
            if (file.exists() && !file.isDirectory()) {
                try {
                    if (checkPHP() && getPHPModules() && checkComposer()) {
                        EventQueue.invokeLater(() -> {
                            new MainWindow().setVisible(true);
                        });
                    } else {
                        try {
                            file.delete();
                            EventQueue.invokeLater(() -> {
                                new FirstTime().setVisible(true);
                            });
                        } catch (Exception e) {
                        }
                    }
                } catch (HeadlessException | IOException | NumberFormatException e) {
                    file.delete();
                    EventQueue.invokeLater(() -> {
                        new FirstTime().setVisible(true);
                    });
                }
            } else {
                EventQueue.invokeLater(() -> {
                    new FirstTime().setVisible(true);
                });
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
