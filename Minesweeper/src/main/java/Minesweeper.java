import java.awt.event.MouseEvent;

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

        GameEngine(Difficulty difficulty){
            GameEngine.difficulty = difficulty;
            viewModel = new ViewBoard(difficulty);
            gameBoard = new Board(difficulty);
            view = new GUI(difficulty);
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
            if(clickType == MouseEvent.BUTTON3){
                flagCell(y,x);
            } else {
                if(gameBoard.isWin()){
                    setWin();
                } else if (gameBoard.isMine(y,x)) {
                    setLose();
                } else {
                    int value = gameBoard.getValue(y,x);
                    gameBoard.revealCell(y,x);
                    if (value == 0){
                        viewModel.setRevealed(y,x,true);
//                        clearBlankCells(y,x);
                    } else {
                        viewModel.setRevealed(y,x,true);
                        viewModel.setValue(y,x, String.valueOf(value));
                    }
                }
            }
            view.displayBoard(viewModel);
        }
        private void setCellValue(int y,int x, String value){
            viewModel.setValue(y, x, value);
        }
        private static void flagCell(int y, int x){
            viewModel.setFlagged(y, x, !viewModel.isFlagged(y, x));
        }
        private static void clearBlankCells(int y, int x){
            //set the cell as revealed on the viewModel and on the data model
            //reveal all adjacent cells and the next immediately adjacent number

        }
        private static void setWin(){
            viewModel.setGameState("WON");
            //reveal all cells except flagged cells, flag all mines, and set the value of all cells
            view.displayBoard(viewModel);
        }
        private static void setLose(){
            viewModel.setGameState("LOST");
            //reveal all cells, unflag all cells, and set value of all cells "M" for the mines
            view.displayBoard(viewModel);
        }
        private static void setRunning(){
            viewModel.setGameState("RUNNING");
        }
        private static void resetGame(Difficulty difficulty) {
            setRunning();
        }

    }
}
