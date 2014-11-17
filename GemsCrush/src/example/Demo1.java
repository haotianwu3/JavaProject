/*
 * Project Name: EE2311 Project - Gems Crush
 * Student Name:
 * Student ID:
 * 
 */

package example;

import game.GameConsole;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;
import javax.swing.JButton;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/**
 * Demo for the use of:
 * 
 * 1. create and display game console
 * 2. start a game loop
 * 3. create and display gems
 * 4. detect mouse click and toggle gem's selection
 * 5. update screen at predefined interval
 * 6. draw text to show score/time information
 *  
 * @author Dr. Ray
 */
public class Demo1 {
    private GameConsole console = GameConsole.getInstance();
    private String score="0";
    public Demo1() {
        // make the console visible
        console.show();
    }
    public static void main(String[] args) throws IOException {
        Sound backGround = new Sound("/assets/New_LAN_2.wav");
        backGround.playSound();
        // a more OO approach to write the main method
        
        Demo1 game = new Demo1();
        
        JFrame startupFrame = new JFrame();    
        startupFrame.setSize(300, 350);
        startupFrame.setTitle("!!!**Gem Crush**!!!");
        startupFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        startupFrame.setLocation(400, 400);
        startupFrame.setPreferredSize(new Dimension(600, 540));
        startupFrame.setVisible(true);
        startupFrame.setLayout(new GridLayout(3, 1, 0, 0));
        
        ImageIcon start = new ImageIcon("C:\\Users\\Wu Haotian\\Documents\\GitHub\\JavaProject\\GemsCrush\\src\\assets\\1.png");
        JButton jbt = new JButton("Start ", start);
        jbt.setBackground(Color.white);
        startupFrame.add(jbt);
        
        ImageIcon Load = new ImageIcon("C:\\Users\\Wu Haotian\\Documents\\GitHub\\JavaProject\\GemsCrush\\src\\assets\\2.png");
        JButton jbt2 = new JButton("Load ", Load);
        jbt2.setBackground(Color.white);
        startupFrame.add(jbt2);
        
        ImageIcon Exit = new ImageIcon("C:\\Users\\Wu Haotian\\Documents\\GitHub\\JavaProject\\GemsCrush\\src\\assets\\3.png");
        JButton jbt3 = new JButton("Exit ", Exit);
        jbt3.setBackground(Color.white);
        startupFrame.add(jbt3);
        //jbt.setPreferredSize(new Dimension(100, 100));
        

        game.startGame();
    }

    public void startGame() {
//randomly generate 8*8 gems to fill up the whole board 
        //Blue = 1, Green = 2, Orange = 3, Purple = 4, Red = 5, White = 6, Yellow = 7
        Random rand = new Random();
        boolean upSame = false;
        boolean leftSame = false;
       
       
        Gem gem[][]=new Gem[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                gem[i][j]=new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, j);
                if(i == 0 && j>=1 ){
                    leftSame = checkLeft(gem[i][j],gem[i][j-1]);
                }
                if(i >= 1 && j == 0){
                    upSame = checkUp(gem[i][j], gem[i-1][j]);
                }
                if(i >= 1 && j >= 1){
                    leftSame = checkLeft(gem[i][j],gem[i][j-1]);
                    upSame = checkUp(gem[i][j], gem[i-1][j]);
                }
                
                while(leftSame){
                    gem[i][j]=new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, j);
                    leftSame = checkLeft(gem[i][j],gem[i][j-1]);
                }
                while(upSame){
                    gem[i][j]=new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, j);
                    upSame = checkUp(gem[i][j], gem[i-1][j]);
                }
            }
        }
        
         
        // board dimension can be obtained from console
        int width = console.getBoardWidth();
        int height = console.getBoardHeight();
        
        // set custom background image
        console.setBackground("/assets/board.png");

        //new a timer
        Timer gameTimer = new Timer();
        gameTimer.start();
        String timeDisplay = new String();
        boolean firstClick = false;
        boolean secondClick = false;
        int position_x1 = 0;
        int position_y1 = 0;
        int position_x2 = 0;
        int position_y2 = 0;
        // enter the main game loop
        while (true) {
            // get whatever inputs
            Point point = console.getClickedPoint();
            if (point != null && !firstClick) {
                // determine what is the gem under the click point, toggle it when found
                position_y1 = (point.x - 240)/65;
                position_x1 = (point.y - 40)/65;
                gem[position_x1][position_y1].toggleFocus();
                firstClick = true;
                point = null;
            }
            if(point != null && firstClick ){
                position_y2 = (point.x - 240)/65;
                position_x2 = (point.y - 40)/65;
                gem[position_x2][position_y2].toggleFocus();
                secondClick = true;
            }
            if(firstClick && secondClick){
                if(checkNearGems(position_x1, position_y1, position_x2, position_y2)){
                    //move the two gems 
                    gem[position_x1][position_y1].moveTo(position_x2, position_y2);
                    gem[position_x2][position_y2].moveTo(position_x1, position_y1);
                    //assign the corresponding gems to corresponding array cells
                    Image temp1 = gem[position_x1][position_y1].getPic();
                    gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                    gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                }
                else{
                    gem[position_x1][position_y1].toggleFocus();
                    gem[position_x2][position_y2].toggleFocus();
                }
                firstClick = false;
                secondClick = false;
            }
            
            // refresh at the specific rate, default 25 fps
            if (console.shouldUpdate()) {
                console.clear();
                
                //change this "00:05:32" to a variable from zero by using Timer.java
                timeDisplay = gameTimer.getTimeString();
                console.drawText(60, 150, "[TIME]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                console.drawText(60, 180, timeDisplay, new Font("Helvetica", Font.PLAIN, 20), Color.white);
                // change the "220" score by a variable that accumulates from 0
                console.drawText(60, 250, "[SCORE]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                console.drawText(60, 280, score, new Font("Helvetica", Font.PLAIN, 20), Color.white);
                
                for(int i=0;i<8;i++)
                    for(int j=0;j<8;j++)
                        gem[i][j].display();
                elimination(gem);
                console.update();
            }

            // the idle time affects the no. of iterations per second which 
            // should be larger than the frame rate
            // for fps at 25, it should not exceed 40ms
            console.idle(10);
        }
    }
    private boolean checkLeft(Gem gem1, Gem gem2) {
        if(gem1.getPic() == gem2.getPic()){
            return true;
        }
        return false;
    }

    private boolean checkUp(Gem gem1, Gem gem2) {
        if(gem1.getPic() == gem2.getPic()){
            return true;
        }
        return false;
    }

    private boolean checkNearGems(int x1, int y1, int x2, int y2) {
        if((x2-1) == x1 && y1 == y2){
            return true;
        }
        else if((x1-1) == x2 && y1 == y2){
            return true;
        }
        else if((y1-1) == y2 && x1 == x2){
            return true;
        }
        else if((y2-1) == y1 && x1 ==x2){
            return true;
        }
        else{
            return false;
        }
    }
    
    private String scoreAdd(int x){
        Sound matchAndScore = new Sound("/assets/match.wav");
        matchAndScore.playSound();
        int score1=Integer.parseInt(score);
        score1=score1+10*x;
        score = Integer.toString(score1);
        return score;
    }

    private void elimination(Gem[][] gem) {
        for(int i=0;i<8;i++){
            int endPlace = 0;
            int j = 0;
           boolean finishRow = checkRow(gem, j, i, endPlace);
        }
        for(int i=0;i<8;i++){
            int endPlace = 0;
            int j = 0;
         boolean finishCol = checkCol(gem, i,j, endPlace);
        }
    }
    private boolean checkRow(Gem [][] gem, int j, int i, int endPlace){
            endPlace = (checkRight(gem,i,j)+1);
            if(endPlace-j>2){
                scoreAdd(endPlace - j);
                Random rand = new Random();
                //fall from top
                for(int start = j; start < endPlace; start++){
                    for(int y=i;y>0;y--){
                       gem[y-1][start].moveTo(y, start);
                       gem[y][start]=new Gem(gem[y-1][start].getPic(),gem[y-1][start].getPosX(),gem[y-1][start].getPosY());
                    }
                    gem[0][start] = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0,start);
                }
            }
            else{
                if(endPlace>=7){
                    return true;
                }
                j = endPlace;
                checkRow(gem, j, i, endPlace);
            }
        return false;
    }
    private boolean checkCol(Gem[][]gem,int j,int i,int endPlace){
        endPlace=(checkDown(gem,i,j)+1);
        int count=endPlace-i;
        if(endPlace-i>2){
            scoreAdd(endPlace-i);
            Random rand = new Random();
          //fall down
            for(int start=endPlace-1;start>=count;start--){
                       gem[start-count][j].moveTo(start,j);
                       gem[start][j]=new Gem(gem[start-count][j].getPic(),gem[start-count][j].getPosX(),gem[start-count][j].getPosY());
            }
            for(int start=count-1;start>=0;start--)
                gem[start][j] = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", start,j);
            }
            
        else{
            if(endPlace>=7){
                return true;
            }
            i=endPlace;
            checkCol(gem,j,i,endPlace);
        }
        return false;
    }
    private int checkRight(Gem[][] gem, int i, int j) {
        int index = j;
        if(j == 7){
            index=7;
           return 7;
        }
        if(checkLeft(gem[i][j],gem[i][j+1])){
            j += 1;
            index = checkRight(gem, i, j);
        }
        return index;
    }
    private int checkDown(Gem[][] gem,int i,int j){
        int index=i;
        if(i==7){
            index=7;
            return 7;
        }
        if(checkUp(gem[i+1][j],gem[i][j])){
           i+=1;
           index=checkDown(gem,i,j);
        }
        return index;
    }
}
