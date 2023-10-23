import javax.swing.*;

public class PizzaOrderRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaOrderFrame frame = new PizzaOrderFrame();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
