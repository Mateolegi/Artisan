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

import io.github.mateolegi.Artisan.models.Project;
import io.github.mateolegi.Artisan.util.Notification;
import io.github.mateolegi.Artisan.util.Preferences;
import java.util.LinkedList;

/**
 *
 * @author Mateo Leal
 */
public class MainWindow extends javax.swing.JFrame {

    Preferences pref = new Preferences();
    Notification noti = new Notification();
    LeftPanel leftPanel = new LeftPanel();
    ProjectsPanel projectsPanel = new ProjectsPanel();
    Manager manager = new Manager();
    public Project selectedProject;
    int xMouse, yMouse;

    public MainWindow() {
        this.setIconImage(new javax.swing.ImageIcon(getClass()
                .getResource("/io/github/mateolegi/Artisan/images/Artisan.png"))
                .getImage().getScaledInstance(48, 48, java.awt.Image.SCALE_AREA_AVERAGING));
        initComponents();
        this.setLocationRelativeTo(null);
        leftPanel.show();
        manager.show();
        panelCanvas.add(leftPanel);
        canvas.add(manager);
        if (!getProjects().isEmpty()) {
            selectedProject = getProjects().getFirst();
            projectName.setText("  " + selectedProject.getName());
            
        }
    }

    public LinkedList<Project> getProjects() {
        LinkedList<Project> projects = pref.getProjects();
        if (projects.isEmpty()) {
            currentProjectLabel.setVisible(false);
            projectName.setVisible(false);
            topSeparator.setVisible(false);
            logo.setBounds(10, 10, 90, 60);
            
            NewProject newProject = new NewProject();
            panelCanvas.removeAll();
            newProject.show();
            newProject.backButton.setVisible(false);
            panelCanvas.add(newProject);
        } else {
            logo.setBounds(260, 10, 90, 60);
            currentProjectLabel.setVisible(true);
            projectName.setVisible(true);
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
        projectName = new javax.swing.JToggleButton();
        panelCanvas = new javax.swing.JDesktopPane();
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

        projectName.setFont(new java.awt.Font("Fira Code", 0, 14)); // NOI18N
        projectName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/mateolegi/Artisan/images/down.png"))); // NOI18N
        projectName.setText("Project");
        projectName.setBorder(null);
        projectName.setContentAreaFilled(false);
        projectName.setFocusable(false);
        projectName.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/io/github/mateolegi/Artisan/images/up.png"))); // NOI18N
        projectName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                projectNameMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                projectNameMouseExited(evt);
            }
        });
        projectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectNameActionPerformed(evt);
            }
        });
        topBar.add(projectName);
        projectName.setBounds(0, 40, 250, 40);

        getContentPane().add(topBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelCanvas.setBackground(new java.awt.Color(236, 240, 241));
        panelCanvas.setPreferredSize(new java.awt.Dimension(960, 580));

        javax.swing.GroupLayout panelCanvasLayout = new javax.swing.GroupLayout(panelCanvas);
        panelCanvas.setLayout(panelCanvasLayout);
        panelCanvasLayout.setHorizontalGroup(
            panelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelCanvasLayout.setVerticalGroup(
            panelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        getContentPane().add(panelCanvas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 250, 580));

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        getContentPane().add(canvas, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 710, 580));

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

    private void projectNameMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectNameMouseEntered
        if (projectName.isSelected()) {
            projectName.setOpaque(true);
            projectName.setBorderPainted(true);
            projectName.setBackground(new java.awt.Color(149, 165, 166));
        } else {
            projectName.setOpaque(true);
            projectName.setBorderPainted(true);
            projectName.setBackground(new java.awt.Color(127, 140, 141));
        }
    }//GEN-LAST:event_projectNameMouseEntered

    private void projectNameMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectNameMouseExited
        if (projectName.isSelected()) {
            projectName.setBorderPainted(false);
            projectName.setBackground(new java.awt.Color(127, 140, 141));
        } else {
            projectName.setOpaque(false);
            projectName.setBorderPainted(false);
        }
    }//GEN-LAST:event_projectNameMouseExited

    private void projectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectNameActionPerformed
        if (projectName.isSelected()) {
            panelCanvas.remove(leftPanel);
            projectsPanel.show();
            panelCanvas.add(projectsPanel);
        } else {
            panelCanvas.remove(projectsPanel);
            leftPanel.show();
            panelCanvas.add(leftPanel);
        }
    }//GEN-LAST:event_projectNameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane canvas;
    private javax.swing.JButton closeButton;
    public javax.swing.JLabel currentProjectLabel;
    public javax.swing.JLabel logo;
    private javax.swing.JButton minimizeButton;
    public javax.swing.JDesktopPane panelCanvas;
    public javax.swing.JToggleButton projectName;
    private javax.swing.JPanel topBar;
    public javax.swing.JSeparator topSeparator;
    // End of variables declaration//GEN-END:variables
}
