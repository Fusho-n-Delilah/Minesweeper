
public class Minesweeper {
    public static void main (String... args){
        Board game = new Board("EASY");
        new GUI("EASY");

        //tests
        int count = 0;
        int mines = 0;
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(game.isMine(i,j)) {
                    System.out.println(game.isMine(i, j));
                    mines++;
                } else {
                    System.out.println(game.getValue(i,j));
                }
                count++;
            }
        }
        System.out.println(count);
        System.out.println(mines);
    }
}
