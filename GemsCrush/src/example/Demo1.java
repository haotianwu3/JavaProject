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
        
//randomly generate 8*8 gems to fill up the whole board yuyue
        Gem gemBlue = new Gem("/assets/gemBlue.png", 2, 2);
        Gem gemRed = new Gem("/assets/gemRed.png", 3, 3);

        // board dimension can be obtained from console
        int width = console.getBoardWidth();
        int height = console.getBoardHeight();
        
        // set custom background image
        console.setBackground("/assets/board.png");

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
            }

            // refresh at the specific rate, default 25 fps
            if (console.shouldUpdate()) {
                console.clear();
                //change this "00:05:32" to a variable from zero by using Timer.java
                console.drawText(60, 150, "[TIME]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                console.drawText(60, 180, "00:05:32", new Font("Helvetica", Font.PLAIN, 20), Color.white);
                // change the "220" score by a variable that accumulates from 0
                console.drawText(60, 250, "[SCORE]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                console.drawText(60, 280, "220", new Font("Helvetica", Font.PLAIN, 20), Color.white);
                
                gemBlue.display();//display the current position of this gem
                gemRed.display();

                console.update();
            }

            // the idle time affects the no. of iterations per second which 
            // should be larger than the frame rate
            // for fps at 25, it should not exceed 40ms
            console.idle(10);
        }
    }
}
