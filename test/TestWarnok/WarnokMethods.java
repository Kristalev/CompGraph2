package TestWarnok;

import MyGraphLib.JColorFigure;
import MyGraphLib.Warnok;
import MyGraphLib.Win;
import javafx.geometry.Point3D;
import org.junit.*;

import java.util.ArrayList;

/**
 * Created by Данил on 17.03.2016.
 */

public class WarnokMethods {
    /*
        Тест на проверку работоспособности теста на пересечение фигуры окна
     */
    @Test
    public void crossingTest(){
        Win w = new Win(0,0,5);
        JColorFigure fig = new JColorFigure();
        fig.addLine(new Point3D(0,0,0), new Point3D(10,0,0));
        fig.addLine(new Point3D(10,0,0), new Point3D(10,10,0));
        fig.addLine(new Point3D(10,10,0), new Point3D(0,10,0));
        fig.addLine(new Point3D(0,10,0), new Point3D(0,0,0));
        Assert.assertTrue("Фигура не пересекает окно", Warnok.crossingTest(w,fig));

    }

    /*
       Тест на проверку работоспособности тестa с обрамляющей оболочкой и
       на проверку конструктора на создание обрамляющей оболочки
    */
    @Test
    public void innerTest(){
        //тестируемая фигура
        JColorFigure fig = new JColorFigure();
        fig.addLine(new Point3D(0,0,0), new Point3D(10,0,0));
        fig.addLine(new Point3D(10,0,0), new Point3D(10,10,0));
        fig.addLine(new Point3D(10,10,0), new Point3D(0,10,0));
        fig.addLine(new Point3D(0,10,0), new Point3D(0,0,0));
        //создаем обрамляющее окно
        Win w = new Win(fig);
        Assert.assertTrue("Фигура не внутри окна", Warnok.innerTest(w,fig));

    }

    /*
       Тест на проверку работоспособности тестa с обрамляющей оболочкой и
       на проверку конструктора на создание обрамляющей оболочки
    */
    @Test
    public void externalEasyTest(){
        //тестируемая фигура
        JColorFigure fig = new JColorFigure();
        fig.addLine(new Point3D(0,0,0), new Point3D(10,0,0));
        fig.addLine(new Point3D(10,0,0), new Point3D(10,10,0));
        fig.addLine(new Point3D(10,10,0), new Point3D(0,10,0));
        fig.addLine(new Point3D(0,10,0), new Point3D(0,0,0));
        //создаем обрамляющее окно
        Win w = new Win(10,10,5);
        Assert.assertTrue("Фигура внешняя для окна", !Warnok.externalEasyTest(w,fig));

    }

    /*
      Тест на проверку работоспособности тестa на охватывающий многоугольник
   */
    @Test
    public void famleTest(){
        //тестируемая фигура
        JColorFigure fig = new JColorFigure();
        fig.addLine(new Point3D(0,0,0), new Point3D(10,0,0));
        fig.addLine(new Point3D(10,0,0), new Point3D(10,10,0));
        fig.addLine(new Point3D(10,10,0), new Point3D(0,10,0));
        fig.addLine(new Point3D(0,10,0), new Point3D(0,0,0));
        //создаем обрамляющее окно
        Win w = new Win(3,3,3);
        Assert.assertTrue("Фигура не охватывает окно", Warnok.famale(w,fig));
    }

    /*
      Тест на проверку работоспособности тестa на охватывающий многоугольник
   */
    @Test
    public void coatingTest(){
        //тестируемые фигуры
        JColorFigure fig1 = new JColorFigure();
        fig1.addLine(new Point3D(0,0,0), new Point3D(10,0,0));
        fig1.addLine(new Point3D(10,0,0), new Point3D(10,10,0));
        fig1.addLine(new Point3D(10,10,0), new Point3D(0,10,0));
        fig1.addLine(new Point3D(0,10,0), new Point3D(0,0,0));

        JColorFigure fig2 = new JColorFigure();
        fig2.addLine(new Point3D(0,0,5), new Point3D(10,0,5));
        fig2.addLine(new Point3D(10,0,5), new Point3D(10,10,5));
        fig2.addLine(new Point3D(10,10,5), new Point3D(0,10,5));
        fig2.addLine(new Point3D(0,10,5), new Point3D(0,0,5));

        ArrayList<JColorFigure> arr = new ArrayList<>();
        arr.add(fig1);
        arr.add(fig2);
        //создаем обрамляющее окно
        Win w = new Win(3,3,3);
        Assert.assertSame("Фигура не экранируюет другую фигуру", Warnok.coating(w,arr),fig2);
    }


}
