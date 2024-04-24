import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Minesweeper {
    public static void main (String... args){
        new GameEngine(Difficulty.EASY);

        //tests
//        int count = 0;
//        int mines = 0;
//        for(int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                if(game.isMine(i,j)) {
//                    System.out.println(game.isMine(i, j));
//                    mines++;
//                } else {
//                    System.out.println(game.getValue(i,j));
//                }
//                count++;
//            }
//        }
//        System.out.println(count);
//        System.out.println(mines);
    }

    public static class GameEngine {
        private static ViewBoard viewModel;
        private static Board gameBoard;
        private static GUI view;
        private static Difficulty difficulty;
        private static ArrayList<Point> blankCells = new ArrayList<>();
        private static int maxX,
                            maxY,
                            rows,
                            cols;

        GameEngine(Difficulty difficulty){
            GameEngine.difficulty = difficulty;
            switch (difficulty){
                case EASY -> {
                    maxX = 8;
                    maxY = 8;
                    rows = 9;
                    cols = 9;
                }
                case INTERMEDIATE -> {
                    maxX = 15;
                    maxY = 15;
                    rows = 16;
                    cols = 16;
                }
                case EXPERT -> {
                    maxX = 29;
                    maxY = 15;
                    rows = 16;
                    cols = 30;
                }
            }
            this.viewModel = new ViewBoard(difficulty);
            this.gameBoard = new Board(difficulty);
            this.view = new GUI(difficulty);

        }
        public static void handleClick(String control){
            switch (control){
                case "RESET" -> resetGame(difficulty);
                case "EASY" -> resetGame(Difficulty.EASY);
                case "INTERMEDIATE" -> resetGame(Difficulty.INTERMEDIATE);
                case "EXPERT" -> resetGame(Difficulty.EXPERT);
            }

        }



        public static void handleClick(int y, int x, int clickType){
            if(clickType == MouseEvent.BUTTON3 && viewModel.getGameState().equals("RUNNING")){
                flagCell(y,x);
            } else {
                if(gameBoard.isRevealed(y,x)) return;
                gameBoard.revealCell(y, x);

                if(gameBoard.isWin()){
                    viewModel.setRevealed(y,x,true);
                    int value = gameBoard.getValue(y,x);
                    viewModel.setValue(y,x,String.valueOf(value));

                    setWin();
                } else if (gameBoard.isMine(y,x)) {
                    setLose();
                } else {
                    int value = gameBoard.getValue(y, x);
//                    gameBoard.revealCell(y, x);
                    System.out.println(gameBoard.getRevealed());

                    if (value == 0){
                        viewModel.setRevealed(y,x,true);
                        clearBlankCells(y,x);
                        System.out.println(gameBoard.getRevealed());
                    } else {
                        viewModel.setRevealed(y,x,true);
                        viewModel.setValue(y,x, String.valueOf(value));
                    }
                }
            }
            view.displayBoard(viewModel);
        }
        private static void flagCell(int y, int x){
            viewModel.setFlagged(y, x, !viewModel.isFlagged(y, x));
        }
        private static void clearBlankCells(int y, int x){
            //set the cell as revealed on the viewModel and on the data model
            //reveal all adjacent cells and the next immediately adjacent number
            boolean blankFound = false;

            for(int i = y-1; i <= y+1; i++){
                for(int j = x-1; j <= x+1; j++){

                    try{
                        if((j<0 || j > maxX)||(i<0 || i > maxY)) continue;
                        if(i==y && j==x) continue;

                        if(gameBoard.getValue(i,j) == 0 && !viewModel.isRevealed(i,j)){
                            blankCells.add(new Point(j,i));
                            blankFound = true;
                            gameBoard.revealCell(i,j);
                        }

                        if(!gameBoard.isRevealed(i,j)) gameBoard.revealCell(i,j);
                        viewModel.setRevealed(i, j, true);
                        if(gameBoard.getValue(i,j)>0) viewModel.setValue(i,j,String.valueOf(gameBoard.getValue(i,j)));

                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("clearBlanksCells: Accessed an index out of bounds");
                    }
                }
            }
            if(blankFound || !blankCells.isEmpty()) {
                Point nextBlankCell = blankCells.removeFirst();
                clearBlankCells(nextBlankCell.y, nextBlankCell.x);
            }
        }
        private static void setWin(){
            viewModel.setGameState("WON");
            //reveal all cells except flagged cells, flag all mines, and set the value of all cells
            view.displayBoard(viewModel);
        }
        private static void setLose(){
            viewModel.setGameState("LOST");
            //reveal all cells, unflag all cells, and set value of all cells "M" for the mines

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    if(gameBoard.isMine(i,j)){
                        viewModel.setValue(i,j,"M");
                    } else if (gameBoard.getValue(i,j) > 0) {

                        viewModel.setValue(i,j,String.valueOf(gameBoard.getValue(i,j)));
                    }
                    if(viewModel.isFlagged(i,j)){
                        viewModel.setFlagged(i,j,false);
                    }
                    viewModel.setRevealed(i,j,true);
                }
            }
        }
        private static void resetGame(Difficulty difficulty) {
            gameBoard = new Board(difficulty);
            viewModel = new ViewBoard(difficulty);
            view = new GUI(difficulty);
            switch (difficulty){
                case EASY -> {
                    maxX = 8;
                    maxY = 8;
                    rows = 9;
                    cols = 9;
                }
                case INTERMEDIATE -> {
                    maxX = 15;
                    maxY = 15;
                    rows = 16;
                    cols = 16;
                }
                case EXPERT -> {
                    maxX = 29;
                    maxY = 15;
                    rows = 16;
                    cols = 30;
                }
            }
        }

    }
}
