package mst;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphVisualizer {

    public static void saveGraphImage(Graph graph, String filename) {
        int vertices = graph.getVertices();

        if (vertices > 10) {
            System.out.println("only small graphs");
            return;
        }

        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setStroke(new BasicStroke(2));
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        double angleStep = 2 * Math.PI / vertices;
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = 200;

        Point[] points = new Point[vertices];
        for (int i = 0; i < vertices; i++) {
            int x = (int) (centerX + radius * Math.cos(i * angleStep));
            int y = (int) (centerY + radius * Math.sin(i * angleStep));
            points[i] = new Point(x, y);
        }

        for (Edge e : graph.getEdges()) {
            Point p1 = points[e.getSrc()];
            Point p2 = points[e.getDest()];

            g2d.setColor(Color.GRAY);
            g2d.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));

            int midX = (p1.x + p2.x) / 2;
            int midY = (p1.y + p2.y) / 2;
            g2d.setColor(Color.BLUE);
            g2d.drawString(String.valueOf(e.getWeight()), midX, midY);
        }

        for (int i = 0; i < vertices; i++) {
            Point p = points[i];
            g2d.setColor(new Color(255, 120, 120));
            g2d.fillOval(p.x - 15, p.y - 15, 30, 30);

            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x - 15, p.y - 15, 30, 30);
            g2d.drawString(String.valueOf(i), p.x - 4, p.y + 5);
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Graph image saved: " + filename);
        } catch (IOException ex) {
            System.err.println("Error saving graph image: " + ex.getMessage());
        }
    }
}
