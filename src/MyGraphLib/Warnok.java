package MyGraphLib;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;

/**
 * Класс, содержащий основные тесты при реализации алгоритма Варнока и его реализацию
 */
public class Warnok {


    /**
     * <p>Простой тест с обрамляющей оболочкой.</p>
     * @param win-окно, относительно которого тестируется фигура
     * @param fig-тестируемая фигура
     * @return true-если фигура fig внутреняя по отножению к окну win, иначе false
     */
    public static boolean innerTest(Win win, JColorFigure fig){
        Win winFig = new Win(fig);
        if (win.x_l  <= winFig.x_l && win.x_r >= winFig.x_r && win.y_b >= winFig.y_b && win.y_t <= winFig.y_t)
            return true;
        else
            return false;
    }

    /**
     * <p>Простой тест с обрамляющей оболочкой.</p>
     * @param win-окно, относительно которого тестируется фигура
     * @param fig-тестируемая фигура
     * @return true-если фигура fig внешняя по отножению к окну win, иначе false
     */
    public static boolean externalEasyTest(Win win, JColorFigure fig){
        Win winFig = new Win(fig);
        if (win.x_l  > winFig.x_r || win.x_r < winFig.x_l || win.y_b < winFig.y_t || win.y_t > winFig.y_b)
            return true;
        else
            return false;
    }

    /**
     * <p>Тест на пересечение фигуры и онка при помощи пробной функции</p>
     * @param win-окно, относительно которого тестируется фигура
     * @param fig-тестируемая фигура
     * @return true-если фигура fig пересикающаяся по отножению к окну win, иначе false
     */
    public static boolean crossingTest(Win win, JColorFigure fig) {
        boolean flug = false;
        for (JFigure.JLine line : fig.getLines()) {
            double m = line.getSecond().getY() - line.getFist().getY();
            m /= line.getSecond().getX() - line.getFist().getX();
            double b = line.getFist().getY() - m * line.getFist().getX();
            double[] ass = new double[4];
            if (line.getSecond().getX() - line.getFist().getX() != 0){
                ass[0] = Math.signum(win.y_b - m * win.x_l - b);
                ass[1] = Math.signum(win.y_t - m * win.x_l - b);
                ass[2] = Math.signum(win.y_b - m * win.x_r - b);
                ass[3] = Math.signum(win.y_t - m * win.x_r - b);
            }else{
                ass[0] = Math.signum(win.x_l - line.getFist().getX());
                ass[1] = Math.signum(win.x_l - line.getFist().getX());
                ass[2] = Math.signum(win.x_r - line.getFist().getX());
                ass[3] = Math.signum(win.x_r - line.getFist().getX());
            }

            for (int i = 1; i < ass.length; i++) {
                if (Math.abs(ass[i] - ass[i - 1]) > 0.001) flug = true;
                if (flug) break;
            }
            if (flug) return flug;
        }
        return flug;
    }


    /**
     * <p>Метод, находящий многоугольник, охватывающий данное окно размером не больше 1,
     * который лежит ближе всех к наблюдателю</p>
     * @param win-окно, относительно которого тестируются фигуры
     * @param arrFig-коллекция проверяемых многоугольников
     * @return Результат работы метода - фигура, охватывающий данное окно размером не больше 1,
     * который лежит ближе всех к наблюдателю
     */
    public static JColorFigure coating(Win win, ArrayList<JColorFigure> arrFig){
        double zMax = -1000000;
        JColorFigure rezultFig = null;
        double xCentr = win.x_l + win.getSize()/2;
        double yCentr = win.y_t + win.getSize()/2;
        for (JColorFigure fig : arrFig){
            double visible = 0;
            for(JFigure.JLine line : fig.getLines()){
                visible += Warnok.visiblePointTest(new Point2D(xCentr,yCentr),line);
            }
            if (visible % 360 >-1 && visible % 360 <1 ){
                double z = Warnok.depth(fig,win);
                if (z - zMax > 0.01){
                    zMax = z;
                    rezultFig = fig;
                }
            }
        }
        return rezultFig;
    }


    /**
     * <p>Тест с подсчетом угла</p>
     * @param win-окно, относительно которого тестируются фигуры
     * @param fig-тестируемая фигура
     * @return true-если фигура fig внутреняя по отножению к окну win, иначе false
     */
    public static boolean famale(Win win, JColorFigure fig){
        double x = win.x_l + win.getSize()/2;
        double y= win.y_t + win.getSize()/2;
        double visible = 0;
        for(JFigure.JLine line : fig.getLines()){
            visible += Warnok.visiblePointTest(new Point2D(x,y),line);
        }
        if (Math.abs(visible - 360) <0.01){
            return true;
        }
        return false;
    }


    /**
     * <p>Метод возращающий угол между двумя векторами. Вершина угла в точке point</p>
     * @param point-вершина угла
     * @param line-линия концы которой являются концами сторон угла.
     * @return Возвращаяется угол с вершиной в точке point и концами сторон угла на концах отрезка line
     */
    public static double visiblePointTest(Point2D point, JFigure.JLine line) {
        double angle = point.angle(new Point2D(line.getFist().getX(), line.getFist().getY()), new Point2D(line.getSecond().getX(), line.getSecond().getY()));
        if (angle == Double.NaN)
            angle = point.angle(new Point2D(line.getFist().getX()-1, line.getFist().getY()-1), new Point2D(line.getSecond().getX()+1, line.getSecond().getY()+1));
        return  angle;
    }


    /**
     * <p>Метод определения глубины фигуры</p>
     * @param fig-фигура, глубина которой определяется
     * @param win-окно, в центре которого определяется глубина фигуры
     * @return Возвращется координата Z точки, координаты X b Y которой равны координатам цента окна win,
     *         и которая принадлежит плоскости фигуры fig
     */
    public static double depth(JColorFigure fig, Win win){
        double xCentr = win.x_l + win.getSize()/2;
        double yCentr = win.y_t + win.getSize()/2;
        double[] coef = Warnok.coefficient(fig);
        double z = -10000000;
        if(coef[2] == 0){
            for(JFigure.JLine line : fig.getLines()){
                z = Math.max(line.getFist().getZ(),z);
            }
        }else{
            z = -(coef[0]*xCentr + coef[1]*yCentr +coef[3]) / coef[2];
        }
        return z;
    }

    /**
     * <p>Метод нахождения коэффицентов плоскости, в которой лежит фигура</p>
     * @param fig-фигура лежащая в плоскости
     * @return Возвращается массив из 4-ёх элеменов, каждый элемент которого
     * равен коэфицентам уравнения плоскости Ax+By+Cz+D=0
     */
    public static double[] coefficient(JColorFigure fig){
        Point3D f = fig.getLines().get(0).getFist();
        Point3D s = fig.getLines().get(1).getFist();
        Point3D t = fig.getLines().get(2).getFist();
        double[] coef = new double[4];
        coef[0] = f.getY()*(s.getZ()-t.getZ())+s.getY()*(t.getZ()-f.getZ())+t.getY()*(f.getZ()-s.getZ());
        coef[1] = f.getZ()*(s.getX()-t.getX())+s.getZ()*(t.getX()-f.getX())+t.getZ()*(f.getX()-s.getX());
        coef[2] = f.getX()*(s.getY()-t.getY())+s.getX()*(t.getY()-f.getY())+t.getX()*(f.getY()-s.getY());
        coef[3] = f.getX()*(s.getY()*t.getZ() - t.getY()*s.getZ()) + s.getX()*(t.getY()*f.getZ() - f.getY()*t.getZ()) + t.getX()*(f.getY()*s.getZ()-s.getY()*f.getZ());
        coef[3] = -coef[3];
        return coef;
    }

    /**
     * <p>Алгоритм Варнока. Рекурсивная функция.</p>
     * @param win-текущее окно
     * @param arrFig-массив фигур для отрисовки
     * @param holst-элемент, на котором происходит отрисовка
     */
    public static void algorithm(Win win, ArrayList<JColorFigure> arrFig, Canvas holst){
        ArrayList<JColorFigure> innerFig = new ArrayList<>();
        ArrayList<JColorFigure> crossFig = new ArrayList<>();
        ArrayList<JColorFigure> externalFig = new ArrayList<>();
        ArrayList<JColorFigure> femaleFig = new ArrayList<>();
        for(JColorFigure fig : arrFig){
            if (Warnok.externalEasyTest(win,fig))
                externalFig.add(fig);
            else
            if(Warnok.innerTest(win, fig)) innerFig.add(fig);
            else
            if(Warnok.crossingTest(win,fig)) crossFig.add(fig);
            else
            if (Warnok.famale(win,fig)){
                femaleFig.add(fig);
            }
            else
                externalFig.add(fig);

        }
        if (innerFig.size() == 1 && crossFig.size() == 0 && femaleFig.size() == 0){
            win.fillWin(holst, Color.WHITE);
            innerFig.get(0).drawFllFigure(holst);
        }else{
            if(innerFig.size() == 0 && crossFig.size() == 0 && femaleFig.size() == 1){
                win.fillWin(holst, femaleFig.get(0).getColor() );
            }else{
                if(externalFig.size() == arrFig.size()) {
                    win.fillWin(holst, Color.WHITE);
                }
                else{
                    if (femaleFig.size()!= 0){
                        int c = 0;
                        int index = -1;
                        for(JColorFigure fig : femaleFig){
                            if(Warnok.nearestPolygonTest(win,arrFig.indexOf(fig), arrFig)){
                                c++;
                                index = arrFig.indexOf(fig);
                            }
                        }
                        if (c == 1){
                            win.fillWin(holst, arrFig.get(index).getColor() );
                        }else {
                            if (win.getSize() <= 1){
                                JColorFigure coatingFig = coating(win,arrFig);
                                if (coatingFig != null){
                                    win.fillWin(holst,coatingFig.getColor());
                                }else{
                                    win.fillWin(holst,Color.WHITE);
                                }
                            }else{
                                double s = win.getSize()/2;
                                algorithm(new Win(win.x_l,win.y_t, s),arrFig,holst);
                                algorithm(new Win(win.x_l+s,win.y_t+s, s),arrFig,holst);
                                algorithm(new Win(win.x_l,win.y_t+s, s),arrFig,holst);
                                algorithm(new Win(win.x_l+s,win.y_t, s),arrFig,holst);
                            }
                        }

                    }else{
                        if (win.getSize() <= 1){
                            JColorFigure coatingFig = coating(win,arrFig);
                            if (coatingFig != null){
                                win.fillWin(holst,coatingFig.getColor());
                            }else{
                                win.fillWin(holst,Color.WHITE);
                            }
                        }else{
                            double s = win.getSize()/2;
                            algorithm(new Win(win.x_l,win.y_t, s),arrFig,holst);
                            algorithm(new Win(win.x_l+s,win.y_t+s, s),arrFig,holst);
                            algorithm(new Win(win.x_l,win.y_t+s, s),arrFig,holst);
                            algorithm(new Win(win.x_l+s,win.y_t, s),arrFig,holst);
                        }
                    }

                }
            }
        }
    }


    /**
     * <p>Проверка на экранируемость охватывающей окно фигурой другие фигуры</p>
     * @param win-преверяемое окно
     * @param femaleFig-индекс охватывающей фигуры
     * @param arrFig-колекция всех фигур
     * @return Возраещает true - если охватывающая фигура эранирует остальные фигуры в этом окне.
     */
    public static boolean nearestPolygonTest(Win win, int femaleFig, ArrayList<JColorFigure> arrFig){
        double[][] zInDigr = new double[arrFig.size()][4];
        int index = 0;
        for(JColorFigure fig: arrFig){
            double[] coef = Warnok.coefficient(fig);
            if(coef[2] != 0){
                zInDigr[index][0] = -(coef[0]*win.x_l + coef[1]*win.y_t +coef[3]) / coef[2];
                zInDigr[index][1] = -(coef[0]*win.x_l + coef[1]*win.y_b +coef[3]) / coef[2];
                zInDigr[index][2] = -(coef[0]*win.x_r + coef[1]*win.y_t +coef[3]) / coef[2];
                zInDigr[index][3] = -(coef[0]*win.x_r + coef[1]*win.y_b +coef[3]) / coef[2];
            }
            index++;
        }
        for(int i = 0 ; i < zInDigr.length; i++){
            if(i == femaleFig)
                continue;
            else{
                if (zInDigr[i][0] > zInDigr[femaleFig][0] ||
                        zInDigr[i][1] > zInDigr[femaleFig][1] ||
                        zInDigr[i][2] > zInDigr[femaleFig][2] ||
                        zInDigr[i][3] > zInDigr[femaleFig][3])
                    return false;
            }
        }
        return true;
    }

    /**
     * <p>Максимальная координата Z фигуры</p>
     * @param c1-фигура
     * @return Возвращет максимальную координату Z фигуры c1
     */
    public static double maxZ(JColorFigure c1){
        double maxZ1 = -100000000;
        for (JFigure.JLine line: c1.getLines())
            if (line.getFist().getZ() - maxZ1 > 0.01)
                maxZ1 = line.getFist().getZ();
        return maxZ1;
    }


    /**
     * <p>Алгоритм Варнока. Реализация со стеком.</p>
     * @param win-текущее окно
     * @param wins-стек окон
     * @param arrFig-массив фигур для отрисовки
     * @param holst-элемент, на котором происходит отрисовка
     */
    public static void algorithm(Win win,Stack<Win> wins, ArrayList<JColorFigure> arrFig, Canvas holst) {
        ArrayList<JColorFigure> innerFig = new ArrayList<>();
        ArrayList<JColorFigure> crossFig = new ArrayList<>();
        ArrayList<JColorFigure> externalFig = new ArrayList<>();
        ArrayList<JColorFigure> femaleFig = new ArrayList<>();
        for (JColorFigure fig : arrFig) {
            if (Warnok.externalEasyTest(win, fig))
                externalFig.add(fig);
            else if (Warnok.innerTest(win, fig)) innerFig.add(fig);
            else if (Warnok.crossingTest(win, fig)) crossFig.add(fig);
            else if (Warnok.famale(win, fig)) femaleFig.add(fig);
            else
                externalFig.add(fig);

        }
        if (innerFig.size() == 1 && crossFig.size() == 0 && femaleFig.size() == 0) {
            win.fillBorderWin(holst, Color.WHITE);
            innerFig.get(0).drawFllFigure(holst);
        } else {
            if (innerFig.size() == 0 && crossFig.size() == 0 && femaleFig.size() == 1) {
                win.fillBorderWin(holst, femaleFig.get(0).getColor());
            } else {
                if (externalFig.size() == arrFig.size()) {
                    win.fillBorderWin(holst, Color.WHITE);
                } else {
                    if (femaleFig.size() != 0) {
                        int c = 0;
                        int index = -1;
                        for (JColorFigure fig : femaleFig) {
                            if (Warnok.nearestPolygonTest(win, arrFig.indexOf(fig), arrFig)) {
                                c++;
                                index = arrFig.indexOf(fig);
                            }
                        }
                        if (c == 1) {
                            win.fillBorderWin(holst, arrFig.get(index).getColor());
                        } else {
                            if (win.getSize() <= 1) {
                                JColorFigure coatingFig = coating(win, arrFig);
                                if (coatingFig != null) {
                                    win.fillBorderWin(holst, coatingFig.getColor());
                                } else {
                                    win.fillBorderWin(holst, Color.WHITE);
                                }
                            } else {
                                double s = win.getSize() / 2;
                                wins.push(new Win(win.x_l, win.y_t, s));
                                wins.push(new Win(win.x_l + s, win.y_t + s, s));
                                wins.push(new Win(win.x_l, win.y_t + s, s));
                                wins.push(new Win(win.x_l + s, win.y_t, s));
                            }
                        }

                    } else {
                        if (win.getSize() <= 1) {
                            JColorFigure coatingFig = coating(win, arrFig);
                            if (coatingFig != null) {
                                win.fillBorderWin(holst, coatingFig.getColor());
                            } else {
                                win.fillBorderWin(holst, Color.WHITE);
                            }
                        } else {
                            double s = win.getSize() / 2;
                            wins.push(new Win(win.x_l, win.y_t, s));
                            wins.push(new Win(win.x_l + s, win.y_t + s, s));
                            wins.push(new Win(win.x_l, win.y_t + s, s));
                            wins.push(new Win(win.x_l + s, win.y_t, s));
                        }
                    }

                }
            }
        }
    }
}
