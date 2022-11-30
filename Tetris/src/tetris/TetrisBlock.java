package tetris;

import java.awt.*;
import java.util.Random;

public class TetrisBlock {

    private Color [] availableColors = {Color.red, Color.yellow, Color.blue, Color.green, Color.magenta, Color.orange};
    private int [][] shape;
    private Color color;
    private int x;
    private int y;
    public TetrisBlock (){
        Random r = new Random();
        shape = availableShapes[r.nextInt(availableShapes.length)];
    }
    public void spawnBlock(int gridWidth){
        Random r = new Random();

        y=-this.getHeight();
        x=r.nextInt(gridWidth - getWidth()-1);

        color = availableColors[r.nextInt(availableColors.length)];
    }
    public void rotate(){
        int [][] rotatedShape = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                rotatedShape[j][i] = shape[shape.length-i-1][j];
            }
        }
        shape = rotatedShape;
    }
    public int[][] getShape(){return shape;}
    public void setShape(int[][] shape){this.shape = shape;}
    public Color getColor(){return color;}
    public int getHeight(){return shape.length;}
    public int getWidth(){return shape[0].length;}
    public int getX(){return x;}
    public int getY(){return y;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public void fall(){y++;}
    public void moveRight(){x++;}
    public void moveLeft(){x--;}
    public int getBottomEdge(){return (y+getHeight()); }
    public int getRightEdge(){return (getWidth()+x);}

    private int [][][] availableShapes =
            {       {
                            {1},
                            {1},
                            {1},
                            {1}
                    },
                    {
                            {0,1},
                            {0,1},
                            {1,1}
                    },
                    {
                            {1,0},
                            {1,0},
                            {1,1}
                    },
                    {
                            {1,1},
                            {1,1}
                    },
                    {
                            {1,1,0},
                            {0,1,1}
                    },
                    {
                            {0,1,1},
                            {1,1,0}
                    },
                    {
                            {1,1,1},
                            {0,1,0}
                    }
            };

}
