package RecursoCompartido;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bodega {
	
	private int productos;
	private int CAPACIDAD_MAX = 10;
	
    private float progress;
    private ArcProgressPane p1;
	
	public Bodega(){
		p1 = new ArcProgressPane();
        p1.setForeground(Color.RED);
        p1.setFillProgress(true);
        
        JFrame frame = new JFrame("Llenado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(p1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

	public int getProductos(){
		return productos;
	}
	
	public void agregarProducto() {
		if(productos<CAPACIDAD_MAX){
			productos++;
			progress += 0.1f;
            p1.setProgress(progress);
		}
	}
	
	public void sacarProducto() {
		if(productos>0){
			productos--;
			progress -= 0.1f;
            p1.setProgress(progress);
		}
	}
	
	@SuppressWarnings("serial")
	public class ArcProgressPane extends JPanel {

        private boolean fillProgress = false;
        private float progress;

        public ArcProgressPane() {
        }

        public void setFillProgress(boolean value) {
            if (fillProgress != value) {
                this.fillProgress = value;
                firePropertyChange("fillProgress", !fillProgress, fillProgress);
            }
        }

        public boolean isFillProgress() {
            return fillProgress;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }

        public void setProgress(float value) {
            if (progress != value) {
                float old = progress;
                this.progress = value;
                firePropertyChange("progress", old, progress);
                repaint();
            }
        }

        public float getProgress() {
            return progress;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            Insets insets = getInsets();
            int width = getWidth() - (insets.left + insets.right);
            int height = getHeight() - (insets.bottom + insets.top);
            int raidus = Math.min(width, height);
            int x = insets.left + ((width - raidus) / 2);
            int y = insets.right + ((height - raidus) / 2);

            double extent = 360d * progress;

            g2d.setColor(getForeground());
            Arc2D arc = null;
            if (isFillProgress()) {
                arc = new Arc2D.Double(x, y, raidus, raidus, 90, -extent, Arc2D.PIE);
            } else {
                extent = 360 - extent;
                arc = new Arc2D.Double(x, y, raidus, raidus, 90, extent, Arc2D.PIE);
            }
            g2d.fill(arc);
            g2d.dispose();
        }
    }
}
