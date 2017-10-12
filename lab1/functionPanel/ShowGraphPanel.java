package lab1.functionPanel;
//change on branch B2
import lab1.AppMainWindow;
import lab1.MyIconButton;

import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Created by Hunter on 2017/9/22.
 */
public class ShowGraphPanel extends JPanel {

    public static JFrame graphWindow = new JFrame();

    public final static int MAIN_WINDOW_X = 240;
    public final static int MAIN_WINDOW_Y = 50;
    public final static int MAIN_WINDOW_WIDTH = 1200;
    public final static int MAIN_WINDOW_HEIGHT = 960;

    private JLabel GraphLabel;

    private Image GraphImg;

    public ShowGraphPanel()
    {
        super(true);
        this.setLayout(new BorderLayout());
        initialize();
        addComponent();
    }

    private void initialize()
    {
        graphWindow.setBounds(MAIN_WINDOW_X,MAIN_WINDOW_Y,MAIN_WINDOW_WIDTH,
                MAIN_WINDOW_HEIGHT);
        graphWindow.setBackground(Color.WHITE);
        graphWindow.setLayout(new GridLayout(1,1));
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
        //File f = new File(AppMainWindow.ImagePath);
    }

    private void addComponent()
    {
        graphWindow.add(getGraphpanel(),BorderLayout.CENTER);
    }

    private JLabel getGraphpanel()
    {

        GraphLabel = new JLabel();
        GraphLabel.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
        GraphLabel.setSize(600, 450);

        //展示第一张图
        ImageIcon image = new ImageIcon((AppMainWindow.ImagePath));

        Image scaledImage = image.getImage().getScaledInstance(1100,860,Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        GraphLabel.setIcon(scaledIcon);
        GraphLabel.setHorizontalAlignment(0);
        GraphLabel.setVerticalAlignment(0);

        return GraphLabel;
    }
}
