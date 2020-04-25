package Presentation;

import Data.Datasets.Dataset;
import Logic.NeuronStructures.MultiLayerPerceptron;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.TextureIO;
import org.jzy3d.bridge.awt.FrameAWT;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapGrayscale;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.maths.Rectangle;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.ScatterMultiColor;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlotPrepper {

    private MultiLayerPerceptron multiLayerPerceptron;
    private Coord3d[] points = new Coord3d[4];

    public void setMultiLayerPerceptron(MultiLayerPerceptron multiLayerPerceptron){
        this.multiLayerPerceptron = multiLayerPerceptron;
    }

    public void setPoints(Dataset dataset){
        int i = 0;
        for(ArrayList<Float> point : dataset.getTests()){
            points[i] = new Coord3d(point.get(0),point.get(1),point.get(2));
            i++;
        }
    }

    public void createChartWithController(){

        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                 ArrayList<Float> input = new ArrayList<Float>();
                input.add((float) x);
                input.add((float) y);
                return multiLayerPerceptron.calculateOutput(input).get(0);
            }
        };

// Define range and precision for the function to plot
        Range range = new Range(-2, 2);
        int steps = 50;
        ColorMapper cm1 = new ColorMapper( new ColorMapGrayscale(), 0.0f, 1000.0f );
        ScatterMultiColor scatter = new ScatterMultiColor(points,new Color[] {new Color(0.0f,0.0f,0.0f),new Color(0.0f,0.0f,0.0f),new Color(0.0f,0.0f,0.0f),new Color(0.0f,0.0f,0.0f)},cm1,5.0f);


// Create a surface drawing that function
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
        ColorMapper cm;
        cm = new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f));
        surface.setColorMapper(cm);
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);

// Create a chart and add the surface
        Chart chart = new AWTChart(Quality.Advanced);
        chart.add(surface);
        chart.getScene().add(scatter);
        chart.addMouseCameraController();
        System.out.println(chart.getViewPoint().toString());

        chart.open("Jzy3d Demo", 600, 600);

        System.out.println(chart.getViewPoint().toString());
    }

    public void createGIFFrame(int i) throws IOException {
        File image = new File("C:\\GIF\\Image"+i+".png");
        image.createNewFile();
        Mapper mapper = new Mapper() {
            public double f(double x, double y) {
                ArrayList<Float> input = new ArrayList<Float>();
                input.add((float) x);
                input.add((float) y);
                return multiLayerPerceptron.calculateOutput(input).get(0);
            }
        };

// Define range and precision for the function to plot
        Range range = new Range(-2, 2);
        int steps = 50;
        ColorMapper cm1 = new ColorMapper( new ColorMapGrayscale(), 0.0f, 1000.0f );
        ScatterMultiColor scatter = new ScatterMultiColor(points,new Color[] {new Color(0.0f,0.0f,0.0f),new Color(0.0f,0.0f,0.0f),new Color(0.0f,0.0f,0.0f),new Color(0.0f,0.0f,0.0f)},cm1,5.0f);


// Create a surface drawing that function
        Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
        ColorMapper cm;
        cm = new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f));
        surface.setColorMapper(cm);
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);
        surface.setWireframeColor(Color.BLACK);

// Create a chart and add the surface
        Chart chart = new AWTChart(Quality.Advanced);
        chart.add(surface);
        chart.getScene().add(scatter);
        saveScreenshot(image.getAbsolutePath(),chart,ViewPositionMode.FREE, new Coord3d(-1,-1,4.0));
    }


    public void saveScreenshot(String outputFile, Chart chart, ViewPositionMode viewPosMode,
                               Coord3d viewPoint) {
        Rectangle window = new Rectangle(200, 200, 700, 600);

        FrameAWT frame = (FrameAWT) chart.getFactory().newFrame(chart, window, "Chart Frame");

        if (viewPosMode != null) {
            chart.setViewMode(viewPosMode);
        }

        if (viewPoint != null) {
            chart.setViewPoint(viewPoint);
        }
        try {
            File output = new File(outputFile);
            if (!output.getParentFile().exists()) {
                output.getParentFile().mkdirs();
            }
            TextureIO.write(chart.getCanvas().screenshot(), output);
            frame.remove((java.awt.Component) chart.getCanvas());
            chart.dispose();
            frame.dispose();
        } catch (GLException e) {
            System.out.println("Problem with screenshot: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Problem with screenshot: " + e.getMessage());
            System.exit(1);
        }
    }
}
