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

    // create the game console for drawing         
    // singleton, always return the same instance
    private GameConsole console = GameConsole.getInstance();
    private String score="0";

    public Demo1() {
        // make the console visible
        console.show();
    }

    public static void main(String[] args) {
        // a more OO approach to write the main method
        Demo1 game = new Demo1();
    
       game.startGame();
    }

    public void startGame() {
//randomly generate 8*8 gems to fill up the whole board 
        //Blue = 1, Green = 2, Orange = 3, Purple = 4, Red = 5, White = 6, Yellow = 7
        Random rand = new Random();
        int picNum = rand.nextInt(7)+1;
        boolean upSame = false;
        boolean leftSame = false;
        Gem gem[][]=new Gem[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                gem[i][j]=new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, j);
                //needed to be modified here
                if(i == 0 && j>=1){
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
    }
    private boolean checkRow(Gem [][] gem, int j, int i, int endPlace){
            endPlace = (checkRight(gem,i,j)+1);
            // find that endPlace-j always samller than 2
            if(endPlace-j>2){//here are some errors
                //xiaochu j ~ endPlace
                System.out.println(j);//used for testing
                System.out.println(endPlace);//used for testing
                scoreAdd(endPlace - j);
                Random rand = new Random();
                for(int start = j; start < endPlace; start++){
                    gem[i][start] = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, start);
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
    private int checkRight(Gem[][] gem, int i, int j) {
        int index = j;
        if(j == 7){
            return 7;
        }
        if(checkLeft(gem[i][j],gem[i][j+1])){
            j += 1;
            index = checkRight(gem, i, j);
        }
        return index;
    }
}
