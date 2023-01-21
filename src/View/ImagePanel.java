package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageDisplay {
        
        private final List<Order> orders;
        private ImageDisplay.DragEvent onDragged = ImageDisplay.DragEvent::Null;
        private ImageDisplay.NotifyEvent onReleased = ImageDisplay.NotifyEvent::Null;
        private int x;

        ImagePanel() {
            this.orders = new ArrayList<>();
            this.addMouseListener(new MouseListener() {
                
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    x = e.getX();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    onReleased.handle(e.getX()-x);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            this.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    onDragged.handle(e.getX()-x);
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            clean(g);
            for (Order order : orders){
                order.update_size(this.getWidth(),this.getHeight());
                g.drawImage(order.image, order.x(this.getWidth()), order.y(this.getHeight()), order.width, order.height, null);                
            }
        }

        private void clean(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        @Override
        public void clear() {
            orders.clear();
        }

        @Override
        public void paint(Object data, int offset) {
            orders.add(new Order(data, offset));
            repaint();
        }

        @Override
        public void onDragged(ImageDisplay.DragEvent event) {
            this.onDragged = event;
        }

        @Override
        public void onReleased(ImageDisplay.NotifyEvent event) {
            this.onReleased = event;
        }

        @Override
        public int width() {
            return this.getWidth();
        }
        
        private static class Order {
            private final BufferedImage image;
            private final int offset;
            private int width;
            private int height;

            public Order(Object data, int offset)  {
                this.image = (BufferedImage) data;
                this.offset = offset;
                this.width = image.getWidth();
                this.height = image.getHeight();
            }
        
            public void update_size(int panelWidth, int panelHeight) {
                this.width = image.getWidth();
                this.height = image.getHeight();
                if (panelHeight < this.height || panelWidth < this.width) {
                    double imageRatio = (double) this.width / this.height;
                    if (panelWidth < this.width ) {
                        this.width = panelWidth;
                        this.height = (int) (width / imageRatio);
                    }
                    if (panelHeight < this.height ){
                        this.height = panelHeight;
                        this.width = (int) (height * imageRatio);
                    }
                }
            }
        
            public int x(int width) {
                return (width - this.width) / 2 + offset;
            }

            public int y(int height) {
                return (height - this.height) / 2;
            }
        }
        
    }
