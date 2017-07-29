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
package io.github.mateolegi.Artisan.views;

import io.github.mateolegi.Artisan.util.Preferences;
import io.github.mateolegi.Artisan.util.Terminal;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Mateo Leal
 */
public class FirstTime extends javax.swing.JFrame {

    Preferences pref = new Preferences();
    Terminal terminal = new Terminal();
    FileNameExtensionFilter exeFilter = new FileNameExtensionFilter("Executable .exe", "exe");
    FileNameExtensionFilter pharFilter = new FileNameExtensionFilter("PHAR File .phar", "phar");

    public FirstTime() {
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass()
                .getResource("/io/github/mateolegi/Artisan/images/Artisan.png"))
                .getImage().getScaledInstance(48, 48, java.awt.Image.SCALE_AREA_AVERAGING));
        setLocationRelativeTo(null);
        DefaultCaret caret = (DefaultCaret) checkingLabel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        //check();
    }

    private boolean check(String php, String composer) {
        if (checkPHP(php) && checkPHPModules(php) && checkComposer(php, composer)) {
            checkingLabel.append("Status: successful");
            return true;
        } else {
            checkingLabel.append("Status: error");
            return false;
        }
    }

    private boolean checkPHP(String php) {
        boolean flag;
        try {
            ProcessBuilder pb = new ProcessBuilder(php, "-v");
            Process p = pb.start();
            InputStream output = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(output));
            String reading = in.readLine();
            System.out.println(reading);
            String[] phpBuild = reading.split(" ");
            String version = phpBuild[1];
            float ver = Float.parseFloat(version.substring(0, 2));
            checkingLabel.setText("PHP: version " + version + "\n");
            if (ver >= 5.6) {
                flag = true;
            } else {
                checkingLabel.append("PHP: A version greater than 5.6.4 is required\n");
                flag = false;
            }
        } catch (NumberFormatException | IOException ex) {
            checkingLabel.setText("PHP: Could not find command\n");
            flag = false;
        }
        return flag;
    }

    private boolean checkPHPModules(String php) {

        try {

            LinkedList<String> modules = new LinkedList<>();
            ProcessBuilder pb = new ProcessBuilder(php, "-m");
            Process p = pb.start();
            InputStream output = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(output));
            String line;
            try {
                while ((line = in.readLine()) != null) {
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
                    missingExtensions += "xml ";
                }
                if (!missingExtensions.equals("")) {
                    checkingLabel.append("PHP: Extensions " + missingExtensions + "are not enabled\n");
                    return false;
                } else {
                    return true;
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                return false;
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    private boolean checkComposer(String php, String composer) {
        try {
            ProcessBuilder pb = new ProcessBuilder(php, composer, "-V");
            Process p = pb.start();
            InputStream output = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(output));
            String line = in.readLine();
            System.out.println(line);
            String[] composerBuild = line.split(" ");
            try {
                boolean flag = Float.parseFloat(composerBuild[2].substring(0, 3)) > 1.0;
                checkingLabel.append("Composer: version " + composerBuild[2] + "\n");
                return flag;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        } catch (IOException | NullPointerException ex) {
            checkingLabel.append("Composer: Could not find command\n");
            return false;
        }
    }

    private void installLocalComposer(String php) {
        String[] installComposerWindows = {
            "@ECHO OFF",
            "REM Set Home folder as Composer Global Configuration Folder",
            "SET COMPOSER_HOME=%~dp0Home",
            "if not exist %COMPOSER_HOME% md \"%COMPOSER_HOME%\"",
            php + " -r \"readfile('https://getcomposer.org/installer');\" | " + php,
            "SET COMPOSER_BAT=%~dp0composer.bat",
            "if not exist \"%COMPOSER_BAT\" (",
            "echo @ECHO OFF> \"%COMPOSER_BAT%\"",
            "echo SET COMPOSER_HOME=%%~dp0Home>> \"%COMPOSER_BAT%\"",
            "echo if not exist %%COMPOSER_HOME%% md \"%%COMPOSER_HOME%%\">> \"%COMPOSER_BAT%\"",
            "echo " + php + " \"%%~dp0composer.phar\" %%*>> \"%COMPOSER_BAT%\"",
            "echo EXIT /B %%ERRORLEVEL%%>> \"%COMPOSER_BAT%\"",
            ")",
            "call composer --version | findstr /i /r /c:\"Composer......version\"",
            "REM Increases Composer Timeout",
            "call composer --quiet config --global process-timeout 3000",
            "REM Set Local folder for Composer Internal Cache",
            "SET COMPOSER_LOCAL=%~dp0Local",
            "if not exist %COMPOSER_LOCAL% md \"%COMPOSER_LOCAL%\"",
            "call composer --quiet config --global cache-dir \"%COMPOSER_LOCAL%\""
        };
        try {
            Process p;
            ProcessBuilder pb;
            BufferedReader in;
            String line;
            for (int i = 0; i < 20; i++) {
                pb = new ProcessBuilder(Arrays.asList(new String[]{"cmd.exe", "/C", installComposerWindows[i]}));
                pb.directory(new File("C:\\Artisan\\Composer"));
                p = pb.start();
                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = in.readLine()) != null) {
                    checkingLabel.append(line + "\n");
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
        phpGroup = new javax.swing.ButtonGroup();
        composerGroup = new javax.swing.ButtonGroup();
        phpPathText = new javax.swing.JTextField();
        composerPathText = new javax.swing.JTextField();
        phpPathLabel = new javax.swing.JLabel();
        composerPathLabel = new javax.swing.JLabel();
        selectPHPButton = new javax.swing.JButton();
        selectComposerButton = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        checkingLabel = new javax.swing.JTextArea();
        titleLabel = new javax.swing.JLabel();
        phpInstalledLabel = new javax.swing.JLabel();
        yesPHPButton = new javax.swing.JRadioButton();
        noPHPButton = new javax.swing.JRadioButton();
        composerInstalledLabel = new javax.swing.JLabel();
        yesComposerButton = new javax.swing.JRadioButton();
        noComposerButton = new javax.swing.JRadioButton();

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setApproveButtonText("Open");
        fileChooser.setApproveButtonToolTipText("");
        fileChooser.setCurrentDirectory(new java.io.File("C:\\"));

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Welcome to Artisan");
            setResizable(false);

            phpPathText.setText("php");
            phpPathText.setEnabled(false);
            phpPathText.setPreferredSize(new java.awt.Dimension(26, 20));
            phpPathText.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    phpPathTextKeyPressed(evt);
                }
            });

            composerPathText.setText("composer.phar");
            composerPathText.setEnabled(false);
            composerPathText.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    composerPathTextKeyPressed(evt);
                }
            });

            phpPathLabel.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            phpPathLabel.setText("PHP path");
            phpPathLabel.setEnabled(false);

            composerPathLabel.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            composerPathLabel.setText("Composer path");
            composerPathLabel.setEnabled(false);

            selectPHPButton.setBackground(new java.awt.Color(149, 165, 166));
            selectPHPButton.setText("Search");
            selectPHPButton.setBorder(null);
            selectPHPButton.setBorderPainted(false);
            selectPHPButton.setContentAreaFilled(false);
            selectPHPButton.setEnabled(false);
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
            selectComposerButton.setText("Search");
            selectComposerButton.setBorder(null);
            selectComposerButton.setBorderPainted(false);
            selectComposerButton.setContentAreaFilled(false);
            selectComposerButton.setEnabled(false);
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
            confirmButton.setText("Continue");
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

            scrollPane.setBackground(new java.awt.Color(255, 255, 255));
            scrollPane.setFocusable(false);
            scrollPane.setOpaque(false);

            checkingLabel.setEditable(false);
            checkingLabel.setBackground(new java.awt.Color(51, 51, 51));
            checkingLabel.setColumns(20);
            checkingLabel.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
            checkingLabel.setForeground(new java.awt.Color(240, 240, 240));
            checkingLabel.setRows(5);
            scrollPane.setViewportView(checkingLabel);

            titleLabel.setFont(new java.awt.Font("Fira Code", 0, 18)); // NOI18N
            titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            titleLabel.setText("First time configuration");

            phpInstalledLabel.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            phpInstalledLabel.setText("Do you have PHP installed?");

            phpGroup.add(yesPHPButton);
            yesPHPButton.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            yesPHPButton.setText("Yes");
            yesPHPButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    yesPHPButtonActionPerformed(evt);
                }
            });

            phpGroup.add(noPHPButton);
            noPHPButton.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            noPHPButton.setSelected(true);
            noPHPButton.setText("No");
            noPHPButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    noPHPButtonActionPerformed(evt);
                }
            });

            composerInstalledLabel.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            composerInstalledLabel.setText("Do you have Composer installed?");
            composerInstalledLabel.setEnabled(false);

            composerGroup.add(yesComposerButton);
            yesComposerButton.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            yesComposerButton.setText("Yes");
            yesComposerButton.setEnabled(false);
            yesComposerButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    yesComposerButtonActionPerformed(evt);
                }
            });

            composerGroup.add(noComposerButton);
            noComposerButton.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
            noComposerButton.setSelected(true);
            noComposerButton.setText("No");
            noComposerButton.setEnabled(false);
            noComposerButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    noComposerButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(phpPathLabel)
                                        .addComponent(composerPathLabel))
                                    .addGap(27, 27, 27)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(phpPathText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(composerPathText, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(selectPHPButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(selectComposerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(phpInstalledLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(yesPHPButton)
                                    .addGap(39, 39, 39)
                                    .addComponent(noPHPButton)
                                    .addGap(0, 278, Short.MAX_VALUE))
                                .addComponent(scrollPane))
                            .addGap(16, 16, 16))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(composerInstalledLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(yesComposerButton)
                            .addGap(39, 39, 39)
                            .addComponent(noComposerButton)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(274, 274, 274))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(titleLabel)
                    .addGap(37, 37, 37)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(phpInstalledLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yesPHPButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(noPHPButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(phpPathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(phpPathText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectPHPButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(composerInstalledLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(yesComposerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(noComposerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                    .addGap(12, 12, 12)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(selectComposerButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(composerPathText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(composerPathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE))
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
        fileChooser.removeChoosableFileFilter(pharFilter);
        if (System.getProperty("os.name").startsWith("Windows")) {
            fileChooser.setFileFilter(exeFilter);
        }
        fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();

        try {
            String path = file.getAbsolutePath();
            phpPathText.setText(path);
            checkingLabel.setText("");
            checkPHP(path);
            checkPHPModules(path);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_selectPHPButtonActionPerformed

    private void selectComposerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectComposerButtonActionPerformed
        fileChooser.removeChoosableFileFilter(exeFilter);
        fileChooser.setFileFilter(pharFilter);
        fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();
        try {
            String path = file.getAbsolutePath();
            composerPathText.setText(path);
            checkingLabel.setText("");
            check(phpPathText.getText(), path);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_selectComposerButtonActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        confirmButton.setEnabled(false);
        if (yesPHPButton.isSelected()) {
            if (yesComposerButton.isSelected()) {
                if (check(phpPathText.getText(), composerPathText.getText())) {
                    pref.saveProp("configurations", "php-path", phpPathText.getText());
                    pref.saveProp("configurations", "composer-path", composerPathText.getText());
                    EventQueue.invokeLater(() -> {
                        new MainWindow().setVisible(true);
                    });
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Status: error", "Error", JOptionPane.ERROR_MESSAGE);
                    confirmButton.setEnabled(true);
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            } else {
                if (checkPHP(phpPathText.getText()) && checkPHPModules(phpPathText.getText())) {
                    installLocalComposer(phpPathText.getText());
                    String composerPath = "C:\\Artisan\\Composer\\composer.phar";
                    try {
                        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", phpPathText.getText(), composerPath, "-V");
                        Process p = pb.start();
                        String[] composerBuild = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine().split(" ");
                        try {
                            Float.parseFloat(composerBuild[2].substring(0, 3));
                            checkingLabel.append("Composer: version " + composerBuild[2] + "\n");
                            pref.saveProp("configurations", "php-path", phpPathText.getText());
                            pref.saveProp("configurations", "composer-path", composerPath);
                            EventQueue.invokeLater(() -> {
                                new MainWindow().setVisible(true);
                            });
                            dispose();
                        } catch (NumberFormatException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
        } else {
            final String php = "C:\\Artisan\\php\\php.exe";
            if (checkPHP(php) && checkPHPModules(php)) {
                File composerPath = new File("C:\\Artisan\\Composer");
                final String composer = "C:\\Artisan\\Composer\\composer.phar";
                if (!composerPath.exists()) {
                    composerPath.mkdirs();
                }
                installLocalComposer(php);
                if (checkComposer(php, composer)) {
                    pref.saveProp("configurations", "php-path", php);
                    pref.saveProp("configurations", "composer-path", composer);
                    dispose();
                    new MainWindow().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "An error has occurred with the Composer check, please inform the developer.\nmateolegi@gmail.com", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(this, "An error has occurred with the PHP check, please inform the developer.\nmateolegi@gmail.com", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        confirmButton.setEnabled(true);
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

    private void yesPHPButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesPHPButtonActionPerformed
        phpPathLabel.setEnabled(true);
        phpPathText.setEnabled(true);
        selectPHPButton.setEnabled(true);
        if (checkPHP(phpPathText.getText())) {
            checkPHPModules(phpPathText.getText());
        }
        composerInstalledLabel.setEnabled(true);
        yesComposerButton.setEnabled(true);
        noComposerButton.setEnabled(true);
        if (yesComposerButton.isSelected()) {
            yesComposerButtonActionPerformed(evt);
        }
    }//GEN-LAST:event_yesPHPButtonActionPerformed

    private void noPHPButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noPHPButtonActionPerformed
        phpPathLabel.setEnabled(false);
        phpPathText.setEnabled(false);
        selectPHPButton.setEnabled(false);
        composerInstalledLabel.setEnabled(false);
        yesComposerButton.setEnabled(false);
        noComposerButton.setEnabled(false);
        composerPathLabel.setEnabled(false);
        composerPathText.setEnabled(false);
        checkingLabel.setText("");
    }//GEN-LAST:event_noPHPButtonActionPerformed

    private void yesComposerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesComposerButtonActionPerformed
        composerPathLabel.setEnabled(true);
        composerPathText.setEnabled(true);
        selectComposerButton.setEnabled(true);
        check(phpPathText.getText(), composerPathText.getText());
    }//GEN-LAST:event_yesComposerButtonActionPerformed

    private void noComposerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noComposerButtonActionPerformed
        composerPathLabel.setEnabled(false);
        composerPathText.setEnabled(false);
        selectComposerButton.setEnabled(false);
    }//GEN-LAST:event_noComposerButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea checkingLabel;
    private javax.swing.ButtonGroup composerGroup;
    private javax.swing.JLabel composerInstalledLabel;
    private javax.swing.JLabel composerPathLabel;
    private javax.swing.JTextField composerPathText;
    private javax.swing.JButton confirmButton;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JRadioButton noComposerButton;
    private javax.swing.JRadioButton noPHPButton;
    private javax.swing.ButtonGroup phpGroup;
    private javax.swing.JLabel phpInstalledLabel;
    private javax.swing.JLabel phpPathLabel;
    private javax.swing.JTextField phpPathText;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton selectComposerButton;
    private javax.swing.JButton selectPHPButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JRadioButton yesComposerButton;
    private javax.swing.JRadioButton yesPHPButton;
    // End of variables declaration//GEN-END:variables
}
