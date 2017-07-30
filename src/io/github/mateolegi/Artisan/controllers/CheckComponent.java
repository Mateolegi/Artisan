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

import io.github.mateolegi.Artisan.util.Preferences;
import java.io.BufferedReader;
import java.io.File;
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

    private static final Preferences PREFERENCES_FILE = new Preferences();
    private static final String APP_DIRECTORY = System.getProperty("user.dir");

    public static String getPHPPath() {
        return PREFERENCES_FILE.getProp("configurations", "php-path");
    }

    public static String getComposerPath() {
        return PREFERENCES_FILE.getProp("configurations", "composer-path");
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
     * @return <code>true</code> if PHP version is greater or equals than 5.6.4, otherwise <code>false</code>
     * @throws IOException when Artisan fail executing command
     * @throws NumberFormatException when can't format string to numbers to check version
     */
    public static boolean checkPHP() throws IOException, NumberFormatException {

        final Process p = Runtime.getRuntime().exec(getPHPPath() + " -v");
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = in.readLine();
        String version = line.split(" ")[1];
        System.out.println("PHP version: " + version);
        String[] versionN = version.split(".");
        for (String val : versionN) {
            System.out.println(val);
        }
        if (versionPHP(versionN)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "A version greater than 5.6.4 is required", "Update PHP", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Check if PHP extensions requiered to run Laravel are available.<p>
     * Execute <em>php -m</em> to see compiled extensions
     * @return <code>true</code> if every extension requiered is available, otherwise <code></code>
     */
    public static boolean getPHPModules() {

        List<String> modules = new ArrayList<>();
        List<String> requiredModules = new ArrayList<>();
        String[] required = {"openssl", "mbstring", "PDO", "tokenizer", "xml"};
        try {
            Process p = Runtime.getRuntime().exec(getPHPPath() + " -m");
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
                return true;
            } else if (requiredModules.size() == 1) {
                mensaje = "La extensión " + requiredModules.get(0) + " no se encuentra activada";
            } else {
                mensaje = "Las extensiones ";
                for (int i = 0; i < requiredModules.size(); i++) {
                    if (i != requiredModules.size() - 1) {
                        mensaje += requiredModules.get(i) + ", ";
                    } else {
                        mensaje += requiredModules.get(i);
                    }
                }
                mensaje += " no se encuentran activadas";
            }
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException | InterruptedException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return false;
    }

    /**
     * Get Composer version executing <em>php composer.phar -V</em> command.
     * @return <code>true</code> if Composer version is greater than 1.0, otherwise <code>false</code>
     * @throws IOException when Artisan fail executing command
     */
    public static boolean checkComposer() throws IOException {
        String[] composerVersion = {getPHPPath(), getComposerPath(), "-V"};
        ProcessBuilder pb = new ProcessBuilder(composerVersion);
//        pb.directory(new File("C:\\Artisan\\Composer"));
        pb.directory(new File(APP_DIRECTORY + "\\Composer"));
        Process p = pb.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String version = input.readLine().split(" ")[2];
        try {
            boolean flag = Float.parseFloat(version.substring(0, 3)) > 1.0;
            System.out.println(version);
            return flag;
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
