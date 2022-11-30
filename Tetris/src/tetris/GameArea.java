package tetris;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    private int gridColumns;
    private int gridRows;
    private int gridCellSize;
    private TetrisBlock block;
    private Color[][] background;
    public GameArea(Rectangle r, int columns){
        this.setBounds(r.getBounds());
        this.setBackground(Color.lightGray);
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        gridColumns = columns;
        gridCellSize = this.getWidth()/columns;
        gridRows=this.getHeight()/gridCellSize;
    }

    public void initBackgroudArray(){
        background = new Color[gridRows][gridColumns];
    }
    public void spawnBlock(){
        block = new TetrisBlock();
        block.spawnBlock(gridColumns);
    }

    public boolean makeBlockFall(){
        if(checkBottom()){
            return false;
        }
        block.fall();
        repaint();
        return true;
    }

    public boolean isBlockOutOfBounds(){
        if(block.getY()<0){
            block = null;
            return true;
        }

        return false;
    }

    public void moveBlockRight(){
        if(block==null) return;
        if(checkRight()) return;

        block.moveRight();
        repaint();
    }

    public void moveBlockLeft(){
        if(block==null) return;
        if(checkLeft()) return;

        block.moveLeft();
        repaint();
    }

    public void dropBlock(){
        if(block==null) return;
        while(!checkBottom()){
            makeBlockFall();
        }
        repaint();
    }

    public void rotateBlock(){
        if(block==null) return;
        if(block.getY()<0)return;
        int[][] savedShape = block.getShape();
        block.rotate();

        if(block.getX()<0) block.setX(0);
        if(block.getRightEdge()>=gridColumns) block.setX(gridColumns-block.getWidth());
        if(block.getBottomEdge()>=gridRows)  block.setY(gridRows-block.getHeight());
        if(colidesWithBackground())block.setShape(savedShape);

        repaint();
    }

    public int clearLines(){
        int clearedLines = 0;
        boolean lineFilled;
        for (int i = gridRows-1; i >=0; i--) {
            lineFilled = true;
            for (int j = 0; j < gridColumns; j++) {
                if (background[i][j] == null) {
                    lineFilled = false;
                    break;
                }
            }
            if(lineFilled) {
                clearedLines++;
                clearLine(i);
                shiftDown(i);
                clearLine(0);

                i++;
                repaint();
            }
            if(clearedLines>0){
                Main.playClearLines();
            }
        }
        return clearedLines;
    }

    public void moveBlockToBackground(){
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        int x = block.getX();
        int y = block.getY();

        Color color = block.getColor();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if(shape[i][j]==1){
                    background[i+y][j+x]=color;
                }
            }

        }
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        paintBackground(g);
        paintBlock(g);
    }

    private void paintBlock(Graphics g){
        for (int i = 0; i < block.getHeight(); i++) {
            for (int j = 0; j < block.getWidth() ; j++) {
                if(block.getShape()[i][j]==1) {
                    int x = (block.getX()+j)*gridCellSize;
                    int y = (block.getY()+i)*gridCellSize;
                    drawGridSquare(g,x,y,block.getColor());
                }
            }
        }
    }

    private void paintBackground(Graphics g){
        Color color;
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j <gridColumns; j++) {
                color = background[i][j];
                if(color!=null){
                    int x = j*gridCellSize;
                    int y = i * gridCellSize;
                    drawGridSquare(g,x,y,color);
                }
            }
        }
    }

    private void drawGridSquare(Graphics g,int x, int y, Color color){
        g.setColor(color);
        g.fillRect(x, y, gridCellSize, gridCellSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, gridCellSize, gridCellSize);
    }


    private boolean checkBottom(){
        if(block.getBottomEdge()==gridRows){
            return true;
        }
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int i = 0; i < w; i++) {
            for (int j = h-1; j >=0; j--) {
                if(shape[j][i]!=0) {
                    int x = i + block.getX();
                    int y = j + block.getY()+1;
                    if (y < 0) break;
                    if (background[y][x] != null) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    private boolean checkRight(){
        if(block.getRightEdge()==gridColumns) return true;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = w-1; j >= 0; j--) {
                if(shape[i][j]!=0) {
                    int x = j + block.getX() + 1;
                    int y = i + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != null) return true;
                    break;
                }
            }
        }
        return false;
    }

    private boolean checkLeft(){
        if(block.getX()==0) return true;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if(shape[i][j]!=0) {
                    int x = j + block.getX() - 1;
                    int y = i + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != null)return true;
                    break;
                }
            }
        }

        return false;
    }

    private boolean colidesWithBackground(){
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (shape[i][j]!=0) {
                    int x = block.getX() + j;
                    int y = block.getY() + i;
                    if (background[y][x] != null) return true;
                }
            }
        }
        return false;
    }
    private void clearLine(int lineNumber){
        for (int k = 0; k < gridColumns; k++) {
            background[lineNumber][k] = null;
        }
    }

    private void shiftDown(int startingLine){
        for (int i = startingLine; i > 0 ; i--) {
            for (int j = 0; j < gridColumns; j++) {
                background[i][j]=background[i - 1][j];
            }
        }
    }
}
