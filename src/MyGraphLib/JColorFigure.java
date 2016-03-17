package MyGraphLib;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by Admin on 17.02.16.
 */
public class JColorFigure extends JFigure {
    private Color col;

    public void setColor(Color c){
        this.col = c;
    }

    public Color getColor(){
        return this.col;
    }

    public void drawFllFigure(Canvas holst){
        GraphicsContext g2 = holst.getGraphicsContext2D();
        g2.setFill(col);
        double[] aX = new double[this.lines.size()];
        double[] aY = new double[this.lines.size()];
        for(int i = 0 ; i < this.lines.size(); i++){
            aX[i] = lines.get(i).getFist().getX();
            aY[i] = lines.get(i).getFist().getY();
        }
        g2.fillPolygon(aX,aY,this.lines.size());
    }


    public JColorFigure proecColorFigureAksom(double alph, double bet) {
        ArrayList<JLine> arr = super.proecFigureAksom(alph, bet);
        JColorFigure newFig = new JColorFigure();
        newFig.setColor(this.getColor());
        for(JLine line : arr){
            newFig.addLine(line.getFist(),line.getSecond());
        }
        return newFig;
    }
}
