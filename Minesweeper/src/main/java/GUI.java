import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {
    private ArrayList<Cell> cells = new ArrayList<>();
    private JPanel board;
    private JPanel controls;
    private Difficulty difficulty;
    private int numOfCells;
    private int numOfFlags;
    private final ImageIcon smiley = new ImageIcon("src/main/resources/smiley.png");
    private final ImageIcon smiley_dead = new ImageIcon("src/main/resources/smiley_dead.png");
    private final ImageIcon smiley_melted = new ImageIcon("src/main/resources/smiley_melted.png");

    GUI(){
        init(Difficulty.EASY);
    }
    GUI(String difficulty){
        switch (difficulty){
            case "EASY" -> {
                this.numOfCells=9*9;
                this.numOfFlags = 10;
                init(Difficulty.EASY);
            }
            case "INTERMEDIATE" -> {
                this.numOfCells=16*16;
                this.numOfFlags=40;
                init(Difficulty.INTERMEDIATE);
            }
            case "EXPERT" -> {
                this.numOfCells=30*16;
                this.numOfFlags=99;
                init(Difficulty.EXPERT);
            }
        }
    }
    private void init(Difficulty difficulty){
        //set difficulty
        this.difficulty = difficulty;

        //initialize Frame
        JFrame game = new JFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create and initialize components
        initializeControls();
        initializeBoard();
        initializeCells();

        //pack components in Frame and finish Initializing Frame
        game.add(controls);
        game.add(this.board);
        game.pack();
        game.setLayout(new FlowLayout());
        switch (difficulty){
            case EASY -> game.setSize(235,325);
            case INTERMEDIATE -> game.setSize(405,500);
            case EXPERT -> game.setSize(770,505);
        }
        game.setResizable(false);
        game.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private void initializeBoard(){
        switch (this.difficulty) {
            case EASY ->{
                this.board = new JPanel(new GridLayout(9, 9));
                this.board.setPreferredSize(new Dimension(9*25,9*25));
            }
            case INTERMEDIATE ->{
                this.board = new JPanel(new GridLayout(16, 16));
                this.board.setPreferredSize(new Dimension(16*25,16*25));
            }
            case EXPERT ->{
                this.board = new JPanel(new GridLayout(16, 30));
                this.board.setPreferredSize(new Dimension(30*25,16*25));
            }
        }
        this.board.setBackground(Color.LIGHT_GRAY);
        this.board.setBorder(new BevelBorder(BevelBorder.RAISED));
    }
    private void initializeCells(){
        int rows, cols;
        if(this.difficulty == Difficulty.EXPERT){
            rows = 16;
            cols = 30;
        } else if (this.difficulty == Difficulty.INTERMEDIATE) {
            rows = 16;
            cols = 16;
        } else {
            rows = 9;
            cols = 9;
        }

        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                Cell cell = new Cell(i,j);
                this.board.add(cell);
                this.cells.add(cell);
            }
        }
    }
    public void initializeControls(){
        /*
            Panel containing the controls of the gui
            a red window containing the number of flags remaining
            a square button with a smiley face that changes based on game state and
                when clicked resets the game
            a timer that increments every second while the game plays
                stops on either a game win state or lose state
         */
        this.controls = new JPanel();
        controls.setBorder(new BevelBorder(BevelBorder.LOWERED));
        switch (difficulty){
            case EASY -> controls.setPreferredSize(new Dimension(235,60));
            case INTERMEDIATE -> controls.setPreferredSize(new Dimension(405,60));
            case EXPERT -> controls.setPreferredSize(new Dimension(745,60));
        }
        controls.setLayout(new BorderLayout());
        JTextField flagsLeft = new JTextField(3);
        flagsLeft.setFont(new Font("vt100",Font.PLAIN,27));
        flagsLeft.setHorizontalAlignment(SwingConstants.RIGHT);
        flagsLeft.setBackground(new Color(43, 13, 13));
        flagsLeft.setForeground(Color.red);
        flagsLeft.setText(String.valueOf(numOfFlags));
        JButton resetButton = new JButton(smiley);
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(50,50));
        JTextField timerDisplay = new JTextField(3);
        timerDisplay.setFont(new Font("vt100",Font.PLAIN,27));
        timerDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        timerDisplay.setBackground(new Color(43, 13, 13));
        timerDisplay.setForeground(Color.red);
        timerDisplay.setText("0");

        JPanel centerControl = new JPanel(new GridLayout(1,7));
        if(difficulty != Difficulty.EASY) {
            int i = 0;
            if(difficulty == Difficulty.EXPERT) {
                while (i<4){
                    centerControl.add(Box.createHorizontalGlue());
                    i++;
                }
                i=0;
                centerControl.add(resetButton);
                while (i<4){
                    centerControl.add(Box.createHorizontalGlue());
                    i++;
                }
            } else {
                while (i<2){
                    centerControl.add(Box.createHorizontalGlue());
                    i++;
                }
                i=0;
                centerControl.add(resetButton);
                while (i<2){
                    centerControl.add(Box.createHorizontalGlue());
                    i++;
                }
            }
        } else {
            centerControl.add(resetButton);
        }

        controls.add(BorderLayout.WEST, flagsLeft);
        controls.add(centerControl, BorderLayout.CENTER);
        controls.add(BorderLayout.EAST, timerDisplay);
    }
    public static class Cell extends JButton {
        private final Point cell;
        Cell(){
            this.cell = new Point();
            this.setPreferredSize(new Dimension(25,25));
            setBackground(Color.LIGHT_GRAY);
            setBorder(new BevelBorder(BevelBorder.RAISED));
            this.setOpaque(true);
        }
        Cell(int x, int y){
            this.cell = new Point(x,y);
            this.setPreferredSize(new Dimension(25,25));
            setBackground(Color.LIGHT_GRAY);
//            setBorder(new BevelBorder(BevelBorder.RAISED));
            this.setFocusPainted(false);
            this.setOpaque(true);
        }

        public int getCellX() {
            return (int) cell.getX();
        }
        public int getCellY() {
            return (int) cell.getY();
        }

        public void setLocation(int x, int y) {
            cell.setLocation(x, y);
        }
    }
}
