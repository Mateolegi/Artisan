/*
 * Artisan - GUI for Laravel developers.
 * Copyright (C) 2017  Mateo Leal.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.mateolegi.Artisan.controllers;

import io.github.mateolegi.Artisan.util.Deprecated;
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
 * @author Mateo Leal
 */
public class MainOld {

    Preferences pref = new Preferences();

    static boolean checkPHP() throws HeadlessException, IOException, NumberFormatException {

        final Process p = Runtime.getRuntime().exec("php -v");
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = in.readLine();
        String version = line.split(" ")[1];
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
            final Process p = Runtime.getRuntime().exec(Deprecated.PHPMODULES);

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
        ProcessBuilder pb = new ProcessBuilder(Arrays.asList(Deprecated.COMPOSERVERSION));
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
