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
import io.github.mateolegi.Artisan.views.FirstTime;
import io.github.mateolegi.Artisan.views.MainWindow;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mateo Leal
 */
public class Main {
    
    public void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            File file = new File(Preferences.PATH);
            if (file.exists() && !file.isDirectory()) {
                try {
                    if (CheckComponent.checkPHP() && CheckComponent.getPHPModules() && CheckComponent.checkComposer()) {
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

    private Main() {
    }

    public static Main getInstance() {
        return MainHolder.INSTANCE;
    }

    private static class MainHolder {
        private static final Main INSTANCE = new Main();
    }
 }
