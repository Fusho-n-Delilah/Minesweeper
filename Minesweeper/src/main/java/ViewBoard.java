import java.awt.Color;

public class ViewBoard {

    private enum state {WON, LOST, RUNNING};
    private state gameState;
    private Difficulty difficulty;
//    private Color[] colors;
    private ViewCell[][] board;
    private int flags;
//    private int maxX, maxY;

    ViewBoard(Difficulty difficulty){
        this.difficulty = difficulty;
        this.gameState = state.RUNNING;
//        setColors();

        switch (difficulty) {
            case EASY -> {
//                this.maxX = 8;
//                this.maxY = 8;
                this.board = new ViewCell[9][9];
                initializeCells(9, 9);
            }
            case INTERMEDIATE -> {
//                this.maxX = 15;
//                this.maxY = 15;
                this.board = new ViewCell[16][16];
                initializeCells(16, 16);
            }
            case EXPERT -> {
//                this.maxX = 29;
//                this.maxY = 15;
                this.board = new ViewCell[16][30];
                initializeCells(16, 30);
            }
        }
    }

    public String getGameState() {
        return gameState.name();
    }
    public void setGameState(String changeState) {
        state newState = state.RUNNING;

        switch(changeState){
            case "WON" -> newState = state.WON;
            case "LOST" -> newState = state.LOST;
            case "RUNNING" -> newState = state.RUNNING;
        }
        this.gameState = newState;
    }
    public boolean isFlagged(int y, int x){
        return this.board[y][x].getFlagged();
    }
    public boolean isRevealed(int y, int x){
        return this.board[y][x].getRevealed();
    }
    public String getValue(int y, int x){ return this.board[y][x].getValue();}
//    public Color getColor(int value){
//        return colors[value];
//    }
    public void setFlagged(int y, int x, boolean bool){ this.board[y][x].setFlagged(bool);}
    public void setRevealed(int y, int x, boolean bool){
        this.board[y][x].setRevealed(bool);
    }
    public void setValue(int y, int x, String value){
        this.board[y][x].setValue(value);
    }
//    public void setColor (int y, int x, int value){
//        this.board[y][x].setColor(value);
//    }
    private void initializeCells(int rows, int cols){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                this.board[i][j] = new ViewCell();
            }
        }
    }
//    private void setColors() {
//        this.colors = new Color[9];
//
//        this.colors[0] = Color.LIGHT_GRAY;
//        this.colors[1] = Color.BLUE;
//        this.colors[2] = Color.GREEN;
//        this.colors[3] = Color.RED;
//        this.colors[4] = new Color(0,0,128);
//        this.colors[5] = new Color(128,0,0);
//        this.colors[6] = Color.CYAN;
//        this.colors[7] = new Color(128,128,0);
//        this.colors[8] = Color.MAGENTA;
//    }

    private class ViewCell {
        private String value;
        private Boolean flagged;
        private Boolean revealed;
//        private Color color;

        ViewCell() {
            this.value = " ";
            this.flagged = false;
            this.revealed = false;
//            this.color = Color.LIGHT_GRAY;
        }
        public Boolean getFlagged() {
            return this.flagged;
        }

        public Boolean getRevealed() {
            return this.revealed;
        }
        public String getValue(){
            return this.value;
        }

//        public void setColor(int value) {
//            this.color = colors[value];
//        }

        public void setFlagged(Boolean flagged) {
            this.flagged = flagged;
        }

        public void setRevealed(Boolean revealed) {
            this.revealed = revealed;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
