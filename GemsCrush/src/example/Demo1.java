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
        Gem gem00 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 0);
        Gem gem01 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 1);
        Gem gem02 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 2);
        Gem gem03 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 3);
        Gem gem04 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 4);
        Gem gem05 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 5);
        Gem gem06 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 6);
        Gem gem07 = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0, 7);
        Gem gemBlue = new Gem("/assets/"+ picNum + ".png", 2, 2);
        Gem gemRed = new Gem("/assets/" + picNum + ".png", 3, 3);
        
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
                if (gemBlue.isAt(point)) {
                    gemBlue.toggleFocus();
                } else if (gemRed.isAt(point)) {
                    gemRed.toggleFocus();
                } 
                int position_x = (point.x - 240)/65;
                int position_y = (point.y - 40)/65;
                int position_xy = position_x*10 + position_y;
                switch(position_xy){
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
                }
                
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
                
                gemBlue.display();//display the current position of this gem
                gemRed.display();
                gem00.display();
                gem01.display();
                gem02.display();
                gem03.display();
                gem04.display();
                gem05.display();
                gem06.display();
                gem07.display();

                console.update();
            }

            // the idle time affects the no. of iterations per second which 
            // should be larger than the frame rate
            // for fps at 25, it should not exceed 40ms
            console.idle(10);
        }
    }
}
