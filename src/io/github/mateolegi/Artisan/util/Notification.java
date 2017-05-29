/*
 * The MIT License
 *
 * Copyright 2017 mateo.
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
package io.github.mateolegi.Artisan.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

/**
 *
 * @author mateo
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
