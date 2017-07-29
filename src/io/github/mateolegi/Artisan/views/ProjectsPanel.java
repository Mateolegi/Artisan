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
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Mateo Leal
 */
public class ProjectsPanel extends javax.swing.JInternalFrame {

    Preferences pref = new Preferences();
    javax.swing.JInternalFrame projectsPanel = this;
    LinkedList<javax.swing.JButton> projectsList = new LinkedList<>();
    int y = 120;

    /**
     * Creates new form ProjectPanel
     */
    public ProjectsPanel() {
        initComponents();
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        newProjectButton.setOpaque(true);
        loadProjects();
    }

    public void loadProjects() {
        projectsList.forEach((button) -> {
            projectsPanel.remove(button);
            this.invalidate();
            this.validate();
            this.repaint();
        });
        projectsList.clear();
        y = 120;
        pref.getProjects().stream().map((project) -> {
            javax.swing.JButton projectButton = new javax.swing.JButton();
            projectButton.setText(project.getName());
            getContentPane().add(projectButton);
            projectButton.setBounds(10, y, 230, 40);
            projectButton.setBackground(new java.awt.Color(149, 165, 166));
            projectButton.setContentAreaFilled(false);
            projectButton.setOpaque(true);
            projectButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    projectButton.setBackground(new java.awt.Color(189, 195, 199));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    projectButton.setBackground(new java.awt.Color(149, 165, 166));
                }
            });
            projectButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                MainWindow topFrame = (MainWindow) (JFrame) SwingUtilities.getWindowAncestor(projectsPanel);
                topFrame.selectedProject = project;
                topFrame.projectName.setText(project.getName());
                topFrame.leftPanel.show();
                topFrame.panelCanvas.add(topFrame.leftPanel);
                topFrame.projectName.setSelected(false);
                topFrame.projectName.setOpaque(false);
                dispose();
            });
            projectsList.add(projectButton);
            return project;
        }).forEachOrdered((_item) -> {
            y += 45;
        });
    }

    public void loadProjects(String search) {
        projectsList.forEach((button) -> {
            projectsPanel.remove(button);
            this.invalidate();
            this.validate();
            this.repaint();
        });
        projectsList.clear();
        y = 120;
        pref.getProjects().stream().filter((project) -> (project.getName().toLowerCase().contains(search.toLowerCase()))).map((project) -> {
            javax.swing.JButton projectButton = new javax.swing.JButton();
            projectButton.setText(project.getName());
            getContentPane().add(projectButton);
            projectButton.setBounds(10, y, 230, 40);
            projectButton.setBackground(new java.awt.Color(149, 165, 166));
            projectButton.setContentAreaFilled(false);
            projectButton.setOpaque(true);
            projectButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    projectButton.setBackground(new java.awt.Color(189, 195, 199));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    projectButton.setBackground(new java.awt.Color(149, 165, 166));
                }
            });
            projectButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                System.out.println(projectButton.getName());
                MainWindow topFrame = (MainWindow) (JFrame) SwingUtilities.getWindowAncestor(projectsPanel);
                topFrame.selectedProject = project;
                topFrame.projectName.setText(project.getName());
                topFrame.leftPanel.show();
                topFrame.panelCanvas.add(topFrame.leftPanel);
                topFrame.projectName.setSelected(false);
                topFrame.projectName.setOpaque(false);
                dispose();
            });
            return project;
        }).forEachOrdered((_item) -> {
            y += 45;
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchTextBox = new javax.swing.JTextField();
        newProjectButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(127, 140, 141));
        setBorder(null);
        setPreferredSize(new java.awt.Dimension(250, 580));
        getContentPane().setLayout(null);

        searchTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTextBoxKeyTyped(evt);
            }
        });
        getContentPane().add(searchTextBox);
        searchTextBox.setBounds(10, 11, 230, 30);

        newProjectButton.setBackground(new java.awt.Color(149, 165, 166));
        newProjectButton.setText("New Project");
        newProjectButton.setContentAreaFilled(false);
        newProjectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newProjectButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newProjectButtonMouseExited(evt);
            }
        });
        newProjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectButtonActionPerformed(evt);
            }
        });
        getContentPane().add(newProjectButton);
        newProjectButton.setBounds(10, 60, 230, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newProjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectButtonActionPerformed
        MainWindow topFrame = (MainWindow) (JFrame) SwingUtilities.getWindowAncestor(this);
        //topFrame.currentProjectLabel.setVisible(false);
        //topFrame.projectName.setVisible(false);
        //topFrame.topSeparator.setVisible(false);
        //topFrame.logo.setBounds(10, 10, 90, 60);

        NewProjectPanel newProject = new NewProjectPanel();
        //topFrame.getCanvas().removeAll();
        //newProject.show();
        if (topFrame.manager.getTabbedPane().indexOfTab("New project") == -1) {
            topFrame.manager.getTabbedPane().addTab("New project", newProject);
        } else {
            topFrame.manager.getTabbedPane().setSelectedIndex(topFrame.manager.getTabbedPane().indexOfTab("New project"));
        }
        //this.dispose();
    }//GEN-LAST:event_newProjectButtonActionPerformed

    private void newProjectButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newProjectButtonMouseEntered
        newProjectButton.setBackground(new java.awt.Color(189, 195, 199));
    }//GEN-LAST:event_newProjectButtonMouseEntered

    private void newProjectButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newProjectButtonMouseExited
        newProjectButton.setBackground(new java.awt.Color(149, 165, 166));
    }//GEN-LAST:event_newProjectButtonMouseExited

    private void searchTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextBoxKeyTyped
        if (searchTextBox.getText().equals("") || searchTextBox.getText() == null) {
            loadProjects();
        } else {
            loadProjects(searchTextBox.getText());
        }
    }//GEN-LAST:event_searchTextBoxKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton newProjectButton;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
