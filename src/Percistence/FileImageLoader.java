package Percistence;

import Model.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;

public class FileImageLoader implements ImageLoader{
    private final File[] files;
    private final Map<File, BufferedImage> cache;

    public FileImageLoader(File file) {
        this.files = file.listFiles(FileImageLoader::filterImages);
        this.cache = new HashMap<>();
    }
    
    private static final Set<String> imageExtensions = imageExtensions();
    private static boolean filterImages(File folder, String filename) {
        return imageExtensions.contains(extensionOf(filename));
    }

    private static String extensionOf(String filename) {
        return filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
    }


    private static Set<String> imageExtensions() {
        Set<String> result = new HashSet<String>();
        result.add("png");
        result.add("jpg");
        result.add("jpeg");
        return result;
    }

    @Override
    public Image load() {
        return image(0);
    }

    private Image image(int i) {
        return new Image() {
            private BufferedImage image = read(files[i]);
            
            @Override
            public Object data() {
                return image;
            }

            @Override
            public int width() {
                return image.getWidth();
            }

            @Override
            public Image prev() {
                return i > 0 ? image(i-1) : image(files.length-1);
            }

            @Override
            public Image next() {
                return i < files.length-1 ? image(i+1) : image(0);
            }

            private BufferedImage read(File file) {
                if (cache.containsKey(file)) return cache.get(file);
                BufferedImage image = load(file);
                cache.put(file, image);
                return image;
            }

            private BufferedImage load(File file) {
                try {
                    return ImageIO.read(file);
                } catch (IOException ex) {
                    return null;
                }
            }
        };
    }
}
