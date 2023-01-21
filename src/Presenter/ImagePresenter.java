package Presenter;

import View.ImageDisplay;
import Model.Image;

public class ImagePresenter {
    private Image image;
    private final ImageDisplay display;

    public static ImagePresenter with(Image image, ImageDisplay imageDisplay) {
        return new ImagePresenter(image, imageDisplay);
    }
    
    public void show(Image image) {
        this.image = image;
        this.refresh();
    }
    
    private ImagePresenter(Image image, ImageDisplay display) {
        this.image = image;
        this.display = display;
        this.display.onDragged(this::onDragged);
        this.display.onReleased(this::onReleased);
        this.refresh();
    }
    
    private void onDragged(int offset) {
        display.clear();
        int size = offset;
        display.paint(image.data(), size);
        if (offset < 0) {
            Image next = image.next();
            size = image.width() > display.width() ? display.width() + offset: (display.width()-(display.width()-image.width())/2) + offset;
            display.paint(next.data(), size);            
        }
        else {
            Image prev = image.prev();
            size = image.width() > display.width() ? -display.width() + offset : -(display.width()+image.width())/2 + offset;
            display.paint(prev.data(), size);                        
        }
    }
    
       
    private void onReleased(int offset) {
        if (Math.abs(offset) > display.width() / 2) {
            this.image = offset < 0 ? image.next() : image.prev();
        }
        refresh();
        
    }
    
    private void refresh() {
        display.clear();
        display.paint(image.data(), 0);
    }
    
    public Image image(){
        return image;
    }
}
