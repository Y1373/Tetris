package src.movingrect;
import java.util.Random;


public class FallingObject {
    int x, y;
    int rnd_map;
    private int type;
    public int map[][] = new int[3][3];
    public int score;
    //method karesh sakhtan yek shekl jadide
    private static final Random randomNumbers = new Random();
    //sakhtane shekle jadid
    public FallingObject(int xinit, int yinit) {
        type = randomNumbers.nextInt(7);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                map[i][j] = Main.object_map[type][i][j];

        score = Main.objectScore[type];

        x = xinit;
        y = yinit;
    }
     //harkat kardan dar do halat shekl ma be samte paeen harkat miikone ,
     // ya bayad ziresh khali bash eke tavasote methode blowIsFree check mishe
     // ya bayad dar ebtedaie tarin sath yani balatarin satr barname bashe (unja ke mige y=-1)
    public boolean move() {
        if (!below_is_free()) {

            Main.totalScore += this.score;
            // Baraaye sari-tarkardane harekat ba'd az har 100 emtiaz
            Main.scoreUp += this.score;
            if (Main.scoreUp >= 100) {
                Main.scoreUp -= 100;
                if (Main.speed > 50) Main.speed -= 10;
            }
            return false;
        } else if (y == -1) {
            y++;
            for (int i = 0; i < 3; i++)
                for (int j = 2; j >= 0; j--) {
                    if (map[i][j] == 1) {
                        Main.scrmap.status[x + i][y + j] = 1;

                    }
                }
        } else {
            y++;
            for (int i = 0; i < 3; i++)
                for (int j = 2; j >= 0; j--) {
                    if (map[i][j] == 1) {
                        Main.scrmap.status[x + i][y + j] = 1;
                        Main.scrmap.status[x + i][y + j - 1] = 0;

                    }
                }
        }
        return true;
    }
    //baraye check kardan in e ke aya shekl ma ghader be harkat chap o rast hast ya kheyr
    // . dar in surat age bashe mogheytaesh ro dar screen map taghir mide . shartesham hamontor ke comment hast ye
    //if o else dare ke ba un chek mikone chap o rastesh khali bashe ta betune shek be unja harkat kone .
    public void move_side(int side)
    // MOVE_RIGHT: side = 1, MOVE_LEFT: side = -1
    {
        if (y < 0) return;
        if (!side_is_free(side)) return;

        if (side == +1) {
            x++;
            for (int j = 0; j < 3; j++)
                for (int i = 2; i >= 0; i--) {
                    if (map[i][j] == 1) {
                        Main.scrmap.status[x + i][y + j] = 1;
                        Main.scrmap.status[x + i - 1][y + j] = 0;

                    }
                }
        } else {     // if (side==-1):LEFT
            x--;
            for (int j = 0; j < 3; j++)
                for (int i = 0; i < 3; i++) {
                    if (map[i][j] == 1) {
                        Main.scrmap.status[x + i][y + j] = 1;
                        Main.scrmap.status[x + i + 1][y + j] = 0;

                    }
                }
        }
    }
     //chek mikone ke paeene In shekli ke mikhay alan tekunesh bedi aslan khali hast ya na
     //ke badesh hala be fekre update kardane screen map o ina bashim
     //. dar mrede in method chand khat baatar goftim ke chi ova chetor checkm mikone
    private boolean below_is_free() {
        for (int i = 0; i < 3; i++) {
            for (int j = 2; j >= 0; j--) {
                if (map[i][j] == 0) continue;
                else if (y + j == Main.verCells - 1)
                    return false;
                else if (Main.scrmap.status[x + i][y + j + 1] == 1)
                    return false;
                else
                    break;

            }
        }
        return true;
    }
    //miad khali budan kenarehaye samte chap o rast ro Barresi mikone
    //va agar khali bashe be method balaie ke vase harkat be tarafeyn bud va dar mredesh sohbat kardim ejaze harkat mide .
    private boolean side_is_free(int side) {
        if (side == +1) { //Right side
            for (int j = 0; j < 3; j++) {
                for (int i = 2; i >= 0; i--) {
                    if (map[i][j] == 0) continue;
                    else if (x + i == Main.horCells - 1)
                        return false;
                    else if (Main.scrmap.status[x + i + 1][y + j] == 1)
                        return false;
                    else // if map[i][j]==1 and its right is free
                        break;
                }
            }
            return true;
        } else {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    if (map[i][j] == 0) continue;
                    else if (x + i == 0)
                        return false;
                    else if (Main.scrmap.status[x + i - 1][y + j] == 1)
                        return false;
                    else
                        break;
                }
            }
            return true;
        }
    }
   //akharesh baraye charkhesh tasvire vaghti dar bazi felesh bala ro bezanim shekl ma micharkhe az in method estefade mishe .
   // in method miad har khune az un matrsi ke tasvire ma daresh gharar dare be ye mohktasate jadid negasht mikone
   //, intori mitunim begim ke tasvire ma charkhide . va badesh kea male charkhesh ro anjam dad sceern map ro update mikone .
    public void rotate() {
        if (y < 0) return;
        if (this.type == 2) return; // don't rotate the square objects
        int next_map[][] = new int[3][3];
        next_map[0][2] = map[0][0];
        next_map[0][0] = map[2][0];
        next_map[2][0] = map[2][2];
        next_map[2][2] = map[0][2];
        next_map[1][1] = map[1][1];
        next_map[0][1] = map[1][0];
        next_map[1][0] = map[2][1];
        next_map[2][1] = map[1][2];
        next_map[1][2] = map[0][1];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((map[i][j] == 0) && (next_map[i][j] == 1)) {
                    if ((x + i < 0) && this.side_is_free(1)) {
                        move_side(1);
                    } else if (x + i < 0) return;

                    if ((x + i < 0) && this.side_is_free(1)) {
                        move_side(1);
                    } else if (x + i < 0) return;

                    if ((x + i >= Main.horCells) && this.side_is_free(-1)) {
                        move_side(-1);
                    } else if (x + i >= Main.horCells) return;

                    if ((x + i >= Main.horCells) && this.side_is_free(-1)) {
                        move_side(-1);
                    } else if (x + i >= Main.horCells) return;

                    if (y + j >= Main.verCells) return;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((map[i][j] == 0) && (next_map[i][j] == 1)) {
                    if (Main.scrmap.status[x + i][y + j] == 1) return;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((map[i][j] == 1) && (next_map[i][j] == 0))
                    Main.scrmap.status[x + i][y + j] = 0;
                else if ((map[i][j] == 0) && (next_map[i][j] == 1))
                    Main.scrmap.status[x + i][y + j] = 1;
            }
        }

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                map[i][j] = next_map[i][j];
    }
}
