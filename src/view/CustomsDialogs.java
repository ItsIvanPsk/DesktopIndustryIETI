package src.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomsDialogs {

    //ADDRESS ALREADY ON USE
    public static void alreadyOnUseDialog(JFrame frame){
        JOptionPane.showMessageDialog(frame,
            "The server address is already on use, please restart the server!",
            "Server address already on use",
            JOptionPane.ERROR_MESSAGE);
    }

    //ID VALIDATE DIALOG
    public static void idDialog(JFrame frame){
        JOptionPane.showMessageDialog(frame,
            "One or more Id's are duplicated",
            "Id validate error",
            JOptionPane.ERROR_MESSAGE);
    }

    //CONTROL VALIDATE DIALOG
    public static void controlDialog(JFrame frame){
        JOptionPane.showMessageDialog(frame,
            "Attribute name of Control repeated",
            "Control Error",
            JOptionPane.ERROR_MESSAGE);
    }

    //NUM ATTRIBUTE DIALOG
    public static void numAttributeDialog(JFrame frame){
        JOptionPane.showMessageDialog(frame,
            "Number of the attributes of one component are wrong",
            "Attribute number Error",
            JOptionPane.ERROR_MESSAGE);
    }

    //NULL VALUE DIALOG
    public static void nullValueDialog(JFrame frame){
        JOptionPane.showMessageDialog(frame,
            "Some Attribute are null",
            "Null Attribute Error",
            JOptionPane.ERROR_MESSAGE);
    }

    //TYPE VALUE DIALOG
    public static void typeValueErrorDialog(JFrame frame){
        JOptionPane.showMessageDialog(frame,
            "Some Attribute Type are wrong",
            "Type Error",
            JOptionPane.ERROR_MESSAGE);
    }

    //SWITCH TEXT DIALOG
    public static void switchTextDialog(JFrame frame){
        JOptionPane.showMessageDialog(frame,
            "Switch default Attribute only can be on or off",
            "Switch Text Error",
            JOptionPane.ERROR_MESSAGE);
    }

}
