package com.dfm.form;

import com.dfm.beans.ParamInfo;
import com.dfm.main.Download;
import com.dfm.utils.JsonUtils;
import com.dfm.utils.ThreadFacotryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NewWindow {

    private JFrame frmMudownload;
    private JTable table;
    private JPopupMenu jPopupMenu;
    private final Vector<String> coulm = new Vector<>();
    private final Vector<Vector<String>> tableData = new Vector<>();
    private List<ParamInfo> dataList = new LinkedList<>();
    DefaultTableModel model = new DefaultTableModel();
    private final String paramFile = "./data.json";
    private final ExecutorService executorService = Executors.newFixedThreadPool(5,
            new ThreadFacotryImpl("ScheduledTaskThread", new ThreadGroup("MainTask")));
    private JTextArea textArea;
    private JScrollPane scrollPane_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NewWindow window = new NewWindow();
                    window.frmMudownload.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public NewWindow() {
        // {"序号" , "链接" , "名称","路径","进度"};
        coulm.add("序号");
        coulm.add("链接");
        coulm.add("名称");
        coulm.add("路径");
        coulm.add("进度");
        initialize();

        model.setDataVector(tableData, coulm);
        File file = new File(paramFile);
        if (file.exists()) {
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(file.toPath());
                dataList = JsonUtils.parseJsonList(new String(bytes, StandardCharsets.UTF_8).replace("\n", ""), LinkedList.class,
                        ParamInfo.class);
                setEmptyTableData(dataList.size());
                dataList.stream().forEach(t -> {
                    Vector<String> rowData = new Vector<String>();
                    int localtion = setEmptyLocaltion();
                    if (localtion > 0) {
                        rowData.add((localtion + 1) + "");
                        rowData.add(t.getUrl());
                        rowData.add(t.getName().trim());
                        rowData.add(t.getPath());
                        rowData.add(t.getProgress() + "%");
                        tableData.set(localtion, rowData);
                        model.setDataVector(tableData, coulm);
                    } else {
                        rowData.add((tableData.size() + 1) + "");
                        rowData.add(t.getUrl());
                        rowData.add(t.getName().trim());
                        rowData.add(t.getPath());
                        rowData.add(t.getProgress() + "%");
                        tableData.add(rowData);
                        model.setDataVector(tableData, coulm);
                    }
                });
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            setEmptyTableData(32);
        }
    }

    private void setEmptyTableData(int count) {
        // TODO Auto-generated method stub
        for (int i = 0; i < count - 1; i++) {
            tableData.add(new Vector<>());
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmMudownload = new JFrame();
        frmMudownload.setResizable(false);
        frmMudownload.setTitle("M3u8Download");
        frmMudownload.setBounds(100, 100, 924, 520);
        frmMudownload.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMudownload.getContentPane().setLayout(null);
//		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
//		try {
//			BeautyEyeLNFHelper.launchBeautyEyeLNF();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 12, 900, 329);
        frmMudownload.getContentPane().add(scrollPane);
        createPopupMenu();
        table = new JTable(model);
        scrollPane.setViewportView(table);

        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(12, 345, 900, 133);
        frmMudownload.getContentPane().add(scrollPane_1);

        textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        scrollPane_1.setViewportView(textArea);
        textArea.setLineWrap(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int selectRowIndex = table.rowAtPoint(e.getPoint());
                    if (selectRowIndex == -1) {
                        return;
                    }
                    table.setRowSelectionInterval(selectRowIndex, selectRowIndex);
                    jPopupMenu.show(table, e.getX(), e.getY());
                }

            }

        });
        Thread thread = new Thread(() -> {
            startTask();
        });
        thread.start();
    }

    private void createPopupMenu() {
        jPopupMenu = new JPopupMenu();
        JMenuItem newItem = new JMenuItem("新增");
//		JMenuItem editItem = new JMenuItem("编辑");
        JMenuItem delItem = new JMenuItem("删除");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                new TaskWindow(dataList, tableData, model, coulm);

            }
        });
//		editItem.addActionListener((e) -> {
//
//		});

        delItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            Vector<String> object = tableData.get(selectedRow);
            removeDataList(object);
            tableData.remove(selectedRow);
            model.setDataVector(tableData, coulm);
        });
        jPopupMenu.add(newItem);
//		jPopupMenu.add(editItem);
        jPopupMenu.add(delItem);
    }

    private void removeDataList(Vector<String> object) {
        // TODO Auto-generated method stub
        dataList.removeIf(t -> t.getName().equals(object.get(2)) && t.getUrl().equals(object.get(1)));
        try {
            String content = JsonUtils.parseJsonString(dataList);
            File file = new File("./data.json");
            if (!file.exists()) {
                file.createNewFile();
            }else{
                file.delete();
                file.createNewFile();
            }
            Files.write(file.toPath(), content.replace("\n", "").getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startTask() {
        while (true) {
            dataList.stream().filter(t -> t.getTaskStatus() == 0).forEach(paramInfo -> {
                executorService.execute(() -> {
                    if (paramInfo.getTaskStatus() == 0) {
                        new Download(paramInfo, model,
                                dataList, table, textArea).start();
                    }
                });
            });
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private int setEmptyLocaltion() {
        // TODO Auto-generated method stub
        int result = 0;
        for (int i = 0; i < tableData.size(); i++) {
            Vector<String> rowData = tableData.get(i);
            int[] count = {0};
            rowData.stream().filter(t -> StringUtils.isBlank(t)).forEach(t -> count[0]++);
            if (count[0] >= 5) {
                result = i;
                break;
            }
        }
        return result;
    }

    static String hexToStr(String key) {
        return getString(key);
    }

    @Nullable
    public static String getString(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        boolean flag = false;
        if (key.indexOf("0x") != -1 || key.indexOf("0X") != -1) {
            key = key.replace("0x", "").replace("0X", "");
            flag = true;
        }
        String[] bys = key.split(",");
        byte[] bytes = new byte[bys.length];
        int a = 0;
        for (int i = 0; i < bys.length; i++) {
            if (flag) {
                a = Integer.parseInt(bys[i].trim(), 16);
            } else {
                a = Integer.parseInt(bys[i].trim());
            }
            bytes[i] = (byte) a;
        }
        return bys.length == 1 ? key : new String(bytes);
    }
}
