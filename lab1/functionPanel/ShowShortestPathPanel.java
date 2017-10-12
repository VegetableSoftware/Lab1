package lab1.functionPanel;
//change on branch B2
import Graph.Graph;
import lab1.AppMainWindow;
import lab1.MyIconButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by Hunter on 2017/9/25.
 */
public class ShowShortestPathPanel extends JFrame{

    public final static int MAIN_WINDOW_X = 120;
    public final static int MAIN_WINDOW_Y = 50;
    public final static int MAIN_WINDOW_WIDTH = 1500;
    public final static int MAIN_WINDOW_HEIGHT = 1000;

    public static JButton preImgbutton;
    public static JButton nextImgbutton;

    public static JFrame graphWindow = new JFrame();
    private JLabel GraphLabel;

    public int num = 0;
    public int count = 0;
    //所有图的地址
    private ArrayList<String> list = new ArrayList<String>();

    private void initialize()
    {
        graphWindow.setBounds(MAIN_WINDOW_X,MAIN_WINDOW_Y,MAIN_WINDOW_WIDTH,
                MAIN_WINDOW_HEIGHT);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //AppMainWindow window = new AppMainWindow();
                    graphWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    graphWindow.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ShowShortestPathPanel()
    {
        File firstFile = new File(AppMainWindow.FirstShortestPathfilePath);
        File[] fileList = firstFile.getParentFile().listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                String[] part = fileList[i].getName().split("\\.");
                if (part[1].equals("png") || part[1].equals("PNG") ) {
                    //System.out.println(part[1]);
                    list.add(fileList[i].getAbsolutePath());
                    num++;
                    //System.out.println(fileList[i].getAbsoluteFile());
                    if (fileList[i].getAbsolutePath().equals(AppMainWindow.FirstShortestPathfilePath)) {
                        count = num;
                    }
                }
            }
        }
        //嵌入框架
        this.setLayout(new BorderLayout());
        initialize();
        addComponent();
        addListener();
    }

    private void addComponent()
    {
        graphWindow.add(getGraphLabel(),BorderLayout.CENTER);
        graphWindow.add(getButtonPanel(),BorderLayout.SOUTH);
    }

    private JLabel getGraphLabel()
    {
        //图框
        GraphLabel = new JLabel();
        GraphLabel.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
        GraphLabel.setSize(600, 450);

        //展示第一张图
        ImageIcon image = new ImageIcon((AppMainWindow.FirstShortestPathfilePath));

        Image scaledImage = image.getImage().getScaledInstance(800,900,Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        GraphLabel.setIcon(scaledIcon);
        GraphLabel.setHorizontalAlignment(0);
        GraphLabel.setVerticalAlignment(0);

        return GraphLabel;
    }

    private JPanel getButtonPanel()
    {
        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setBackground(Color.WHITE);
        ButtonPanel.setLayout(new GridLayout(1,4));

        JPanel gridPanel1 = new JPanel();
        gridPanel1.setBackground(Color.WHITE);
        JPanel gridPanel2 = new JPanel();
        gridPanel2.setBackground(Color.WHITE);
        gridPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel gridPanel3 = new JPanel();
        gridPanel3.setBackground(Color.WHITE);
        gridPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel gridPanel4 = new JPanel();
        gridPanel4.setBackground(Color.WHITE);

        preImgbutton = new JButton();
        nextImgbutton = new JButton();
        preImgbutton.setText("pre");
        nextImgbutton.setText("next");

        gridPanel2.add(preImgbutton);
        gridPanel3.add(nextImgbutton);

        ButtonPanel.add(gridPanel1);
        ButtonPanel.add(gridPanel2);
        ButtonPanel.add(gridPanel3);
        ButtonPanel.add(gridPanel4);

        return ButtonPanel;
    }

    private void addListener()
    {
        preImgbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                if (count >= 1) {
                    GraphLabel.setText("");
                    String path = list.get(count - 1);
                    //System.out.println(path);
                    ImageIcon image = new ImageIcon(path);
                    Image scaledImage = image.getImage().getScaledInstance(800, 900, Image.SCALE_DEFAULT);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    GraphLabel.setIcon(scaledIcon);
                    GraphLabel.setHorizontalAlignment(0);
                    GraphLabel.setVerticalAlignment(0);
                    count--;
                    //System.out.println(path+"\t"+count);
                } else {
                    //给出警告信息
                    GraphLabel.setIcon(null);
                    GraphLabel.setText("已经是第一张图片！");
                    GraphLabel.setFont(new Font("",0,16));
                    if(count >= 0)
                    {
                        count--;
                    }
                }
            }
        });

        nextImgbutton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                if (count < list.size() - 1) {
                    GraphLabel.setText("");
                    //System.out.println(list.get(0));
                    String path = list.get(count + 1);
                    ImageIcon image = new ImageIcon(path);
                    Image scaledImage = image.getImage().getScaledInstance(800, 900, Image.SCALE_DEFAULT);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    GraphLabel.setIcon(scaledIcon);
                    GraphLabel.setHorizontalAlignment(0);
                    GraphLabel.setVerticalAlignment(0);
                    count++;
                    //System.out.println(path+"\t"+count);
                } else {
                    //给出警告信息
                    GraphLabel.setIcon(null);
                    GraphLabel.setText( "已经是最后一张图片！");
                    GraphLabel.setFont(new Font("",0,16));
                    if(count < num)
                    {
                        count++;
                    }

                }
            }
        });
    }
}
