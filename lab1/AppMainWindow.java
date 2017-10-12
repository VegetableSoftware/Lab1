package lab1;

//change on branch C4

import Graph.Graph;
import lab1.functionPanel.*;

import javax.swing.*;
import java.awt.*;


public class AppMainWindow {

    /**
     * 主窗口大小
     */
    public final static int MAIN_WINDOW_X = 240;
    public final static int MAIN_WINDOW_Y = 100;
    public final static int MAIN_WINDOW_WIDTH = 700;//885
    public final static int MAIN_WINDOW_HEIGHT = 500;//636

    public static int ReInputFlag = 0;

    public static AppMainWindow window = new AppMainWindow();

    private JFrame frame;

    public static JPanel mainPanel;

    public static String ImagePath;
    public static String FirstShortestPathfilePath = null;

    public static Graph graph;

    public static String randomText;

    public static FileChoosePanel fileChoosepanel;
    public static InputPathPanel inputPathpanel;
    public static FunctionChoosePanel functionChoosepanel;
    public static QueryBridgeWordsPanel queryBridgewordsPanel;
    public static GenerateNewTextPanel generateNewtextPanel;
    public static CalcShortestPathPanel calShortestPathpanel;
    public static InputErrorPanel inputErrorpanel;
    public static RandomWalkPanel randomWalkpanel;


    /**
     * 程序入口main
     */
    public static void main(String[] args) {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //AppMainWindow window = new AppMainWindow();
                    //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

                    window.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 构造，创建APP
     */
    public AppMainWindow() {
        initialize();
    }

    /**
     * 初始化frame内容
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(MAIN_WINDOW_X,MAIN_WINDOW_Y,MAIN_WINDOW_WIDTH,
                MAIN_WINDOW_HEIGHT);
        frame.setResizable(false);



        frame.setTitle("Text to Graph");
        frame.setBackground(Color.WHITE);

        mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        fileChoosepanel = new FileChoosePanel();
        inputPathpanel = new InputPathPanel();
        functionChoosepanel = new FunctionChoosePanel();
        queryBridgewordsPanel = new QueryBridgeWordsPanel();
        generateNewtextPanel = new GenerateNewTextPanel();
        calShortestPathpanel = new CalcShortestPathPanel();
        inputErrorpanel = new InputErrorPanel();
        randomWalkpanel = new RandomWalkPanel();

        mainPanel.add(fileChoosepanel,BorderLayout.CENTER);

        frame.add(mainPanel);

    }

    public void windowSetVisible(Boolean par)
    {
        frame.setVisible(par);

    }

    public  void DefaultSize()
    {
        frame.setBounds(MAIN_WINDOW_X,MAIN_WINDOW_Y,MAIN_WINDOW_WIDTH,
                MAIN_WINDOW_HEIGHT);
    }

    public  void ErrorSize()
    {
        frame.setBounds(MAIN_WINDOW_X*2,MAIN_WINDOW_Y*2,MAIN_WINDOW_WIDTH/2,MAIN_WINDOW_HEIGHT/2);
    }

}
