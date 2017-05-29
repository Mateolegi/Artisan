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
package io.github.mateolegi.Artisan.views;

import io.github.mateolegi.Artisan.util.Preferences;
import io.github.mateolegi.Artisan.util.Terminal;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author mateo
 */
public class FirstTime extends javax.swing.JFrame {

    Preferences pref = new Preferences();
    Terminal terminal = new Terminal();

    public FirstTime() {
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass()
                .getResource("/io/github/mateolegi/Artisan/images/Artisan.png"))
                .getImage().getScaledInstance(48, 48, java.awt.Image.SCALE_AREA_AVERAGING));
        setLocationRelativeTo(null);
        check();
    }

    private boolean check() {
        boolean flag;
        String label;
        if (checkPHP()) {
            if (getPHPModules()) {
                installLaravel();
                flag = checkComposer();
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        label = checkingLabel.getText();
        if (flag) {
            label += "Status: successful";
        } else {
            label += "Status: error";
        }
        checkingLabel.setText(label);
        return flag;
    }

    private boolean checkPHP() {
        boolean flag;
        String label;
        try {
            String[] arr = {phpPathText.getText(), "-v"};
            final Process p = Runtime.getRuntime().exec(arr);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = input.readLine();
            String[] phpBuild = line.split(" ");
            String version = phpBuild[1];
            label = "PHP: version " + version + "\n";
            float ver = Float.parseFloat(version.substring(0, 2));
            if (ver >= 5.6) {
                flag = true;
            } else {
                label += "PHP: A version greater than 5.6.4 is required\n";
                flag = false;
            }
        } catch (IOException | NumberFormatException ex) {
            label = "PHP: Could not find command\n";
            flag = false;
        }
        checkingLabel.setText(label);
        return flag;
    }

    private boolean getPHPModules() {

        try {
            
            LinkedList<String> modules = new LinkedList<>();
            String[] arr = {phpPathText.getText(), "-m"};
            final Process p = Runtime.getRuntime().exec(arr);
            
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
                    String label = checkingLabel.getText();
                    label += "PHP: Extensons " + missingExtensions + " are not enabled\n";
                    checkingLabel.setText(label);
                    return false;
                } else {
                    return true;
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            return false;
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    private boolean checkComposer() {
        String label = checkingLabel.getText();
        boolean flag;
        try {
            String[] arr = {composerPathText.getText(), "-V"};
            final Process p = Runtime.getRuntime().exec(arr);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = input.readLine();
            String[] composerBuild = line.split(" ");
            label += "Composer: version " + composerBuild[2] + "\n";
            flag = true;
        } catch (IOException | NullPointerException ex) {
            label += "Composer: Could not find command\n";
            flag = false;
        }
        checkingLabel.setText(label);
        return flag;
    }

    private void installLaravel() {
        String[] command = {composerPathText.getText(), "global", "require", "\"laravel/installer\""};
        terminal.executeCommand(command, checkingLabel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        panel = new javax.swing.JPanel();
        phpPathText = new javax.swing.JTextField();
        composerPathText = new javax.swing.JTextField();
        phpPathLabel = new javax.swing.JLabel();
        composerPathLabel = new javax.swing.JLabel();
        selectPHPButton = new javax.swing.JButton();
        selectComposerButton = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        checkingLabel = new javax.swing.JTextArea();

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setApproveButtonText("Open");
        fileChooser.setApproveButtonToolTipText("");
        fileChooser.setCurrentDirectory(new java.io.File("C:\\"));

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Welcome to Artisan");
            setResizable(false);

            panel.setBackground(new java.awt.Color(236, 240, 241));

            phpPathText.setText("php");
            phpPathText.setPreferredSize(new java.awt.Dimension(26, 20));
            phpPathText.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    phpPathTextKeyPressed(evt);
                }
            });

            composerPathText.setText("composer");
            composerPathText.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    composerPathTextKeyPressed(evt);
                }
            });

            phpPathLabel.setText("PHP path");

            composerPathLabel.setText("Composer path");

            selectPHPButton.setBackground(new java.awt.Color(149, 165, 166));
            selectPHPButton.setText("Examinar");
            selectPHPButton.setBorder(null);
            selectPHPButton.setBorderPainted(false);
            selectPHPButton.setContentAreaFilled(false);
            selectPHPButton.setOpaque(true);
            selectPHPButton.setPreferredSize(new java.awt.Dimension(45, 30));
            selectPHPButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    selectPHPButtonMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    selectPHPButtonMouseExited(evt);
                }
            });
            selectPHPButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    selectPHPButtonActionPerformed(evt);
                }
            });

            selectComposerButton.setBackground(new java.awt.Color(149, 165, 166));
            selectComposerButton.setText("Examinar");
            selectComposerButton.setBorder(null);
            selectComposerButton.setBorderPainted(false);
            selectComposerButton.setContentAreaFilled(false);
            selectComposerButton.setOpaque(true);
            selectComposerButton.setPreferredSize(new java.awt.Dimension(45, 30));
            selectComposerButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    selectComposerButtonMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    selectComposerButtonMouseExited(evt);
                }
            });
            selectComposerButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    selectComposerButtonActionPerformed(evt);
                }
            });

            confirmButton.setBackground(new java.awt.Color(149, 165, 166));
            confirmButton.setText("Continuar");
            confirmButton.setBorder(null);
            confirmButton.setBorderPainted(false);
            confirmButton.setContentAreaFilled(false);
            confirmButton.setOpaque(true);
            confirmButton.setPreferredSize(new java.awt.Dimension(45, 30));
            confirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    confirmButtonMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    confirmButtonMouseExited(evt);
                }
            });
            confirmButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    confirmButtonActionPerformed(evt);
                }
            });

            jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
            jScrollPane1.setFocusable(false);
            jScrollPane1.setOpaque(false);

            checkingLabel.setEditable(false);
            checkingLabel.setColumns(20);
            checkingLabel.setRows(5);
            checkingLabel.setOpaque(false);
            jScrollPane1.setViewportView(checkingLabel);

            javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(137, 137, 137)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap(21, Short.MAX_VALUE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createSequentialGroup()
                                    .addComponent(composerPathLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(composerPathText, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createSequentialGroup()
                                    .addComponent(phpPathLabel)
                                    .addGap(46, 46, 46)
                                    .addComponent(phpPathText, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(selectComposerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(selectPHPButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(23, Short.MAX_VALUE))
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(phpPathLabel)
                        .addComponent(phpPathText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectPHPButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(composerPathLabel)
                        .addComponent(composerPathText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectComposerButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(14, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void selectPHPButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectPHPButtonMouseEntered
        selectPHPButton.setBackground(new java.awt.Color(127, 140, 141));
    }//GEN-LAST:event_selectPHPButtonMouseEntered

    private void selectPHPButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectPHPButtonMouseExited
        selectPHPButton.setBackground(new java.awt.Color(149, 165, 166));
    }//GEN-LAST:event_selectPHPButtonMouseExited

    private void selectComposerButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectComposerButtonMouseExited
        selectComposerButton.setBackground(new java.awt.Color(149, 165, 166));
    }//GEN-LAST:event_selectComposerButtonMouseExited

    private void selectComposerButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectComposerButtonMouseEntered
        selectComposerButton.setBackground(new java.awt.Color(127, 140, 141));
    }//GEN-LAST:event_selectComposerButtonMouseEntered

    private void confirmButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmButtonMouseEntered
        confirmButton.setBackground(new java.awt.Color(127, 140, 141));
    }//GEN-LAST:event_confirmButtonMouseEntered

    private void confirmButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmButtonMouseExited
        confirmButton.setBackground(new java.awt.Color(149, 165, 166));
    }//GEN-LAST:event_confirmButtonMouseExited

    private void selectPHPButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectPHPButtonActionPerformed
        fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();
        try {
            String path = file.getAbsolutePath();
            phpPathText.setText(path);
            checkingLabel.setText("");
            check();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_selectPHPButtonActionPerformed

    private void selectComposerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectComposerButtonActionPerformed
        fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();
        try {
            String path = file.getAbsolutePath();
            composerPathText.setText(path);
            checkingLabel.setText("");
            check();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_selectComposerButtonActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        confirmButton.setEnabled(false);
        if (check()) {
            pref.saveProp("configurations", "php-path", phpPathText.getText());
            pref.saveProp("configurations", "composer-path", composerPathText.getText());
            setVisible(false);
            new MainWindow().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Status: error", "Error", JOptionPane.ERROR_MESSAGE);
            confirmButton.setEnabled(true);
        }
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void phpPathTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phpPathTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            confirmButtonActionPerformed(null);
        }
    }//GEN-LAST:event_phpPathTextKeyPressed

    private void composerPathTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_composerPathTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            confirmButtonActionPerformed(null);
        }
    }//GEN-LAST:event_composerPathTextKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea checkingLabel;
    private javax.swing.JLabel composerPathLabel;
    private javax.swing.JTextField composerPathText;
    private javax.swing.JButton confirmButton;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel phpPathLabel;
    private javax.swing.JTextField phpPathText;
    private javax.swing.JButton selectComposerButton;
    private javax.swing.JButton selectPHPButton;
    // End of variables declaration//GEN-END:variables
}
