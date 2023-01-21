package imageviewer_final;

import Presenter.ImagePresenter;
import View.ImageDisplay;
import Controler.PrevImageCommand;
import Controler.NextImageCommand;
import View.MainFrame;
import Percistence.FileImageLoader;
import Percistence.ImageLoader;
import java.io.File;

public class ImageViewer_Final {

    public static void main(String[] args) {
        ImageLoader loader = new FileImageLoader(new File("images"));
        MainFrame mainFrame = new MainFrame();  
        ImageDisplay imageDisplay = mainFrame.imageDisplay();
        ImagePresenter imagePresenter = ImagePresenter.with(loader.load(), imageDisplay);
        mainFrame.addCommand("next", new NextImageCommand(imagePresenter));
        mainFrame.addCommand("prev", new PrevImageCommand(imagePresenter));
        mainFrame.setVisible(true);
    }
    
}
