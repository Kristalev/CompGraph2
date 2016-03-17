package MyGraphLib;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * окно и обрамляющая оболочка вокруг многоугольника
 */

public class Win{
    /**
     * <p>Координата X правого края окна</p>
     */
    public double x_r;
    /**
     * <p>Координата X левого края окна</p>
     */
    public double x_l;
    /**
     * <p>Координата Y верхнего края окна</p>
     */
    public double y_t;
    /**
     * <p>Координата Y нижнего края окна</p>
     */
    public double y_b;


    /**
     * <p>Конструктор для создания окна.</p>
     * @param xR-Координата X правого края окна
     * @param xL-Координата X левого края окна
     * @param yT-Координата Y верхнего края окна
     * @param yB-Координата Y нижнего края окна
     */
    public Win(double xR, double xL, double yT, double yB){
        x_r = xR;
        x_l = xL;
        y_t = yT;
        y_b = yB;
    }

    /**
     * <p>Конструктор для создания квадратного окна.</p>
     * @param X-Координата X левого края окна
     * @param Y-Координата Y верхнего края окна
     * @param size-размер квадратного окна
     */
    public Win(double X, double Y, double size){
        x_l = X;
        y_t = Y;
        x_r = X + size;
        y_b = Y + size;
    }

    /**
     * <p>Метод возвращающий размер квадратного окна</p>
     * @return размер окна
     */
    public double getSize(){
        return x_r - x_l;
    }

    /**
     *Конструктор для создания обрамляющей фигуру f оболочки
     *
     * @param f-многоугольник вокруг которого строится обрамляющее окно.
     */

    /*<p>Параметры окна такой оболочки равны: </p>
            * <ul>
    *     <li>x_l = координата самой левой вершины многоугольника</li>
            *     <li>x_к = координата самой правой вершины многоугольника</li>
            *     <li>y_t = координата самой верхней вершины многоугольника</li>
            *     <li>y_b = координата самой нижней вершины многоугольника</li>
            * </ul>*/
    public Win(JColorFigure f){
        ArrayList<JFigure.JLine> arrLines = f.getLines();
        double xMax = arrLines.get(0).getFist().getX();
        double xMin = arrLines.get(0).getFist().getX();
        double yMax = arrLines.get(0).getFist().getY();
        double yMin = arrLines.get(0).getFist().getY();

        for(JFigure.JLine line : arrLines){
            if (line.getFist().getX() > xMax)
                xMax = line.getFist().getX();
            if (line.getFist().getX() < xMin)
                xMin = line.getFist().getX();
            if (line.getFist().getY() > yMax)
                yMax = line.getFist().getY();
            if (line.getFist().getY() < yMin)
                yMin = line.getFist().getY();
        }

        x_r = xMax;
        x_l = xMin;
        y_t = yMin;
        y_b = yMax;
    }


    /**
     *<p>Метод выполняющий заливку окна заданным цветом</p>
     * @param holst-элемент, на котором происходит отрисовка
     * @param col-цвет заливки
     */
    public void fillWin(Canvas holst, Color col){
        GraphicsContext g2 = holst.getGraphicsContext2D();
        g2.setFill(col);
        g2.fillRect(this.x_l,this.y_t, this.getSize(), this.getSize());
    }


    /**
     * <p>Метод выполняющий заливку окна с размером больше 2
     * заданным цветом с черной рамкрй</p>
     * @param holst-элемент, на котором происходит отрисовка
     * @param col-цвет заливки
     */
    public void fillBorderWin(Canvas holst, Color col){
        GraphicsContext g2 = holst.getGraphicsContext2D();
        g2.setFill(col);
        g2.setStroke(Color.BLACK);
        g2.setLineWidth(1);
        if (this.getSize() > 2)
            g2.strokeRect(this.x_l,this.y_t,this.getSize(),this.getSize());
        g2.fillRect(this.x_l,this.y_t, this.getSize(), this.getSize());
    }
}
