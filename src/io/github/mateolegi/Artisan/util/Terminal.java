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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextArea;

/**
 *
 * @author Mateo Leal
 */
public class Terminal {
    
    Process p;
    
    public void executeCommand(String command, JTextArea textArea) {
        try {
            try {
                p = Runtime.getRuntime().exec(command);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                p = Runtime.getRuntime().exec("cmd /c " + command);
            }
            InputStream output = p.getInputStream();
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(output));
            String reading = in.readLine();
            while (reading != null) {
                System.out.println(reading);
                if (textArea != null) textArea.append(reading + "\n");
                reading = in.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            if (textArea != null) textArea.setText("Error: " + e.getMessage());
        }
    }
    
    public void executeCommand(String[] command, JTextArea textArea) {
        try {
            try {
                p = Runtime.getRuntime().exec(command);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                p = Runtime.getRuntime().exec("cmd /c " + command);
            }
            InputStream output = p.getInputStream();
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(output));
            String reading = in.readLine();
            while (reading != null) {
                System.out.println(reading);
                if (textArea != null) textArea.append(reading + "\n");
                reading = in.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            if (textArea != null) textArea.setText("Error: " + e.getMessage());
        }
    }
    
    public BufferedReader executeCommand(String command) {
        try {
            try {
                p = Runtime.getRuntime().exec(command);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                p = Runtime.getRuntime().exec("cmd /c " + command);
            }
            InputStream output = p.getInputStream();
            return new BufferedReader(new InputStreamReader(output));
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    public BufferedReader executeCommand(String[] command) {
        try {
            try {
                p = Runtime.getRuntime().exec(command);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                p = Runtime.getRuntime().exec("cmd /c " + command);
            }
            InputStream output = p.getInputStream();
            return new BufferedReader(new InputStreamReader(output));
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
}
