package src.movingrect;
import java.awt.Color;
 /* In class dar vaghe baraye ejraye yek seri az taghirat ruye safhe namayesh bekar mire
  , az method hash baraye in ke neshun bedim masalan  un shekl ma
  har lahze dare miad paenn ta berese be ye block ya ziresh ye shekl dg bashe ke natune paeen tar bere
  . ya masalan sorat paeen umadan sheklha cheghade ke har dafe ke emtiyaz 100 ta ezafe mishe sorate ejraye bazi bishtar msihe
  . ya masalan method updatesh ke be ma neshun mide har dafe ke yek satr kamel mishe un satro hazf mikone . */

public class screenMap {

    private static final int lineScore = 10;
    public static int status[][] = new int[Main.horCells][Main.verCells];
    public static int color[][] = new int[Main.horCells][Main.verCells];

    public screenMap() {
        for (int i = 0; i < Main.horCells; i++)
            for (int j = 0; j < Main.verCells; j++) {
                color[i][j] = j % 2;
            }
    }

    void shift_down(int row) {
        for (int j = row; j > 0; j--) {
            for (int i = 0; i < 10; i++) {
                status[i][j] = status[i][j - 1];
            }
        }
    }
  //  ya masalan method updatesh ke be ma neshun mide har dafe ke yek satr kamel mishe un satro hazf mikone
    void update()//ijad shakle jadid bad az ezafe shodae ye shekle jadid
     {
        int i, j;
        int row_count = 0;

        for (i = 16; i > 1; i--) {
            boolean flag = true;
            for (j = 0; j < 10; j++) {
                if (status[j][i] == 0) {
                    flag = false;
                }
            }
            if (flag) {
                shift_down(i);
                row_count++; //emtiaz dehi
                Main.totalScore += this.lineScore;
                Main.scoreUp += this.lineScore;
                if (Main.scoreUp >= 100) {
                    Main.scoreUp -= 100;
                    if (Main.speed > 50) Main.speed -= 10;
                }
                i++;
            }

        }
        if (row_count == 3) Main.speed += 4;
        else if (row_count == 2) Main.speed += 2;
        if ((status[3][0] == 1) || (status[4][0] == 1) || (status[5][0] == 1))//noghteye shuru
            Main.game_over = true;
    }

}
