/*
 * Created by JFormDesigner on Fri Jan 22 08:55:16 CST 2021
 */

package com.dfm.form;

import com.dfm.main.App;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author unknown
 */
public class M3u8DownloadTool extends JFrame {
    public static int state = 0;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JButton button1;

    public M3u8DownloadTool() {
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
        executorService.execute(() -> {
            try {
                if (state == 0) {
                    App.start(textArea1.getText());
                }else{
                    JOptionPane.showMessageDialog(null,"任务已执行");
                }
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        button1 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {

            //---- textArea1 ----
            textArea1.setText("[\n  {\n    \"name\": \"test\",\n    \"url\": \" https://iqiyi.cdn9-okzy.com/20210124/21387_698f81a5/index.m3u8\",\n    \"path\": \"/media/bluesky/Share/TuringTutorials/test/\",\n    \"key\": null,\n    \"core\": 8,\n    \"tryNum\": 16\n  }\n]");
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 785, 435);

        //---- button1 ----
        button1.setText("\u5f00\u59cb\u89e3\u6790\u4e0b\u8f7d");
        button1.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(635, 440), button1.getPreferredSize()));

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables


}
