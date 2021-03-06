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

import io.github.mateolegi.Artisan.controllers.CheckComponent;
import io.github.mateolegi.Artisan.main.Main;
import io.github.mateolegi.Artisan.util.Hash;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author Mateo Leal
 */
public class InstallComposerProgress extends javax.swing.JDialog {

    /**
     * Creates new form CreateNewProjectProgress
     *
     * @param owner the {@code Frame} from which the dialog is displayed
     * @param modal specifies whether dialog blocks user input to other
     * top-level windows when shown. If {@code true}, the modality type property
     * is set to {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is
     * modeless.
     */
    public InstallComposerProgress(java.awt.Frame owner, boolean modal) {
        super(owner, modal);
        this.setIconImage(new javax.swing.ImageIcon(getClass()
                .getResource("/io/github/mateolegi/Artisan/images/Artisan.png"))
                .getImage().getScaledInstance(48, 48, java.awt.Image.SCALE_AREA_AVERAGING));
        initComponents();
        InstallComposer p = new InstallComposer(progressBar, secondLabel);
        p.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if (evt.getPropertyName().equalsIgnoreCase("state")) {
                if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                    this.dispose();
                    System.exit(0);
                }
            }
        });
        p.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressBar = new javax.swing.JProgressBar();
        firstLabel = new javax.swing.JLabel();
        secondLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Configuring Artisan");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N

        firstLabel.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
        firstLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        firstLabel.setText("Configuring Artisan, this may take a while.");

        secondLabel.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
        secondLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        secondLabel.setText("Please, be pacient");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(secondLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(firstLabel)
                .addGap(13, 13, 13)
                .addComponent(secondLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel firstLabel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel secondLabel;
    // End of variables declaration//GEN-END:variables
}

class InstallComposer extends SwingWorker<Integer, String> {

    private final JProgressBar progressBar;
    private final JLabel label;
    private final String[] envp = {"COMPOSER_HOME=Home", "COMPOSER_BAT=composer.bat", "COMPOSER_LOCAL=Local"};
    private String php;
    private File composerDirectory;

    public InstallComposer(JProgressBar progressBar, JLabel label) {
        this.progressBar = progressBar;
        this.label = label;
    }

    private void installLaravel() {
        try {
            Process laravelInstall = Runtime.getRuntime().exec("cmd /c " + php + " composer.phar global require \"laravel/installer\"", envp, composerDirectory);
            laravelInstall.waitFor();
            BufferedReader bric = new BufferedReader(new InputStreamReader(laravelInstall.getInputStream()));
            String result;
            while ((result = bric.readLine()) != null) {
                System.out.println(result);
            }
//            label.setText("Proyecto de prueba");
//            Process laravel = Runtime.getRuntime().exec("laravel new test", envp, composerDirectory);
//            laravel.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(InstallComposer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Integer doInBackground() {
        progressBar.setIndeterminate(true);
        php = CheckComponent.getPHPFile();
        if (php.contains(" ")) {
            php = "\"" + php + "\"";
        }
        label.setText("Checking PHP");
        if (CheckComponent.checkPHP() == CheckComponent.PHP_SUCCESSFUL) {
            label.setText("Checking PHP modules");
            if (CheckComponent.getPHPModules() == CheckComponent.PHP_MODULES_SUCCESSFUL) {
                try {
                    composerDirectory = new File(Main.APP_DIRECTORY + "\\composer");
                    if (!composerDirectory.isDirectory()) {
                        composerDirectory.delete();
                    }
                    if (!composerDirectory.mkdir()) {
                        if (!composerDirectory.mkdirs()) {
                            System.err.println("Can't create folder");
                        }
                    }
                    label.setText("Downloading Composer installer signature");
                    ReadableByteChannel in = Channels.newChannel(new URL("https://composer.github.io/installer.sig").openStream());
                    FileChannel out = new FileOutputStream(Main.APP_DIRECTORY + "\\composer\\installer.sig").getChannel();
                    out.transferFrom(in, 0, Long.MAX_VALUE);
                    File signature = new File(Main.APP_DIRECTORY + "\\composer\\installer.sig");
                    BufferedReader br = new BufferedReader(new FileReader(signature));
                    String expectedHash = br.readLine();
                    label.setText("Downloading Composer installer");
                    Process downloadInstallerProcess = Runtime.getRuntime().exec(php + " -r \"copy('https://getcomposer.org/installer', 'composer-setup.php');\"", envp, composerDirectory);
                    downloadInstallerProcess.waitFor();
                    File installer = new File(composerDirectory.getAbsoluteFile() + "\\composer-setup.php");
                    String obtainedHash = Hash.getHash(installer);
                    label.setText("Checking checksum");
                    if (expectedHash.equalsIgnoreCase(obtainedHash)) {
                        label.setText("Installing Composer");
                        Process installComposer = Runtime.getRuntime().exec(php + " composer-setup.php --quiet", envp, composerDirectory);
                        String result;
                        installComposer.waitFor();
                        installComposer = Runtime.getRuntime().exec(php + " composer.phar --version", envp, composerDirectory);
                        BufferedReader bric = new BufferedReader(new InputStreamReader(installComposer.getInputStream()));
                        while ((result = bric.readLine()) != null) {
                            label.setText(result);
                        }
                        installComposer = Runtime.getRuntime().exec(php + " composer.phar --quiet config --global process-timeout 3000", envp, composerDirectory);
                        installComposer.waitFor();
                        if (new File(composerDirectory.getAbsoluteFile() + "\\Local").mkdir()) {
                            installComposer = Runtime.getRuntime().exec(php + " composer.phar --quiet config --global cache-dir \"Local\"", envp, composerDirectory);
                            installComposer.waitFor();
                        }
                        Thread.sleep(500);
                        label.setText("Deleting signature");
                        if (!signature.delete()) {
                            Runtime.getRuntime().exec("cmd /c del installer.sig", null, composerDirectory);
                        }
                        Thread.sleep(500);
                        label.setText("Deleting installer");
                        if (!installer.delete()) {
                            Runtime.getRuntime().exec("cmd /c del composer-setup.php", null, composerDirectory);
                        }
                        label.setText("Installing Laravel");
                        installLaravel();
                    } else {
                        JOptionPane.showMessageDialog(null, "An error ocurred downloading Composer installer", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(InstallComposer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(InstallComposer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        progressBar.setIndeterminate(false);
        return 0;
    }
}
