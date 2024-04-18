import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {
    private ArrayList<Cell> cells = new ArrayList<>();
    private enum Difficulty {EASY,INTERMEDIATE,EXPERT};
    private Difficulty difficulty;
    private int numOfCells;

    GUI(){
        init(Difficulty.EASY);
    }
    GUI(String difficulty){
        switch (difficulty){
            case "EASY" -> {
                numOfCells=9*9;
                init(Difficulty.EASY);
            }
            case "INTERMEDIATE" -> {
                numOfCells=16*16;
                init(Difficulty.INTERMEDIATE);
            }
            case "EXPERT" -> {
                numOfCells=30*16;
                init(Difficulty.EXPERT);
            }
        }
    }
    private void init(Difficulty difficulty){
        JFrame game = new JFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu menu = new JMenu();
        menu.setBackground(Color.LIGHT_GRAY);

        JPanel controls = new JPanel();
        controls.setBackground(Color.LIGHT_GRAY);

        JPanel board = new JPanel();
        board.setBackground(Color.DARK_GRAY);
        cells.forEach(board::add);

        game.add(menu);
        game.add(controls);
        game.add(board);
        game.pack();
        game.setLayout(new FlowLayout());
        game.setSize(420,420);
        game.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    //Method: create "JButton" cells

    //Method: add cells to JPanel

    private static class Cell extends JButton {
        private Point cell;
        Cell(int cornerX, int cornerY){
            this.cell = new Point();
            this.setBounds(0,0,20,20);
            setBackground(Color.LIGHT_GRAY);
            setBorder(new EtchedBorder());
        }
        Cell(int x, int y, int cornerX, int cornerY){
            this.cell = new Point(x,y);
            this.setBounds(0,0,20,20);
            setBackground(Color.LIGHT_GRAY);
            setBorder(new EtchedBorder());
        }

        public int getX() {
            return (int) cell.getX();
        }
        public int getY() {
            return (int) cell.getY();
        }

        public void setLocation(int x, int y) {
            cell.setLocation(x, y);
        }
    }
}
