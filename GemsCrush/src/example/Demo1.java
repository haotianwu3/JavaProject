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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

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
    private final GameConsole console = GameConsole.getInstance();
    private String score="0";
    public Demo1() {
        // make the console visible
        console.show();
    }
    public static void main(String[] args) throws IOException {
        //play Background Music 
        Sound backGround = new Sound("/assets/New_LAN_2.wav");
        backGround.playSound();
        // a more OO approach to write the main method
        Demo1 game = new Demo1();
        game.startGame();
    }

    public void startGame() {
        //Four buttons
        Image start = new ImageIcon(".\\assets\\Start.png").getImage();
        Image Save = new ImageIcon(".\\assets\\Save.png").getImage();
        Image Load = new ImageIcon(".\\assets\\Load.png").getImage();
        Image Exit = new ImageIcon(".\\assets\\Exit.png").getImage();
        //randomly generate 8*8 gems to fill up the whole board 
        Gem gem[][]=new Gem[8][8];
        initialGem(gem);
        // set custom background image
        console.setBackground("/assets/board.png");
        //new a timer
        GameTimer gameTimer = new GameTimer();
        String timeDisplay = new String();
        //serve for clicking
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
            // Four Buttons determination
            if(point != null && point.x >= 240 && point.y >= 40 && startGame){
                //determine gem clicks
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
                        Image temp1 = gem[position_x1][position_y1].getPic();
                        gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                        gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                        }
                        else if(position_y2-position_y1==1){ //一在二的左边
                        Image temp1 = gem[position_x1][position_y1].getPic();
                        gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                        gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                        }
                        
                        else if(position_x1-position_x2==1){  //一在二下面
                        Image temp1 = gem[position_x1][position_y1].getPic();
                        gem[position_x1][position_y1] = new Gem(gem[position_x2][position_y2].getPic(), position_x1, position_y1);
                        gem[position_x2][position_y2] = new Gem(temp1, position_x2, position_y2);
                        }
                        
                        else if(position_x2-position_x1==1){ //一在二的上边
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
            }else if(point != null && point.x >=30 && point.x <= 180 && point.y >= 360 && point.y <= 440 && startGame == true){
                //Save clicked
                File out = new File("GameLog.txt");
                FileWriter writer = null;
                try{
                    writer = new FileWriter(out);
                    writer.write(score + "\r\n");
                    Image pic1 = new ImageIcon(this.getClass().getResource("/assets/1.png")).getImage();
                    Image pic2 = new ImageIcon(this.getClass().getResource("/assets/2.png")).getImage();
                    Image pic3 = new ImageIcon(this.getClass().getResource("/assets/3.png")).getImage();
                    Image pic4 = new ImageIcon(this.getClass().getResource("/assets/4.png")).getImage();
                    Image pic5 = new ImageIcon(this.getClass().getResource("/assets/5.png")).getImage();
                    Image pic6 = new ImageIcon(this.getClass().getResource("/assets/6.png")).getImage();
                    Image pic7 = new ImageIcon(this.getClass().getResource("/assets/7.png")).getImage();
                    for(int i=0;i<8;i++){
                        for(int j=0;j<8;j++){
                            Image temp = gem[i][j].getPic();
                            if(temp == pic1){
                                writer.write("1 ");
                            }else if(temp == pic2){
                                writer.write("2 ");
                            }
                            else if(temp == pic3){
                                writer.write("3 ");
                            }
                            else if(temp == pic4){
                                writer.write("4 ");
                            }
                            else if(temp == pic5){
                                writer.write("5 ");
                            }
                            else if(temp == pic6){
                                writer.write("6 ");
                            }
                            else if(temp == pic7){
                                writer.write("7 ");
                            }
                        }
                        writer.write("\r\n");
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Successful!!!", "Save", JOptionPane.INFORMATION_MESSAGE);
                }catch(IOException e){
                    System.out.println("IO Error.\r\n" + e);
                } 
            }else if(point != null && point.x >=30 && point.x <= 180 && point.y >= 420 && point.y <= 500){
                //Load clicked
                startGame = true;
                gameTimer.start();
                File in = new File("GameLog.txt");
                Scanner scanner = null; 
                try{
                    scanner = new Scanner(in);
                    score = scanner.nextLine();
                    while(scanner.hasNext()){
                        for(int i=0;i<8;i++){
                            for(int j=0;j<8;j++){
                                String temp = scanner.next();
                                if("1".equals(temp)){
                                    gem[i][j].setPic("/assets/1.png");
                                }else if("2".equals(temp)){
                                    gem[i][j].setPic("/assets/2.png");
                                }
                                else if("3".equals(temp)){
                                    gem[i][j].setPic("/assets/3.png");
                                }
                                else if("4".equals(temp)){
                                    gem[i][j].setPic("/assets/4.png");
                                }
                                else if("5".equals(temp)){
                                    gem[i][j].setPic("/assets/5.png");
                                }
                                else if("6".equals(temp)){
                                    gem[i][j].setPic("/assets/6.png");
                                }
                                else if("7".equals(temp)){
                                    gem[i][j].setPic("/assets/7.png");
                                }
                            }
                        }
                    }
                    scanner.close();
                }catch(IOException e){
                    System.out.println("IO Error.\r\n" + e);
                }
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
                if(startGame){
                    timeDisplay = gameTimer.getTimeString();
                    console.drawText(60, 150, "[TIME]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                    console.drawText(60, 180, timeDisplay, new Font("Helvetica", Font.PLAIN, 20), Color.white);
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
        Image Bomb = new ImageIcon(this.getClass().getResource("/assets/boom.png")).getImage();
        if(gem1.getPic() != Bomb && gem2.getPic() != Bomb){
            if(gem1.getPic() == gem2.getPic()){
                return true;
            }
        }
        return false;
    }

    private boolean checkUp(Gem gem1, Gem gem2) {
        Image Bomb = new ImageIcon(this.getClass().getResource("/assets/boom.png")).getImage();
        if(gem1.getPic() != Bomb && gem2.getPic() != Bomb){
            if(gem1.getPic() == gem2.getPic()){
                return true;
            }
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
        else return (y2-1) == y1 && x1 ==x2;
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
    private boolean checkRow(final Gem [][] gem, int j, int i, int endPlace){
            endPlace = (checkRight(gem,i,j)+1);
            int count = endPlace - j;
            if(endPlace-j>2){
                
                scoreAdd(count);
                //use timer to display bomb image
                delayRow(i,j,endPlace,gem, count);
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
        if(count>2){
            scoreAdd(count);
            delayCol(i, j, endPlace, gem, count);
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

    private void delayRow(final int i, final int j, final int endPlace, final Gem[][] gem, int num) {
        //then change the image again
        //fall from top
        final int begin = j;
        final int x = i;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gem[x][begin].setPic("/assets/boom.png");
                gem[x][begin].display();
            }
        }, 0);
        num--;
        if(num > 0){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[x][begin+1].setPic("/assets/boom.png");
                    gem[x][begin+1].display();
                }
            }, 30);
        }
        num--;
        if(num > 0){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[x][begin+2].setPic("/assets/boom.png");
                    gem[x][begin+2].display();
                }
            }, 60);
        }
        num--;
        if(num > 0){ // means eliminate 4 gems
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[x][begin+3].setPic("/assets/boom.png");
                    gem[x][begin+3].display();
                }
            }, 90);
        }
        num--;
        if(num > 0){ // means elimation 5 gems
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[x][begin+4].setPic("/assets/boom.png");
                    gem[x][begin+4].display();
                }
            }, 120);
        }
        dropRow(i, j, endPlace, gem);                
    }

    private void dropRow(final int i, final int j, final int endPlace, final Gem[][] gem) {
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask(){
            @Override
            public void run() {
                Random rand = new Random();
                for(int start = j; start < endPlace; start++){
                    for(int v=i;v>0;v--){
                        Sound fallRow = new Sound("/assets/fall.wav");
                        fallRow.playSound();
                        gem[v][start] = new Gem(gem[v-1][start].getPic(), v,start);
                    }
                    gem[0][start] = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", 0,start);
                }
            }
        }, 600);
        

    }

    private void delayCol(int i, int j, int endPlace, final Gem[][] gem, int count) {
        final int y = j;
        final int begin = i;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gem[begin][y].setPic("/assets/boom.png");
                gem[begin][y].display();
            }
        }, 0);
        count--;
        if(count > 0){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[begin+1][y].setPic("/assets/boom.png");
                    gem[begin+1][y].display();
                }
            }, 30);
        }
        count--;
        if(count > 0){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[begin+2][y].setPic("/assets/boom.png");
                    gem[begin+2][y].display();
                }
            }, 60);
        }
        count--;
        if(count > 0){ // means eliminate 4 gems
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[begin+3][y].setPic("/assets/boom.png");
                    gem[begin+3][y].display();
                }
            }, 90);
        }
        count--;
        if(count > 0){ // means elimation 5 gems
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gem[begin+4][y].setPic("/assets/boom.png");
                    gem[begin+4][y].display();
                }
            }, 120);
        }
        
        dropCol(i, j, endPlace, gem);
        
    }

    private void dropCol(final int i, final int j, final int endPlace, final Gem[][] gem) {
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask(){
            @Override
            public void run() {
                Random rand = new Random();
                int count = endPlace -i;
                for(int start = endPlace -1; start >= count;start--){
                    Sound fallCol = new Sound("/assets/fall.wav");
                    fallCol.playSound();
                    gem[start][j]=new Gem(gem[start-count][j].getPic(),start,j);
                }
                for(int start = count-1; start >= 0; start--){
                    Sound fallCol = new Sound("/assets/fall.wav");
                    fallCol.playSound();
                    gem[start][j] = new Gem("/assets/"+ (rand.nextInt(7)+1) + ".png", start,j);
                }
            }
        }, 600);
    }

    private void initialGem(Gem[][] gem) {
        Random rand = new Random();
        boolean upSame = false;
        boolean leftSame = false;
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
    }
}
