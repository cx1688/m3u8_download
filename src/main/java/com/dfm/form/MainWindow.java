/*
 * Created by JFormDesigner on Sat Jan 30 00:17:04 CST 2021
 */

package com.dfm.form;

import com.dfm.beans.ParamInfo;
import com.dfm.main.App;
import com.dfm.utils.JsonUtils;
import com.dfm.utils.Resolve;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.*;


/**
 * @author unknown
 */
public class MainWindow extends JFrame {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static int taskState;
    public static final java.util.List<ParamInfo>paramInfos = new ArrayList<>();
    public MainWindow() {
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        executorService.execute(() -> {
            try {
                if (taskState == 0) {
                    App.start(textArea1.getText(),textField1.getText());
                }else{
                    JOptionPane.showMessageDialog(null,"任务已执行");
                }
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
        });
    }
    private void listener(){
        while (true){

            try {
                textArea1.setText(JsonUtils.parseJsonString(paramInfos));

                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException | JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    public static int getTaskState() {
        return taskState;
    }

    public static void setTaskState(int taskState) {
        MainWindow.taskState = taskState;
    }

    private void newTask(ActionEvent e) {
        // TODO add your code here
        TaskInfo.getInstance().initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        buttonBar = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();
        okButton = new JButton();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();

        //======== this ========
        setTitle("M3u8Download");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
            ( 0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax. swing. border. TitledBorder. CENTER, javax. swing. border
            . TitledBorder. BOTTOM, new java .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 ), java. awt
            . Color. red) ,dialogPane. getBorder( )) ); dialogPane. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
            propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .equals (e .getPropertyName () )) throw new RuntimeException( )
            ; }} );
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

                //======== scrollPane1 ========
                {

                    //---- textArea1 ----
                    textArea1.setText("[\n  {\n    \"name\": \"test\",\n    \"url\": \" https://iqiyi.cdn9-okzy.com/20210124/21387_698f81a5/index.m3u8\",\n    \"path\": \"/media/bluesky/Share/TuringTutorials/test/\",\n    \"key\": null,\n    \"core\": 8,\n    \"tryNum\": 16\n  }\n]");
                    scrollPane1.setViewportView(textArea1);
                }
                contentPanel.add(scrollPane1);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //======== panel1 ========
                {
                    panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

                    //---- label1 ----
                    label1.setText("\u5916\u90e8Key:");
                    label1.setPreferredSize(new Dimension(75, 20));
                    label1.setHorizontalTextPosition(SwingConstants.LEADING);
                    label1.setHorizontalAlignment(SwingConstants.CENTER);
                    panel1.add(label1);
                    panel1.add(textField1);
                }
                buttonBar.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- button1 ----
                button1.setText("\u65b0\u589e\u4efb\u52a1");
                button1.setPreferredSize(new Dimension(80, 34));
                button1.addActionListener(e -> newTask(e));
                buttonBar.add(button1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- okButton ----
                okButton.setText("\u89e3\u6790\u4e0b\u8f7d");
                okButton.setPreferredSize(new Dimension(80, 34));
                okButton.addActionListener(e -> okButtonActionPerformed(e));
                buttonBar.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== scrollPane2 ========
                {

                    //---- textArea2 ----
                    textArea2.setPreferredSize(new Dimension(1, 100));
                    scrollPane2.setViewportView(textArea2);
                }
                buttonBar.add(scrollPane2, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane);
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
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JButton button1;
    private JButton okButton;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
