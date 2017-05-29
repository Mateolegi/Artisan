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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextArea;

/**
 *
 * @author mateo
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
