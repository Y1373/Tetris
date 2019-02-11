package src.movingrect;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;


class DrawPanel extends JPanel {

    public void paintComponent(Graphics g){
        int nextStartX, nextStartY;
        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        // Main Window
        g.setColor(Color.GREEN);
        g.drawRect(Main.horStart-2, Main.verStart-2,30*(Main.horCells)+2,30*(Main.verCells)+2); //sabz
        g.setColor(Color.BLUE);
        g.fillRect(Main.horStart-1, Main.verStart-1,30* (Main.horCells)+1,30*Main.verCells+1); //abi....*30 baraye in ast ke tulo arze mostatil chand barabr shavd

        // NEXT Object Window
        nextStartX = Main.horStart + 30*Main.horCells + 50; // yani 150 +30*10+ 50 noghteye shurue next box
        nextStartY = Main.verStart + 40;
        g.setColor(Color.BLUE);
        g.fillRect(nextStartX, nextStartY, 110, 110);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Serif",Font.BOLD+Font.ITALIC,24));
        g.drawString("NEXT", nextStartX+2, nextStartY - 10);

        // SCORE Window
        g.setColor(Color.BLUE);
        g.fillRect(nextStartX, nextStartY + 200, 110, 50);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Serif",Font.BOLD+Font.ITALIC,24));
        g.drawString("SCORE", nextStartX + 2,  nextStartY + 190);

        // Show The Score Value
        String scoreStr = "0000";
        Main.totalScore = Main.totalScore % 10000;
        scoreStr = String.valueOf(Main.totalScore);
        g.setFont(new Font("Serif",Font.BOLD,32));
        g.drawString(scoreStr, nextStartX + 10,  nextStartY + 240);



        // Draw the Next Object
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if (Main.next_object.map[i][j] == 1) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(nextStartX + 30*i + 10, nextStartY + 30*j + 10, 29, 29);
                }
                else {
                    g.setColor(Color.BLUE);
                    g.fillRect(nextStartX + 30*i + 10, nextStartY + 30*j + 10, 29, 29);
                }

            }
        }

        // Draw Main Window Content
        for (int x=0;x<Main.horCells;x++) {
            for (int y=0;y<Main.verCells;y++) {
                if (Main.scrmap.status[x][y] == 1) {
                    g.setColor(Color.RED);
                    g.fillRect(150+30*x, 50+30*y, 29, 29);
                }
            }
        }

        // GAME OVER
        if (Main.game_over) {
            g.setColor(Color.MAGENTA);
            g.fillRect(Main.horStart-40, Main.verStart+30*(Main.verCells-8), 30*(Main.horCells)+80, 180);
            g.setColor(Color.WHITE);
            g.fillRect(Main.horStart-35, Main.verStart+30*(Main.verCells-8)+5, 30*(Main.horCells)+70, 170);
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("Serif",Font.BOLD+Font.ITALIC,56));
            g.drawString("GAME OVER", Main.horStart-20, Main.verStart+30*(Main.verCells-6)+10);
            g.setFont(new Font("Arial",Font.ITALIC,18));

        }
    }
}