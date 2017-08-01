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

import io.github.mateolegi.Artisan.main.Main;
import io.github.mateolegi.Artisan.util.Preferences;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Mateo Leal
 */
public class CheckComponent {
    
    public static final int PHP_SUCCESSFUL = 1;
    public static final int PHP_UNSOPORTED_VERSION = 0;
    public static final int PHP_NOT_FOUND = -1;
    public static final int PHP_MODULES_SUCCESSFUL = 2;
    public static final int PHP_MODULES_FAIL = -2;
    public static final int COMPOSER_SUCCESSFUL = 1;
    public static final int COMPOSER_NOT_FOUND = -1;

    /**
     * Get the PHP path in the application location
     * @return PHP executable path
     */
    public static String getPHPFile() {
        if (Main.OS == Main.OSType.WINDOWS)
            return Main.APP_DIRECTORY + "\\php\\php.exe";
        return Main.APP_DIRECTORY + "\\php\\php";
    }

    /**
     * Get the composer path in the application location
     * @return composer executable path
     */
    public static String getComposerFile() {
        if (Main.OS == Main.OSType.WINDOWS)
            return Main.APP_DIRECTORY + "\\composer\\composer.bat";
        return Main.APP_DIRECTORY + "\\composer\\composer";
    }

    /**
     * Check PHP version
     * @param version string array with every number of PHP version
     * @return <code>true</code> if PHP version is greater or equals than 5.6.4, otherwise <code>false</code>
     */
    private static boolean versionPHP(String[] version) {
        if (Integer.parseInt(version[0]) > 5) {
            return true;
        } else if (Integer.parseInt(version[0]) == 5) {
            if (Integer.parseInt(version[1]) > 6) {
                return true;
            } else {
                return Integer.parseInt(version[1]) == 6 && Integer.parseInt(version[2]) >= 4;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Get PHP version executing <em>php -v</em> command.
     * @param php
     * @return enum with result
     * @throws NumberFormatException when can't format string to numbers to check version
     */
    public static int checkPHP(String php) {
        try {
            Process p = Runtime.getRuntime().exec(php + " -v");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = in.readLine();
            String version = line.split(" ")[1];
            System.out.println("PHP version: " + version);
            String[] versionN = version.split("\\.");
            if (versionPHP(versionN)) {
                return PHP_SUCCESSFUL;
//                return Integer.parseInt(version);
            } else {
                JOptionPane.showMessageDialog(null, "A version greater than 5.6.4 is required", "Update PHP", JOptionPane.ERROR_MESSAGE);
                return PHP_UNSOPORTED_VERSION;
            }
        } catch (HeadlessException | IOException ex) {
            System.err.println("Error: " + ex.getMessage());
            return PHP_NOT_FOUND;
        }
    }

    /**
     * Get PHP version executing <em>php -v</em> command.
     * @return enum with result
     * @throws NumberFormatException when can't format string to numbers to check version
     */
    public static int checkPHP() {
        return checkPHP(getPHPFile());
    }
    
    /**
     * Check if PHP extensions requiered to run Laravel are available.<p>
     * Execute <em>php -m</em> to see compiled extensions
     * @param php url or command to execute php
     * @return enum with result
     */
    public static int getPHPModules(String php) {
        List<String> modules = new ArrayList<>();
        List<String> requiredModules = new ArrayList<>();
        String[] required = {"openssl", "mbstring", "PDO", "tokenizer", "xml"};
        try {
            Process p = Runtime.getRuntime().exec(php + " -m");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                modules.add(line);
            }
            p.waitFor();
            for (String module : required) {
                if (modules.indexOf(module) == -1) {
                    requiredModules.add(module);
                }
            }
            String mensaje;
            if (requiredModules.isEmpty()) {
                return PHP_MODULES_SUCCESSFUL;
            } else if (requiredModules.size() == 1) {
                mensaje = "The " + requiredModules.get(0) + " extension is not enabled";
            } else {
                mensaje = "";
                for (int i = 0; i < requiredModules.size(); i++) {
                    mensaje += requiredModules.get(i);
                    if (i != requiredModules.size() - 1) mensaje += ", ";
                }
                mensaje += " extensions are not enabled";
            }
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return PHP_MODULES_FAIL;
        } catch (HeadlessException | IOException | InterruptedException ex) {
            System.err.println("Error: " + ex.getMessage());
            return PHP_NOT_FOUND;
        }
    }

    /**
     * Check if PHP extensions requiered to run Laravel are available.<p>
     * Execute <em>php -m</em> to see compiled extensions
     * @return enum with result
     */
    public static int getPHPModules() {
        return getPHPModules(getPHPFile());
    }
    
    /**
     * Get Composer executing <em>php composer.phar -V</em> command.
     * @param php url or command to execute php
     * @param composer url or command to execute composer.phar
     * @return enum with result
     */
    public static int checkComposer(String php, String composer) {
        String[] composerVersion = {php, composer, "-V"};
//        ProcessBuilder pb = new ProcessBuilder(composerVersion);
//        pb.directory(new File("C:\\Artisan\\Composer"));
//        pb.directory(new File(Main.APP_DIRECTORY + "\\Composer"));
        try {
//            Process p = pb.start();
            Process p = Runtime.getRuntime().exec(composerVersion);
            
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String version = input.readLine().split(" ")[2];
            Float.parseFloat(version.substring(0, 3));
            System.out.println(version);
            return COMPOSER_SUCCESSFUL;
//            return Integer.parseInt(version);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return COMPOSER_NOT_FOUND;
        }
    }

    /**
     * Get Composer executing <em>php composer.phar -V</em> command.
     * @return enum with result
     */
    public static int checkComposer() {
        return checkComposer(getPHPFile(), getComposerFile());
    }
}
