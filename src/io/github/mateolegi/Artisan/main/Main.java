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
package io.github.mateolegi.Artisan.main;

import io.github.mateolegi.Artisan.controllers.CheckComponent;
import io.github.mateolegi.Artisan.util.Preferences;
import io.github.mateolegi.Artisan.views.InstallComposerProgress;
import io.github.mateolegi.Artisan.views.MainWindow;
import java.awt.EventQueue;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mateo Leal
 */
public class Main {

    public static final MainWindow MAIN_WINDOW = new MainWindow();
    ;

    /**
     * Contains the directory where the aplication is located.
     */
    public static final String APP_DIRECTORY = System.getProperty("user.dir");

    /**
     * Constains an enum with the OS.
     *
     * @see OSType
     */
    public static OSType OS;

    /**
     * Most popular OS names<p>
     * <em>WINDOWS</em>
     * <em>LINUX</em>
     * <em>SOLARIS</em>
     * <em>MACOSX</em>
     * <em>UNKNOWN</em>
     */
    public enum OSType {
        WINDOWS, LINUX, SOLARIS, MACOSX, UNKNOWN
    }

    public void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            File file = new File(Preferences.PATH);
            if (file.exists() && !file.isDirectory()) {
                if (CheckComponent.checkPHP() == CheckComponent.PHP_SUCCESSFUL
                        && CheckComponent.getPHPModules() == CheckComponent.PHP_MODULES_SUCCESSFUL
                        && CheckComponent.checkComposer() == CheckComponent.COMPOSER_SUCCESSFUL) {
                    EventQueue.invokeLater(() -> {
                        new MainWindow().setVisible(true);
                    });
                } else {
                    file.delete();
                    EventQueue.invokeLater(() -> {
                        InstallComposerProgress dialog = new InstallComposerProgress(null, true);
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    });
                }
            } else {
                EventQueue.invokeLater(() -> {
                    InstallComposerProgress dialog = new InstallComposerProgress(null, true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                });
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private Main() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            OS = OSType.WINDOWS;
        } else if (osName.contains("Linux")) {
            OS = OSType.LINUX;
        } else if (osName.contains("osx")) {
            OS = OSType.MACOSX;
        } else if (osName.contains("Solaris")) {
            OS = OSType.SOLARIS;
        } else {
            OS = OSType.UNKNOWN;
        }
    }

    public static Main getInstance() {
        return MainHolder.INSTANCE;
    }

    private static class MainHolder {

        private static final Main INSTANCE = new Main();
    }
}
