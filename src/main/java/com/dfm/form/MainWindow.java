/*
 * Created by JFormDesigner on Sat Jan 30 00:17:04 CST 2021
 */

package com.dfm.form;

import com.dfm.main.App;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class MainWindow extends JFrame {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static int taskState;
    public MainWindow() {
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        executorService.execute(() -> {
            try {
                if (taskState == 0) {
                    App.start(textArea1.getText());
                }else{
                    JOptionPane.showMessageDialog(null,"任务已执行");
                }
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
        });
    }

    public static int getTaskState() {
        return taskState;
    }

    public static void setTaskState(int taskState) {
        MainWindow.taskState = taskState;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        buttonBar = new JPanel();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();
        okButton = new JButton();

        //======== this ========
        setTitle("M3u8Download");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.
            border.EmptyBorder(0,0,0,0), "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e",javax.swing.border.TitledBorder.CENTER
            ,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dialo\u0067",java.awt.Font
            .BOLD,12),java.awt.Color.red),dialogPane. getBorder()));dialogPane. addPropertyChangeListener(
            new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("borde\u0072"
            .equals(e.getPropertyName()))throw new RuntimeException();}});
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //======== scrollPane1 ========
                {

                    //---- textArea1 ----
                    textArea1.setText("[\n  {\n    \"name\": \"test\",\n    \"url\": \" https://iqiyi.cdn9-okzy.com/20210124/21387_698f81a5/index.m3u8\",\n    \"path\": \"/media/bluesky/Share/TuringTutorials/test/\",\n    \"key\": null,\n    \"core\": 8,\n    \"tryNum\": 16\n  }\n]");
                    scrollPane1.setViewportView(textArea1);
                }
                contentPanel.add(scrollPane1);
                scrollPane1.setBounds(0, 0, 705, 420);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(textArea2);
                }
                buttonBar.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- okButton ----
                okButton.setText("\u89e3\u6790\u4e0b\u8f7d");
                okButton.addActionListener(e -> okButtonActionPerformed(e));
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JPanel buttonBar;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
