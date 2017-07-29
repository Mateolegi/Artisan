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
package io.github.mateolegi.Artisan.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

/**
 *
 * @author Mateo Leal
 */
public class Notification {

    public static void main(String[] args) throws AWTException, java.net.MalformedURLException {

    }

    public void displayTray(String text, String type) throws AWTException, java.net.MalformedURLException {

        if (SystemTray.isSupported()) {
            MessageType messageType;
            switch (type) {
                case "ERROR":
                    messageType = MessageType.ERROR;
                    break;
                case "INFO":
                    messageType = MessageType.INFO;
                    break;
                case "WARNING":
                    messageType = MessageType.WARNING;
                    break;
                default:
                    messageType = MessageType.NONE;
                    break;
            }
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            //Image image = Toolkit.getDefaultToolkit().createImage("/io/github/mateolegi/Artisan/images/Artisan.png");
            //Alternative (if the icon is on the classpath):
            Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/io/github/mateolegi/Artisan/images/Artisan.png"));
            TrayIcon trayIcon = new TrayIcon(image, "Artisan");
            //Let the system resizes the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("Artisan");
            tray.add(trayIcon);
            trayIcon.displayMessage("Artisan", text, messageType);
        }
    }
}
