package View;

import Controler.Command;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class MainFrame extends JFrame{
    private ImagePanel imagepanel;
    private final Map<String,Command> commands;
    
    public MainFrame() {
        commands = new HashMap<>();
        this.setTitle("Image Viewer");
        this.setSize(800,600);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        imagepanel = new ImagePanel();
        this.add(imagepanel, BorderLayout.CENTER);
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());
        toolbar.add(button("prev","<"));
        toolbar.add(button("next",">"));
        this.add(toolbar, BorderLayout.SOUTH);
    }

    public ImageDisplay imageDisplay() {
        return imagepanel;
    }
    
    private JButton button(String name, String label){
        JButton button = new JButton(label);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(name).execute();
            }
        });
        return button;
    }
    
    public void addCommand(String key, Command command){
        commands.put(key, command);
    }

}
