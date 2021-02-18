/*
 * Created by JFormDesigner on Thu Feb 04 23:06:00 CST 2021
 */

package com.dfm.form;

import com.dfm.beans.ParamInfo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author unknown
 */
public class TaskInfo extends JFrame {
    private static final TaskInfo INSTANCE = new TaskInfo();
    private TaskInfo() {
//        initComponents();
    }
    public static TaskInfo getInstance(){
        return INSTANCE;
    }
    private void addTask(ActionEvent e) {
        // TODO add your code here
        MainWindow.paramInfos.add(new ParamInfo(nameText.getText(),Url_text.getText(),path_text.getText(),key.getText(),Integer.parseInt(core.getText()),Integer.parseInt(tryNum.getText())));
    }

    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        dialogPane = new JPanel();
        panel1 = new JPanel();
        label1 = new JLabel();
        Url_text = new JTextField();
        label2 = new JLabel();
        path_text = new JTextField();
        label3 = new JLabel();
        nameText = new JTextField();
        label4 = new JLabel();
        core = new JTextField();
        tryNum = new JTextField();
        label5 = new JLabel();
        key = new JTextField();
        label6 = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setName("Frame");
        setVisible(true);
        setTitle("Task");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder(
            0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder
            . BOTTOM, new java .awt .Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt. Color.
            red) ,dialogPane. getBorder( )) ); dialogPane. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .
            beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
            dialogPane.setLayout(new BorderLayout());

            //======== panel1 ========
            {
                panel1.setLayout(null);

                //---- label1 ----
                label1.setText("Url:");
                label1.setPreferredSize(new Dimension(20, 20));
                label1.setHorizontalTextPosition(SwingConstants.CENTER);
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                panel1.add(label1);
                label1.setBounds(20, 15, 75, 30);
                panel1.add(Url_text);
                Url_text.setBounds(100, 15, 545, Url_text.getPreferredSize().height);

                //---- label2 ----
                label2.setText("Path:");
                label2.setPreferredSize(new Dimension(20, 20));
                label2.setHorizontalTextPosition(SwingConstants.CENTER);
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                panel1.add(label2);
                label2.setBounds(20, 50, 75, 30);
                panel1.add(path_text);
                path_text.setBounds(100, 50, 545, 30);

                //---- label3 ----
                label3.setText("Name:");
                label3.setPreferredSize(new Dimension(20, 20));
                label3.setHorizontalTextPosition(SwingConstants.CENTER);
                label3.setHorizontalAlignment(SwingConstants.CENTER);
                panel1.add(label3);
                label3.setBounds(20, 85, 75, 30);
                panel1.add(nameText);
                nameText.setBounds(100, 85, 545, 30);

                //---- label4 ----
                label4.setText("Core:");
                label4.setPreferredSize(new Dimension(20, 20));
                label4.setHorizontalTextPosition(SwingConstants.CENTER);
                label4.setHorizontalAlignment(SwingConstants.CENTER);
                panel1.add(label4);
                label4.setBounds(20, 120, 75, 30);

                //---- core ----
                core.setText("8");
                panel1.add(core);
                core.setBounds(100, 120, 235, 30);

                //---- tryNum ----
                tryNum.setText("16");
                panel1.add(tryNum);
                tryNum.setBounds(420, 120, 225, 30);

                //---- label5 ----
                label5.setText("TryNum:");
                label5.setPreferredSize(new Dimension(20, 20));
                label5.setHorizontalTextPosition(SwingConstants.CENTER);
                label5.setHorizontalAlignment(SwingConstants.CENTER);
                panel1.add(label5);
                label5.setBounds(340, 120, 75, 30);
                panel1.add(key);
                key.setBounds(100, 155, 545, 30);

                //---- label6 ----
                label6.setText("Key:");
                label6.setPreferredSize(new Dimension(20, 20));
                label6.setHorizontalTextPosition(SwingConstants.CENTER);
                label6.setHorizontalAlignment(SwingConstants.CENTER);
                panel1.add(label6);
                label6.setBounds(20, 155, 75, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(panel1, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("Add");
                okButton.addActionListener(e -> addTask(e));
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
    private JPanel panel1;
    private JLabel label1;
    private JTextField Url_text;
    private JLabel label2;
    private JTextField path_text;
    private JLabel label3;
    private JTextField nameText;
    private JLabel label4;
    private JTextField core;
    private JTextField tryNum;
    private JLabel label5;
    private JTextField key;
    private JLabel label6;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
