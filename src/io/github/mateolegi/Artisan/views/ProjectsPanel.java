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
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author mateo
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
                topFrame.canvas.add(topFrame.leftPanel);
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
                topFrame.canvas.add(topFrame.leftPanel);
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
        topFrame.currentProjectLabel.setVisible(false);
        topFrame.projectName.setVisible(false);
        topFrame.topSeparator.setVisible(false);
        topFrame.logo.setBounds(10, 10, 90, 60);

        NewProject newProject = new NewProject();
        //topFrame.getCanvas().removeAll();
        newProject.show();
        topFrame.canvas.add(newProject);
        this.dispose();
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
