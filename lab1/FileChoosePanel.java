package lab1;
//change on branch B1
import Dot.DotCompiler;
import lab1.MyIconButton;

import Graph.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Hunter on 2017/9/20.
 */
public class FileChoosePanel extends JPanel {



    public final static ImageIcon ICON_STOP = new ImageIcon(
            AppMainWindow.class.getResource("20170924151910.png"));

    public final static ImageIcon ICON_STOP_ENABLE = new ImageIcon(
            AppMainWindow.class.getResource("20170924151954.png"));

    public final static ImageIcon ICON_SYNC_NOW = new ImageIcon(
            AppMainWindow.class.getResource("20170924152301.png"));

    public final static ImageIcon ICON_SYNC_NOW_ENABLE = new ImageIcon(
            AppMainWindow.class.getResource("20170924152222.png"));


    public final static int MAIN_H_GAP = 25;

    private Image img;

    public static MyIconButton fileChooseButton;
    public static MyIconButton InputPathButton;

    public JFileChooser chooser = new  JFileChooser();

    /**
     * 构造
     */
    public FileChoosePanel()
    {
        super(true);
        initialize();
        addComponent();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(2,1));
    }

    /**
     * 添加组件
     */
    private void addComponent() {
        this.add(getUpPanel());
        this.add(getCenterPanel());
    }

    private JPanel getUpPanel()
    {
        JPanel UpPanel = new JPanel()
        {
            public void paint(Graphics g)
            {
                try
                {
                    img = ImageIO.read(new File(AppMainWindow.class.getResource("20170923160526.png").toURI()));
                    g.drawImage(img,0,0,700,200,null);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        //UpPanel.setBackground(Color.WHITE);
        return UpPanel;
    }

    private JPanel getCenterPanel()
    {
        JPanel CenterPanel = new JPanel();
        CenterPanel.setBackground(Color.WHITE);
        CenterPanel.setLayout(new GridLayout(1,2));
        JPanel panelGrid1 = new JPanel();
        panelGrid1.setBackground(Color.WHITE);
        panelGrid1.setLayout(new FlowLayout(FlowLayout.CENTER,MAIN_H_GAP,15));
        JPanel panelGrid2 = new JPanel();
        panelGrid2.setBackground(Color.WHITE);
        panelGrid2.setLayout(new FlowLayout(FlowLayout.CENTER, MAIN_H_GAP, 15));
        JPanel panelGrid3 = new JPanel();
        panelGrid3.setBackground(Color.WHITE);
        panelGrid3.setLayout(new FlowLayout(FlowLayout.CENTER, MAIN_H_GAP, 15));
        JPanel panelGrid4 = new JPanel();
        panelGrid4.setBackground(Color.WHITE);
        panelGrid4.setLayout(new FlowLayout(FlowLayout.CENTER, MAIN_H_GAP, 15));

        fileChooseButton = new MyIconButton(ICON_STOP, ICON_STOP_ENABLE,
                ICON_STOP, "");
        InputPathButton = new MyIconButton(ICON_SYNC_NOW, ICON_SYNC_NOW_ENABLE,
                ICON_STOP, "");

        panelGrid2.add(fileChooseButton);
        panelGrid3.add(InputPathButton);

        CenterPanel.add(panelGrid1);
        CenterPanel.add(panelGrid2);
        CenterPanel.add(panelGrid3);
        CenterPanel.add(panelGrid4);

        return CenterPanel;
    }


    private void addListener()
    {
        fileChooseButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                chooser.setCurrentDirectory(new File("."));

                // show file chooser dialog
                int result = chooser.showOpenDialog(FileChoosePanel.this);

                // if image file accepted, set it as icon of the label
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        String PathString = chooser.getSelectedFile().getPath().toString();
                        System.out.println(PathString);
                        AppMainWindow.graph =  new Graph(PathString);
                        //并转换窗口
                        AppMainWindow.ImagePath = DotCompiler.saveFile(PathString, AppMainWindow.graph.toDot());
                        AppMainWindow.mainPanel.removeAll();
                        AppMainWindow.mainPanel.add(AppMainWindow.functionChoosepanel,BorderLayout.CENTER);
                        AppMainWindow.mainPanel.updateUI();

                    }
                    catch (Exception e)
                    {
                        AppMainWindow.ReInputFlag = 1;

                        AppMainWindow.window.ErrorSize();
                        AppMainWindow.mainPanel.removeAll();
                        AppMainWindow.mainPanel.add(AppMainWindow.inputErrorpanel,BorderLayout.CENTER);
                        AppMainWindow.mainPanel.updateUI();
                    }
                }
            }
        });

        InputPathButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {

                AppMainWindow.mainPanel.removeAll();
                AppMainWindow.mainPanel.add(AppMainWindow.inputPathpanel,BorderLayout.CENTER);
                AppMainWindow.mainPanel.updateUI();
            }
        });

    }

}
