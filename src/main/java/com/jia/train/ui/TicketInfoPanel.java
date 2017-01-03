package com.jia.train.ui;

import com.jia.train.listener.QueryTicketActionListener;
import com.jia.train.po.StationNameId;
import com.jia.train.po.QueryInfo;
import com.jia.train.util.StationDataUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiaxl on 2017/1/1.
 */
public class TicketInfoPanel extends JPanel {

    //车票条件信息
    public QueryInfo queryInfo = new QueryInfo();

    JLabel labelStartStation = new JLabel("起点站");
    JTextField textStart = new JTextField();

    JLabel labelEndStation = new JLabel("终点站");
    JTextField textEnd = new JTextField();

    Vector<StationNameId> dataStart = new Vector<StationNameId>();
    Vector<StationNameId> dataEnd = new Vector<StationNameId>();

    JComboBox boxStart = new JComboBox() {
        public Dimension getPreferredSize() {
            return new Dimension(super.getPreferredSize().width, 0);
        }
    };
    JComboBox boxEnd = new JComboBox() {
        public Dimension getPreferredSize() {
            return new Dimension(super.getPreferredSize().width, 0);
        }
    };

    JTextField textDate = new JTextField();
    JLabel labelDate = new JLabel("出发日");

    JButton buttonQuery = new JButton("查 询");

    public TicketInfoPanel() {
        initQueryComponent();
        initListener();
    }


    private void initQueryComponent() {
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        labelStartStation.setBounds(30, 0, 100, 30);
        labelStartStation.setFont(new Font("宋体", Font.PLAIN, 16));
        this.add(labelStartStation);

        labelEndStation.setBounds(250, 0, 100, 30);
        labelEndStation.setFont(new Font("宋体", Font.PLAIN, 16));
        this.add(labelEndStation);

        textStart.setLayout(new BorderLayout());
        textStart.add(BorderLayout.SOUTH, boxStart);
        textStart.setBounds(85, 0, 90, 30);
        textStart.setFont(new Font("宋体", Font.PLAIN, 14));
        this.add(textStart);

        textEnd.setBounds(305, 0, 90, 30);
        textEnd.setLayout(new BorderLayout());
        textEnd.add(BorderLayout.SOUTH, boxEnd);
        textEnd.setFont(new Font("宋体", Font.PLAIN, 14));
        boxEnd.setFont(new Font("宋体", Font.PLAIN, 15));
        this.add(textEnd);

        labelDate.setBounds(450, 0, 100, 30);
        labelDate.setFont(new Font("宋体", Font.PLAIN, 16));
        this.add(labelDate);
        textDate.setBounds(520, 0, 100, 30);
        textDate.setFont(new Font("宋体", Font.PLAIN, 14));
        CalendarPanel p = new CalendarPanel(textDate, "yyyy-MM-dd");
        p.initCalendarPanel();
        JLabel l = new JLabel("日历面板");
        p.add(l);
        this.add(textDate);
        this.add(p);

        buttonQuery.setBounds(650, 0, 100, 30);
        buttonQuery.setFont(new Font("宋体", Font.PLAIN, 14));
        buttonQuery.addActionListener(new QueryTicketActionListener(this));
        this.add(buttonQuery);

    }


    /**
     * 初始化监听器
     */
    private void initListener() {
        //刷新起点下拉列表数据监听器
        textStart.addKeyListener(new KeyAdapter() {
            String text;

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_SPACE) {
                    e.setKeyCode(KeyEvent.VK_ENTER);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.setSource(boxStart.getEditor().getEditorComponent());
                    boxStart.getEditor().getEditorComponent().dispatchEvent(e);
                    e.setSource(boxStart);
                    boxStart.dispatchEvent(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                text = textStart.getText();
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_ENTER || code == KeyEvent.VK_UP) {
                    return;
                }

                if (code == KeyEvent.VK_SPACE) {
                    if (text.length() != 0) {
                        textStart.setText(text.trim());
                        if (text.matches("[\\u4E00-\\u9FA5]+")) {
                            refreshStart(text);
                        }
                        return;
                    }
                }
                refreshStart(text);

            }
        });
        //刷新终点下拉列表数据监听器
        textEnd.addKeyListener(new KeyAdapter() {
            String text;

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_SPACE) {
                    e.setKeyCode(KeyEvent.VK_ENTER);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.setSource(boxStart.getEditor().getEditorComponent());
                    boxEnd.getEditor().getEditorComponent().dispatchEvent(e);
                    e.setSource(boxEnd);
                    boxEnd.dispatchEvent(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                text = textEnd.getText();
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_ENTER || code == KeyEvent.VK_UP) {
                    return;
                }

                if (code == KeyEvent.VK_SPACE) {
                    if (text.length() != 0) {
                        textEnd.setText(text.trim());
                        if (text.matches("[\\u4E00-\\u9FA5]+")) {
                            refreshEnd(text);
                        }

                        return;
                    }
                }
                refreshEnd(text);

            }
        });
        //设置起点站文本框回显
        boxStart.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                StationNameId s = (StationNameId) boxStart.getSelectedItem();
                if (s != null) {
                    textStart.setText(s.getCnName());
                    queryInfo.setStartId(s.getId());
                    System.out.println(queryInfo);
                }
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });


        //设置终点站文本框回显
        boxEnd.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                StationNameId s = (StationNameId) boxEnd.getSelectedItem();
                if (s != null) {
                    textEnd.setText(s.getCnName());
                    queryInfo.setEndId(s.getId());
                    System.out.println(queryInfo);
                }
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        //设置日期条件
        textDate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                queryInfo.setDate(textDate.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                queryInfo.setDate(textDate.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void refreshStart(String addr) {
        System.out.println(addr);
        if (addr.equals("")) {
            boxStart.setPopupVisible(false);
            textStart.setText("");
            return;
        }
        if (addr.matches("[\\u4E00-\\u9FA5]+")) {
            dataStart = StationDataUtil.getStationByCnName(addr);
        } else {
            dataStart = StationDataUtil.getStationByAbbrOrUsName(addr);
        }

        if (dataStart.size() == 0) {
            return;
        }
        boxStart.removeAllItems();
        for (StationNameId s : dataStart) {
            boxStart.addItem(s);
        }
        boxStart.setPopupVisible(true);
    }

    private void refreshEnd(String addr) {
        System.out.println(addr);
        if (addr.equals("")) {
            boxEnd.setPopupVisible(false);
            textEnd.setText("");
            return;
        }
        if (addr.matches("[\\u4E00-\\u9FA5]+")) {
            dataEnd = StationDataUtil.getStationByCnName(addr);
        } else {
            dataEnd = StationDataUtil.getStationByAbbrOrUsName(addr);
        }

        if (dataEnd.size() == 0) {
            return;
        }
        boxEnd.removeAllItems();
        for (StationNameId s : dataEnd) {
            boxEnd.addItem(s);
        }
        boxEnd.setPopupVisible(true);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        frame.setBounds(200, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TicketInfoPanel());
        frame.setVisible(true);
    }
}
