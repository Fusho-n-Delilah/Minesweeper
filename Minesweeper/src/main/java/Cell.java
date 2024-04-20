public class Cell {
    private int value;
    private boolean mine;
    private boolean revealed;

    Cell(){
        this.value=0;
        this.mine=false;
        this.revealed=false;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
        this.value = -1;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isRevealed() {
        return revealed;
    }
}
