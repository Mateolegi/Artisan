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

import io.github.mateolegi.Artisan.models.Project;
import io.github.mateolegi.Artisan.util.Notification;
import io.github.mateolegi.Artisan.util.Preferences;
import java.awt.AWTException;
import java.net.MalformedURLException;
import java.util.LinkedList;

/**
 *
 * @author mateo
 */
public class MainWindow extends javax.swing.JFrame {

    Preferences pref = new Preferences();
    Notification noti = new Notification();
    LeftPanel leftPanel = new LeftPanel();
    ProjectsPanel projectsPanel = new ProjectsPanel();
    int xMouse, yMouse;

    public MainWindow() {
        this.setIconImage(new javax.swing.ImageIcon(getClass()
                .getResource("/io/github/mateolegi/Artisan/images/Artisan.png"))
                .getImage().getScaledInstance(48, 48, java.awt.Image.SCALE_AREA_AVERAGING));
        initComponents();
        this.setLocationRelativeTo(null);
        leftPanel.show();
        canvas.add(leftPanel);
        try {
            noti.displayTray("Todo correcto", "INFO");
        } catch (AWTException | MalformedURLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        if (!getProjects().isEmpty()) {
            selectedProject.setText("  " + getProjects().getFirst().getName());
        }
    }

    public LinkedList<Project> getProjects() {
        LinkedList<Project> projects = pref.getProjects();
        if (projects.isEmpty()) {
            System.out.println("No hay proyectos");
            
            currentProjectLabel.setVisible(false);
            selectedProject.setVisible(false);
            topSeparator.setVisible(false);
            logo.setBounds(10, 10, 90, 60);
            
            NewProject newProject = new NewProject();
            canvas.removeAll();
            newProject.show();
            newProject.backButton.setVisible(false);
            canvas.add(newProject);
        } else {
            System.out.println("Hay proyectos");
            logo.setBounds(260, 10, 90, 60);
            currentProjectLabel.setVisible(true);
            selectedProject.setVisible(true);
            topSeparator.setVisible(true);
        }
        return projects;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topBar = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        minimizeButton = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        topSeparator = new javax.swing.JSeparator();
        currentProjectLabel = new javax.swing.JLabel();
        selectedProject = new javax.swing.JToggleButton();
        canvas = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Artisan");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topBar.setBackground(new java.awt.Color(149, 165, 166));
        topBar.setPreferredSize(new java.awt.Dimension(960, 80));
        topBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                topBarMouseDragged(evt);
            }
        });
        topBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                topBarMousePressed(evt);
            }
        });
        topBar.setLayout(null);

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/mateolegi/Artisan/images/close.png"))); // NOI18N
        closeButton.setBorder(null);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusable(false);
        closeButton.setPreferredSize(new java.awt.Dimension(45, 30));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButtonMouseExited(evt);
            }
        });
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        topBar.add(closeButton);
        closeButton.setBounds(915, 0, 45, 30);

        minimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/mateolegi/Artisan/images/minimize.png"))); // NOI18N
        minimizeButton.setBorder(null);
        minimizeButton.setBorderPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setFocusable(false);
        minimizeButton.setPreferredSize(new java.awt.Dimension(45, 30));
        minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeButtonMouseExited(evt);
            }
        });
        minimizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimizeButtonActionPerformed(evt);
            }
        });
        topBar.add(minimizeButton);
        minimizeButton.setBounds(870, 0, 45, 30);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/mateolegi/Artisan/images/Artisan.png"))); // NOI18N
        topBar.add(logo);
        logo.setBounds(260, 10, 90, 60);

        topSeparator.setBackground(new java.awt.Color(127, 140, 141));
        topSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);
        topSeparator.setPreferredSize(new java.awt.Dimension(960, 10));
        topBar.add(topSeparator);
        topSeparator.setBounds(249, 0, 10, 80);

        currentProjectLabel.setFont(new java.awt.Font("Fira Code", 0, 12)); // NOI18N
        currentProjectLabel.setText("Current project");
        topBar.add(currentProjectLabel);
        currentProjectLabel.setBounds(10, 20, 120, 16);

        selectedProject.setFont(new java.awt.Font("Fira Code", 0, 14)); // NOI18N
        selectedProject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/mateolegi/Artisan/images/down.png"))); // NOI18N
        selectedProject.setText("Project");
        selectedProject.setBorder(null);
        selectedProject.setContentAreaFilled(false);
        selectedProject.setFocusable(false);
        selectedProject.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/mateolegi/Artisan/images/up.png"))); // NOI18N
        selectedProject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectedProjectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectedProjectMouseExited(evt);
            }
        });
        selectedProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectedProjectActionPerformed(evt);
            }
        });
        topBar.add(selectedProject);
        selectedProject.setBounds(0, 40, 250, 40);

        getContentPane().add(topBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        canvas.setBackground(new java.awt.Color(236, 240, 241));
        canvas.setPreferredSize(new java.awt.Dimension(960, 580));

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        getContentPane().add(canvas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, -1, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseEntered
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(true);
        closeButton.setBackground(new java.awt.Color(231, 76, 60));
    }//GEN-LAST:event_closeButtonMouseEntered

    private void closeButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseExited
        closeButton.setOpaque(false);
        closeButton.setBorderPainted(false);
    }//GEN-LAST:event_closeButtonMouseExited

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void minimizeButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeButtonMouseEntered
        minimizeButton.setOpaque(true);
        minimizeButton.setBorderPainted(true);
        minimizeButton.setBackground(new java.awt.Color(189, 195, 199));
    }//GEN-LAST:event_minimizeButtonMouseEntered

    private void minimizeButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeButtonMouseExited
        minimizeButton.setOpaque(false);
        minimizeButton.setBorderPainted(false);
    }//GEN-LAST:event_minimizeButtonMouseExited

    private void minimizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeButtonActionPerformed
        this.setState(MainWindow.ICONIFIED);
    }//GEN-LAST:event_minimizeButtonActionPerformed

    private void topBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topBarMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_topBarMouseDragged

    private void topBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topBarMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_topBarMousePressed

    private void selectedProjectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedProjectMouseEntered
        if (selectedProject.isSelected()) {
            selectedProject.setOpaque(true);
            selectedProject.setBorderPainted(true);
            selectedProject.setBackground(new java.awt.Color(149, 165, 166));
        } else {
            selectedProject.setOpaque(true);
            selectedProject.setBorderPainted(true);
            selectedProject.setBackground(new java.awt.Color(127, 140, 141));
        }
    }//GEN-LAST:event_selectedProjectMouseEntered

    private void selectedProjectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectedProjectMouseExited
        if (selectedProject.isSelected()) {
            selectedProject.setBorderPainted(false);
            selectedProject.setBackground(new java.awt.Color(127, 140, 141));
        } else {
            selectedProject.setOpaque(false);
            selectedProject.setBorderPainted(false);
        }
    }//GEN-LAST:event_selectedProjectMouseExited

    private void selectedProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectedProjectActionPerformed
        if (selectedProject.isSelected()) {
            canvas.remove(leftPanel);
            projectsPanel.show();
            canvas.add(projectsPanel);
        } else {
            canvas.remove(projectsPanel);
            leftPanel.show();
            canvas.add(leftPanel);
        }
    }//GEN-LAST:event_selectedProjectActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane canvas;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel currentProjectLabel;
    private javax.swing.JLabel logo;
    private javax.swing.JButton minimizeButton;
    public javax.swing.JToggleButton selectedProject;
    private javax.swing.JPanel topBar;
    private javax.swing.JSeparator topSeparator;
    // End of variables declaration//GEN-END:variables
}
