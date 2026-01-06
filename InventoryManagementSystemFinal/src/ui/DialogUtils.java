package ui;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {
    public static void showError(Component parent,String message){
        UIManager.put("OptionPane.background", new Color(31,31,46));
        UIManager.put("Panel.background", new Color(31,31,46));
        UIManager.put("OptionPane.messageForeground", new Color(229,231,235));
        UIManager.put("Button.background", new Color(130,58,240));
        UIManager.put("Button.foreground",Color.WHITE);
        UIManager.put("Buuton.setFont",new Font("Segoe UI",Font.BOLD,14));
        JOptionPane.showMessageDialog(parent,message,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
//UImanager
//اي شي منحطو فيه بصير كانو عم نقلو انو يخلي اعداداتو هيك بشكل افتراضي بالسوينغ