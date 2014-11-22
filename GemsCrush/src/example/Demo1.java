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
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
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
        
//        JFrame startupFrame = new JFrame();    
//        startupFrame.setSize(300, 350);
//        startupFrame.setTitle("!!!**Gem Crush**!!!");
//        startupFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        startupFrame.setLocation(400, 400);
//        startupFrame.setPreferredSize(new Dimension(600, 540));
//        startupFrame.setVisible(true);
//        startupFrame.setLayout(new GridLayout(3, 1, 0, 0));
//        JButton jbt = new JButton("Start ", start);
//        jbt.setBackground(Color.white);
//        startupFrame.add(jbt);
//        
//        ImageIcon Load = new ImageIcon("C:\\Users\\Wu Haotian\\Documents\\GitHub\\JavaProject\\GemsCrush\\src\\assets\\2.png");
//        JButton jbt2 = new JButton("Load ", Load);
//        jbt2.setBackground(Color.white);
//        startupFrame.add(jbt2);
//        
//        ImageIcon Exit = new ImageIcon("C:\\Users\\Wu Haotian\\Documents\\GitHub\\JavaProject\\GemsCrush\\src\\assets\\3.png");
//        JButton jbt3 = new JButton("Exit ", Exit);
//        jbt3.setBackground(Color.white);
//        startupFrame.add(jbt3);
        //jbt.setPreferredSize(new Dimension(100, 100));
        game.startGame();
    }

    public void startGame() {
//randomly generate 8*8 gems to fill up the whole board 
        //Blue = 1, Green = 2, Orange = 3, Purple = 4, Red = 5, White = 6, Yellow = 7
        Random rand = new Random();
        boolean upSame = false;
        boolean leftSame = false;
        
        Image start = new ImageIcon(".\\assets\\Start.png").getImage();
        Image Save = new ImageIcon(".\\assets\\Save.png").getImage();
        Image Load = new ImageIcon(".\\assets\\Load.png").getImage();
        Image Exit = new ImageIcon(".\\assets\\Exit.png").getImage();

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
        String timeDisplay = new String();
        boolean firstClick = false;
        boolean secondClick = false;
        int position_x1 = 0;
        int position_y1 = 0;
        int position_x2 = 0;
        int position_y2 = 0;
        
        boolean startGame = false;
        
        // enter the main game loop
        while (true) {
            // get whatever inputs
            Point point = console.getClickedPoint();
            if(point != null && point.x >= 240 && point.y >= 40 && startGame){
                if (!firstClick) {
                // determine what is the gem under the click point, toggle it when found
                position_y1 = (point.x - 240)/65;
                position_x1 = (point.y - 40)/65;
                gem[position_x1][position_y1].toggleFocus();
                firstClick = true;
                point = null;
                }
                if(firstClick && point != null){
                    position_y2 = (point.x - 240)/65;
                    position_x2 = (point.y - 40)/65;
                    gem[position_x2][position_y2].toggleFocus();
                    secondClick = true;
                }
                if(firstClick && secondClick){
                    if(checkNearGems(position_x1, position_y1, position_x2, position_y2)){
                        //move the two gems 
                    
                        if(position_y1-position_y2==1){  //第一个在第二的右边
                           int i=position_y1*65+240;
                           int j=position_y2 *65+240;
                          while(i>=position_y2*65+240 && j<=position_y1*65+240) {
                                console.drawImage(position_x1*65+40, i,gem[position_x1][position_y1].getPic());
                                console.drawImage(position_x2*65+40, j,gem[position_x2][position_y2].getPic());
                                int m=0;
                                while(m<=1000000){
                                    m++;
                                int n=0;
                                while(n<=1000000)
                                     n++;
                                }
                              
                                i--;
                                j++;
                                
                        }
                        Image temp1 = gem[position_x1][position_y1].getPic();
                      
                        gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                        gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                        }
                        else if(position_y2-position_y1==1){ //一在二的左边
                            int j=position_y1*65+240;
                           int i=position_y2 *65+240;
                          while(i>=position_y1*65+240 && j<=position_y2*65+240) {
                                console.drawImage(position_x1, j,gem[position_x1][position_y1].getPic());
                                console.drawImage(position_x2*65+40, i,gem[position_x2][position_y2].getPic());
                                int m=0;
                                while(m<=1000000){
                                    m++;
                                int n=0;
                                while(n<=1000000)
                                     n++;
                                   
                                
                                }
                               // console.idle(3); 
                                
                                i--;
                                j++;
                        }
                        Image temp1 = gem[position_x1][position_y1].getPic();
                      
                        gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                        gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                        }
                        
                        else if(position_x1-position_x2==1){  //一在二下面
                             int j=position_x1*65+40;
                           int i=position_x2 *65+40;
                          while(i<=position_x1*65+40 && j>=position_x2*65+40) {
                                console.drawImage(j, position_y1*65+240,gem[position_x1][position_y1].getPic());
                                console.drawImage(i,position_y2*65+240,gem[position_x2][position_y2].getPic());
                              int m=0;
                                while(m<=1000000){
                                    m++;
                                int n=0;
                                while(n<=1000000)
                                     n++;
                                   
                                }
                               // console.idle(3); 
                                j--;
                                i++;
                        }
                        Image temp1 = gem[position_x1][position_y1].getPic();
                      
                        gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                        gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                        }
                        
                        else if(position_x2-position_x1==1){ //一在二的上边
                            int j=position_x1*65+40;
                           int i=position_x2 *65+40;
                          while(i>=position_x1*65+40 && j<=position_x2*65+40) {
                                console.drawImage(j,position_y1*65+240,gem[position_x1][position_y1].getPic());
                                console.drawImage(i,position_y2*65+240,gem[position_x2][position_y2].getPic()); 
                                for(int f=0;f<=380;f++)
                 console.drawImage(f, 240,Exit);
                                
                                int m=0;
                                while(m<=1000000){
                                    m++;
                                int n=0;
                                while(n<=1000000)
                                     n++;
                                     
                                }
                                //console.idle(3); 
                                i--;
                                j++;
                        }
                        Image temp1 = gem[position_x1][position_y1].getPic();
                      
                        gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                        gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                        }
                    }
                    else{
                        gem[position_x1][position_y1].toggleFocus();
                        gem[position_x2][position_y2].toggleFocus();
                    }
                    firstClick = false;
                    secondClick = false;
                }
            }else if(point != null && point.x >=30 && point.x <= 180 && point.y >= 300 && point.y <= 380){
                //start clicked
                startGame = true;
                gameTimer.start();
            }else if(point != null && point.x >=30 && point.x <= 180 && point.y >= 360 && point.y <= 440){
                //Save clicked
                
            }else if(point != null && point.x >=30 && point.x <= 180 && point.y >= 420 && point.y <= 500){
                //Load clicked
                
            }else if(point != null && point.x >=30 && point.x <= 180 && point.y >= 480 && point.y <= 560){
                //Exit clicked
                JOptionPane exitPane = new JOptionPane("Click YES to Exit the Game", QUESTION_MESSAGE, YES_NO_OPTION);
                JDialog dialog = exitPane.createDialog("Exit?");
                dialog.show();
                String selectValue = exitPane.getValue().toString();
                if("0".equals(selectValue)){
                    console.clear();
                    console.close();
                    System.exit(0);
                }
                
            }
            
            
            
            // refresh at the specific rate, default 25 fps
            if (console.shouldUpdate()) {
                console.clear();
                
                //change this "00:05:32" to a variable from zero by using Timer.java
                if(startGame){
                    timeDisplay = gameTimer.getTimeString();
                    console.drawText(60, 150, "[TIME]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                    console.drawText(60, 180, timeDisplay, new Font("Helvetica", Font.PLAIN, 20), Color.white);
                    // change the "220" score by a variable that accumulates from 0
                    console.drawText(60, 250, "[SCORE]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                    console.drawText(60, 280, score, new Font("Helvetica", Font.PLAIN, 20), Color.white);
                    
                    for(int i=0;i<8;i++)
                        for(int j=0;j<8;j++){
                            gem[i][j].display();
                        }
                }
                
                
                console.drawImage(30, 300, start);
                console.drawImage(30, 360, Save);
                console.drawImage(30, 420, Load);
                console.drawImage(30, 480, Exit);
              
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
                           int l=y*65+40;
                           int u=(y-1) *65+40;
                           
                          while(u<=l) {
                                console.drawImage(u, start*65+240,gem[y-1][start].getPic());
                          
                               int m=0;
                                while(m<=1000000){
                                    m++;
                                int n=0;
                                while(n<=1000000)
                                    n++;
                                }
                                u++;
                        }
                        gem[y][start] = new Gem(gem[y-1][start].getPic(), y,start);
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
                
               
                           int l=start*65+40;
                           int u=(start-count) *65+40;
                          while(u<=l) {
                                console.drawImage(u, j*65+240,gem[start-count][j].getPic());
                                     
                                     int m=0;
                                while(m<=1000000){
                                    m++;
                                int n=0;
                                while(n<=1000000)
                                    n++;
                                }
                                u++;
                        }
                        gem[start][j]=new Gem(gem[start-count][j].getPic(),start,j);
           
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
