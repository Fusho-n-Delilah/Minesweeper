import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.JPanel;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI implements ActionListener {
    private Cell[][] cells;
    private JPanel board;
    private JPanel controls;
    private JMenuBar menu;
    private JFrame game;
    private Difficulty difficulty;
    private static Timer timer;
    private static int time;
    private int numOfCells;
    private int numOfFlags;
    private int maxX, maxY;
    private JTextField flagsLeft;
    private JTextField timerDisplay;
    private JButton resetButton;
    private final ImageIcon smiley = new ImageIcon("src/main/resources/smiley.png");
    private final ImageIcon smiley_dead = new ImageIcon("src/main/resources/smiley_dead.png");
    private final ImageIcon smiley_cool = new ImageIcon("src/main/resources/smiley_cool.png");
    private final ImageIcon smiley_melted = new ImageIcon("src/main/resources/smiley_melted.png");
    private final ImageIcon flag = new ImageIcon("src/main/resources/flag.png");
    private final ImageIcon mine = new ImageIcon("src/main/resources/mine.png");
    private ImageIcon[] numbers;



    //    GUI(String difficulty){
//        switch (difficulty){
//            case "EASY" -> {
//                init(Difficulty.EASY);
//            }
//            case "INTERMEDIATE" -> {
//                init(Difficulty.INTERMEDIATE);
//            }
//            case "EXPERT" -> {
//                init(Difficulty.EXPERT);
//            }
//        }
//    }
    GUI(Difficulty difficulty){
        //set difficulty
        this.difficulty = difficulty;
        switch (difficulty){
            case EASY -> {
                this.numOfCells=9*9;
                this.numOfFlags = 10;
                this.maxX = 9;
                this.maxY = 9;
                this.cells = new Cell[9][9];
            }
            case INTERMEDIATE -> {
                this.numOfCells=16*16;
                this.numOfFlags=40;
                this.maxX = 16;
                this.maxY = 16;
                this.cells = new Cell[16][16];
            }
            case EXPERT -> {
                this.numOfCells=30*16;
                this.numOfFlags=99;
                this.maxX = 30;
                this.maxY = 16;
                this.cells = new Cell[16][30];
            }
        }


        //initialize Frame
        this.game = new JFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setBackground(Color.lightGray);

        //create and initialize components
        setNumbers();
        initializeMenu();
        initializeControls();
        initializeBoard();
        initializeCells();
        initializeTimer();

        //pack components in Frame and finish Initializing Frame

        game.add(this.controls);
        game.add(this.board);
        game.pack();
        game.setLocationRelativeTo(null);
        game.setLayout(new FlowLayout());
        switch (difficulty){
            case EASY -> game.setSize(235,350);
            case INTERMEDIATE -> game.setSize(405,525);
            case EXPERT -> game.setSize(765,530);
        }
        game.setResizable(false);
        game.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(resetButton)){
            Minesweeper.GameEngine.handleClick("RESET");
            this.game.dispose();
        }
        //Action Listeners for controls

    }
    public void displayBoard(ViewBoard viewModel){
        if(viewModel.getGameState().equals("WON")){
            this.resetButton.setIcon(smiley_cool);
            timer.stop();
        } else if (viewModel.getGameState().equals("LOST")){
            this.resetButton.setIcon(smiley_dead);
            timer.stop();
        }
        int flags = 0;
        for(int i = 0; i<maxY; i++){
            for(int j = 0; j<maxX; j++) {
                String cellValue = viewModel.getValue(i, j);
                Cell cellButton = cells[i][j];
                if (viewModel.isFlagged(i, j)) {
                    flags++;
                    cellButton.setFlagged(!cells[i][j].getFlagged());
                    cellButton.setEnabled(false);
                    cellButton.setIcon(flag);
                    cellButton.setDisabledIcon(flag);

                } else if (cellValue.equals(" ") && viewModel.isRevealed(i, j)) {
                    cellButton.setEnabled(false);
                    cellButton.setIcon(numbers[0]);
                    cellButton.setDisabledIcon(numbers[0]);
                } else if (cellValue.equals("M") && viewModel.isRevealed(i, j)) {
                    cellButton.setEnabled(false);
                    cellButton.setBackground(Color.RED);
                    cellButton.setIcon(mine);
                    cellButton.setDisabledIcon(this.mine);
                } else if (!cellValue.equals(" ") && viewModel.isRevealed(i, j)) {
                    int value = Integer.parseInt(viewModel.getValue(i, j));
                    cellButton.setEnabled(false);
                    cellButton.setHorizontalAlignment(SwingConstants.CENTER);
                    cellButton.setIcon(numbers[value]);
                    cellButton.setDisabledIcon(numbers[value]);
                } else {
                    //should only be flagged cells
                    cellButton.setIcon(null);
                }
            }
        }
        flagsLeft.setText(String.valueOf((this.numOfFlags-flags)));

    }
    private void initializeTimer(){
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                timerDisplay.setText(String.valueOf(time));
            }
        });
    }

//    private void displayLose(ViewBoard viewModel) {
//        this.resetButton.setIcon(smiley_dead);
//    }
//
//    private void displayWin(ViewBoard viewModel) {
//        this.resetButton.setIcon(smiley_cool);
//    }

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
    private void initializeMenu() {
        this.menu = new JMenuBar();
        ArrayList<JMenuItem> menuItems = new ArrayList<>();
        game.setJMenuBar(this.menu);
        JMenu difficultyOption = new JMenu("Level");

        //easy
        JMenuItem easy = new JMenuItem("Easy");
        easy.addActionListener(e -> {
            Minesweeper.GameEngine.handleClick("EASY");
            this.game.dispose();
        });
        menuItems.add(easy);
        //intermediate
        JMenuItem intermediate = new JMenuItem("Intermediate");
        intermediate.addActionListener(e -> {
            Minesweeper.GameEngine.handleClick("INTERMEDIATE");
            this.game.dispose();
        });
        //expert
        menuItems.add(intermediate);
        JMenuItem expert = new JMenuItem("Expert");
        expert.addActionListener(e -> {
            Minesweeper.GameEngine.handleClick("EXPERT");
            this.game.dispose();
        });
        menuItems.add(expert);

        menuItems.forEach(difficultyOption::add);
        menu.add(difficultyOption);

        this.menu.setPreferredSize(new Dimension(100,25));
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
                Cell cell = new Cell(j,i);
                this.board.add(cell);
                this.cells[i][j] = cell;
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
        this.flagsLeft = new JTextField(3);
        flagsLeft.setFont(new Font("vt100",Font.PLAIN,27));
        flagsLeft.setHorizontalAlignment(SwingConstants.RIGHT);
        flagsLeft.setBackground(new Color(43, 13, 13));
        flagsLeft.setForeground(Color.red);
        flagsLeft.setFocusable(false);
        flagsLeft.setText(String.valueOf(numOfFlags));
        this.resetButton = new JButton(smiley);
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(50,50));
        resetButton.addActionListener(this);

        //timer
        this.time = 0;
        this.timerDisplay = new JTextField(3);
        timerDisplay.setFont(new Font("vt100",Font.PLAIN,27));
        timerDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        timerDisplay.setBackground(new Color(43, 13, 13));
        timerDisplay.setForeground(Color.red);
        timerDisplay.setText(String.valueOf(this.time));

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
    private void setNumbers() {
        this.numbers = new ImageIcon[9];

        this.numbers[0] = new ImageIcon("src/main/resources/numbers/0.png");
        this.numbers[1] = new ImageIcon("src/main/resources/numbers/1.png");
        this.numbers[2] = new ImageIcon("src/main/resources/numbers/2.png");
        this.numbers[3] = new ImageIcon("src/main/resources/numbers/3.png");
        this.numbers[4] = new ImageIcon("src/main/resources/numbers/4.png");
        this.numbers[5] = new ImageIcon("src/main/resources/numbers/5.png");
        this.numbers[6] = new ImageIcon("src/main/resources/numbers/6.png");
        this.numbers[7] = new ImageIcon("src/main/resources/numbers/7.png");
        this.numbers[8] = new ImageIcon("src/main/resources/numbers/8.png");
    }
    public static class Cell extends JButton {
        private final Point cell;
        private boolean flagged;
        Cell(){
            this.cell = new Point();
            this.flagged = false;
            this.setPreferredSize(new Dimension(25,25));
            setBackground(Color.LIGHT_GRAY);
            setBorder(new BevelBorder(BevelBorder.RAISED));
            this.setOpaque(true);
        }
        Cell(int x, int y){
            this.cell = new Point(x,y);
            this.flagged = false;
            this.setPreferredSize(new Dimension(25,25));
//            this.setBackground(Color.LIGHT_GRAY);
//            setBorder(new BevelBorder(BevelBorder.RAISED));
            this.setFocusPainted(false);
            this.setOpaque(true);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Cell targetCell = new Cell();
                    Object obj = e.getSource();
                    if (obj instanceof Cell) {
                        targetCell = (Cell)obj;
                    }
                    int x = targetCell.getCellX();
                    int y = targetCell.getCellY();

                    if(!timer.isRunning() && GUI.time==0){
                        timer.start();
                    }

                    if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && !targetCell.isEnabled()){
                        Minesweeper.GameEngine.clearAdjacent(y,x);
                    }

                    if(e.getButton() == MouseEvent.BUTTON3){
                        Minesweeper.GameEngine.handleClick(y,x,MouseEvent.BUTTON3);
                    } else {
                        Minesweeper.GameEngine.handleClick(y,x,MouseEvent.BUTTON1);
                    }

                }
            });
        }

        public int getCellX() {
            return (int) cell.getX();
        }
        public int getCellY() {
            return (int) cell.getY();
        }

        public void setFlagged(boolean flagged) {
            this.flagged = flagged;
        }
        public boolean getFlagged(){
            return this.flagged;
        }

        public void setLocation(int x, int y) {
            cell.setLocation(x, y);
        }
    }
}

