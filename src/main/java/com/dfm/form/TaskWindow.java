package com.dfm.form;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;
//import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.dfm.beans.ParamInfo;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class TaskWindow {

	private JFrame frame;
	private JTextField urlText;
	private JTextField fileNameText;
	private JLabel lblNewLabel_2;
	private JTextField savePathText;
	private JTextField key;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TaskWindow window = new TaskWindow();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	private List<ParamInfo> dataList;
	private Vector<Vector<String>> tableData;
	private DefaultTableModel model;
	private Vector<String> columnData;
	private int count;

	/**
	 * Create the application.
	 */
	public TaskWindow(List<ParamInfo> dataList, Vector<Vector<String>> tableData, DefaultTableModel model,
			Vector<String> columnData) {
		this.dataList = dataList;
		this.tableData = tableData;
		this.model = model;
		this.columnData = columnData;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 511, 141);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Task");
//		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
//		try {
//			BeautyEyeLNFHelper.launchBeautyEyeLNF();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		UIManager.put("RootPane.setupButtonVisible", false);
		JLabel lblNewLabel = new JLabel("M3U8链接:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 12, 80, 20);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("文件名:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(12, 58, 80, 20);
		frame.getContentPane().add(lblNewLabel_1);

		urlText = new JTextField();
		urlText.setBounds(102, 12, 400, 20);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		if(contents!=null){
			if(contents.isDataFlavorSupported(DataFlavor.stringFlavor)){
				try {
					String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
					if(text!=null && text.lastIndexOf(".m3u8")>0){
						urlText.setText(text);
					}
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		frame.getContentPane().add(urlText);
		urlText.setColumns(10);

		fileNameText = new JTextField();
		fileNameText.setBounds(102, 58, 200, 20);
		frame.getContentPane().add(fileNameText);
		fileNameText.setColumns(10);

		lblNewLabel_2 = new JLabel("保存路径：");
		lblNewLabel_2.setBounds(22, 35, 80, 20);
		frame.getContentPane().add(lblNewLabel_2);

		savePathText = new JTextField();
		savePathText.setBounds(102, 35, 300, 20);
		frame.getContentPane().add(savePathText);
		savePathText.setColumns(10);
		savePathText.setText("./");
		JButton btnNewButton = new JButton("选择文件夹");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser("选择目录");
				jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jFileChooser.showOpenDialog(new Label());
				File file = jFileChooser.getSelectedFile();
				savePathText.setText(file == null ? ".\\download" : file.getPath());
			}
		});
		btnNewButton.setBounds(405, 35, 95, 20);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel_1_1 = new JLabel("key:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(303, 58, 45, 20);
		frame.getContentPane().add(lblNewLabel_1_1);

		key = new JTextField();
		key.setColumns(10);
		key.setBounds(349, 59, 153, 20);
		frame.getContentPane().add(key);

		JButton btnNewButton_1 = new JButton("新增");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (StringUtils.isBlank(fileNameText.getText()) || StringUtils.isBlank(urlText.getText())
						|| StringUtils.isBlank(savePathText.getText())) {
					JOptionPane.showMessageDialog(new JLabel(), "文件名、下载链接、保存路径不可为空！", "错误提示",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				ParamInfo paramInfo = new ParamInfo(fileNameText.getText(), urlText.getText(), savePathText.getText(),
						key.getText(), 16, 255);
				dataList.add(paramInfo);
				Vector<String> rowData = new Vector<String>();
				int localtion = setEmptyLocaltion();
				if (localtion > 0) {
					rowData.add((localtion+1) + "");
					rowData.add(paramInfo.getUrl());
					rowData.add(paramInfo.getName().trim().replace("\n",""));
					rowData.add(paramInfo.getPath());
					rowData.add("0%");
					tableData.set(localtion, rowData);
					model.setDataVector(tableData, columnData);
				} else {
					rowData.add((tableData.size() + 1) + "");
					rowData.add(paramInfo.getUrl());
					rowData.add(paramInfo.getName().trim().replace("\n",""));
					rowData.add(paramInfo.getPath());
					rowData.add("0%");
					tableData.add(rowData);
					model.setDataVector(tableData,columnData);
				}
				frame.dispose();
			}

		});
		btnNewButton_1.setBounds(405, 80, 97, 25);
		frame.getContentPane().add(btnNewButton_1);
	}

	/**
	 * 查出表格数据全空的位置
	 * 
	 * @return
	 */
	private int setEmptyLocaltion() {
		// TODO Auto-generated method stub
		int result = 0;
		for (int i = 0; i < tableData.size(); i++) {
			Vector<String> rowData = (Vector) tableData.get(i);
			rowData.stream().forEach(t -> {
				if (StringUtils.isBlank(t)) {
					count++;
				}
				;
			});
			if (count >= 5) {
				result = i;
				break;
			}
		}
		return result;
	}
}
