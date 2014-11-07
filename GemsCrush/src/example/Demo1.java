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
         Gem gem[][]=new Gem[8][8];
         for(int i=0;i<8;i++){
             for(int j=0;j<8;j++){
                 gem[i][j]=new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, j);
                 while(j-1>0 && gem[i][j].pic==gem[i][j-1].pic )
                    if(gem[i][j-1].pic==gem[i][j-2].pic)
                         gem[i][j]=new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, j);
                    
             
               while(i-1>0 && gem[i][j].pic==gem[i-1][j].pic)
                  if(gem[i-1][j].pic==gem[i-2][j].pic)
                      gem[i][j]=new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", i, j);
                  
         }
         }
        
      //检查是否左右上下相同
        
        // board dimension can be obtained from console
        int width = console.getBoardWidth();
        int height = console.getBoardHeight();
        
        // set custom background image
        console.setBackground("/assets/board.png");

        //new a timer
        Timer gameTimer = new Timer();
        gameTimer.start();
        String timeDisplay = new String();
        // enter the main game loop
        while (true) {

            // get whatever inputs
            Point point = console.getClickedPoint();
            if (point != null) {
                // determine what is the gem under the click point, toggle it when found
             /*  if (gemBlue.isAt(point)) {
                    gemBlue.toggleFocus();
                } else if (gemRed.isAt(point)) {
                    gemRed.toggleFocus();
                } 
             */
                int position_x = (point.x - 240)/65;
                int position_y = (point.y - 40)/65;
                int position_xy = position_x*10 + position_y;
            /*   switch(position_xy){
                    case 0:{
                        gem00.toggleFocus();
                        break;
                    }
                    case 1:{
                        gem01.toggleFocus();
                        break;
                    }
                    case 2:{
                        gem02.toggleFocus();
                        break;
                    }
                    case 3:{
                        gem03.toggleFocus();
                        break;
                    }
                    case 4:{
                        gem04.toggleFocus();
                        break;
                    }
                    case 5:{
                        gem05.toggleFocus();
                        break;
                    }
                    case 6:{
                        gem06.toggleFocus();
                        break;
                    }
                    case 7:{
                        gem07.toggleFocus();
                        break;
                    }
                }*/
                
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
                console.drawText(60, 280, "220", new Font("Helvetica", Font.PLAIN, 20), Color.white);
                
                
                for(int i=0;i<8;i++)
                    for(int j=0;j<8;j++)
                        gem[i][j].display();
                   
               
       
                console.update();
            }

            // the idle time affects the no. of iterations per second which 
            // should be larger than the frame rate
            // for fps at 25, it should not exceed 40ms
            console.idle(10);
        }
    }
    
    
}
