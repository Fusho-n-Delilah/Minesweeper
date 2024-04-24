import java.util.Random;

public class Board{
    private Difficulty difficulty;
    private int mines;
    private int totalCells;
    private int revealed;
    private Cell[][] board;
    private int maxX;
    private int maxY;

    Board(Difficulty difficulty){
        switch (difficulty){
            case EASY -> {
                this.difficulty = difficulty;
                this.mines = 10;
                this.revealed=0;
                this.totalCells = 81;
                this.maxX = 8;
                this.maxY = 8;
                this.board = new Cell[9][9];
                initializeCells(9,9);
                placeMines();
                setCellValues(9,9);
            }
            case INTERMEDIATE -> {
                this.difficulty = difficulty;
                this.mines = 40;
                this.revealed = 0;
                this.totalCells = 256;
                this.maxX = 15;
                this.maxY = 15;
                this.board = new Cell[16][16];
                initializeCells(16,16);
                placeMines();
                setCellValues(16,16);
            }
            case EXPERT -> {
                this.difficulty = difficulty;
                this.mines = 99;
                this.revealed = 0;
                this.totalCells = 480;
                this.maxX = 29;
                this.maxY = 15;
                this.board = new Cell[16][30];
                initializeCells(16,30);
                placeMines();
                setCellValues(16,30);
            }
        }
    }

    private int searchForMines(int y, int x){
        int count = 0;

        for(int i = y-1; i <= y+1; i++){
            for(int j = x-1; j <= x+1; j++){

                try{
                    if((j<0 || j>maxX)||(i<0 || i>maxY)) continue;
                    if(i==y && j==x) continue;

                    if(board[i][j].isMine()){
                        count++;
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("searchForMines: Accessed an index out of bounds");
                }

            }
        }

        return count;
    }
    public boolean isWin(){
        return (revealed == totalCells - mines);
    }
    public boolean isMine(int y, int x){
        return this.board[y][x].isMine();
    }
    private void initializeCells(int rows, int cols){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                this.board[i][j] = new Cell();
            }
        }
    }
    private void setCellValues(int rows, int cols){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                int value = searchForMines(i,j);
                this.board[i][j].setValue(value);
            }
        }
    }
    private void placeMines(){
        int i = 0;
        while(i<this.mines) {
            int randX = randNum(maxX);
            int randY = randNum(maxY);
            if (this.board[randY][randX].isMine()){
                continue;
            }
            this.board[randY][randX].setMine(true);
            i++;
        }
    }
    public int getValue(int y, int x){
        return this.board[y][x].getValue();
    }
    public void revealCell(int y, int x){
        revealed++;
        this.board[y][x].setRevealed(true);
    }
    private static int randNum(int maxVal){
        Random random = new Random();
        return random.nextInt(maxVal);
    }
}
