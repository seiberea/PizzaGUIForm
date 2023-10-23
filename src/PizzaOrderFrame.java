import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class PizzaOrderFrame extends JFrame{
    private JRadioButton thinCrust, regularCrust, deepDish;
    private JComboBox<String> sizeComboBox;
    private JTextArea orderTextArea;
    private JCheckBox[] toppings;
    private JButton orderButton, clearButton, quitButton;

    public PizzaOrderFrame() {
        setTitle("Pizza Order Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel crustPanel = createTitledBorderPanel("Crust Type");
        JPanel sizePanel = createTitledBorderPanel("Pizza Size");
        JPanel toppingsPanel = createTitledBorderPanel("Topping");

        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep-Dish");
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDish);

        sizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large", "Super"});

        toppings = new JCheckBox[6];
        for (int i = 0; i < 6; i++) {
            toppings[i] = new JCheckBox("Toppings " + (i + 1));
            toppings[0] = new JCheckBox("Razz Berry");
            toppings[1] = new JCheckBox("Nanab Berry");
            toppings[2] = new JCheckBox("Pinap Berry");
            toppings[3] = new JCheckBox("Pamtre Berry");
            toppings[4] = new JCheckBox("Oran Berry");
            toppings[5] = new JCheckBox("Chesto Berry");
        }
        orderTextArea = new JTextArea(15, 40);
        orderTextArea.setEditable(false);
        orderTextArea.setWrapStyleWord(true);
        orderTextArea.setLineWrap(true);

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDish);
        sizePanel.add(sizeComboBox);
        for (JCheckBox topping : toppings) {
            toppingsPanel.add(topping);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        add(sizePanel, BorderLayout.NORTH);
        add(crustPanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.EAST);
        add(new JScrollPane(orderTextArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
            private void createOrder() {
            String selectedCrust = "";
            if (thinCrust.isSelected()) {
                selectedCrust = "Thin";
            } else if (regularCrust.isSelected()) {
                selectedCrust = "Regular";
            } else if (deepDish.isSelected()) {
                selectedCrust = "Deep-Dish";
            }

        String selectedSize = (String) sizeComboBox.getSelectedItem();

            StringBuilder selectedToppings = new StringBuilder();
            double totalToppingsCost = 0.0;
            for (JCheckBox topping : toppings){
                if (topping.isSelected()){
                    selectedToppings.append(topping.getText()).append(", ");
                    totalToppingsCost +=1.00;
                }
            }

            if (selectedCrust.isEmpty() || selectedSize.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please select both crust and size" , "Incomplete Order" , JOptionPane.WARNING_MESSAGE);
                return;
            }

            double baseCost;
            switch (selectedSize) {
                case "Small":
                    baseCost = 8.00;
                    break;
                case "Medium":
                    baseCost = 12.00;
                    break;
                case "Large":
                    baseCost = 16.00;
                    break;
                case "Super":
                    baseCost = 20.00;
                    break;
                default:
                    baseCost = 0.00;

            }

            double subTotal = baseCost + totalToppingsCost;
            double tax = 0.07 * subTotal;
            double totalCost = subTotal + tax;

            DecimalFormat df = new DecimalFormat("#.##");
            orderTextArea.setText("----- Pizza Order -----\n\n");
            orderTextArea.append("Crust: " + selectedCrust + "\n");
            orderTextArea.append("Size: " + selectedSize + "\n");
            orderTextArea.append("Toppings: " + (selectedToppings.length() > 0 ? selectedToppings.substring(0, selectedToppings.length() - 2) : "None") + "\n");
            orderTextArea.append("\nSub-total: $" + df.format(subTotal) + "\n");
            orderTextArea.append("Tax: $" + df.format(tax) + "\n");
            orderTextArea.append("------------------------\n");
            orderTextArea.append("Total: $" + df.format(totalCost) + "\n");
            orderTextArea.append("=================\n");

    }

    private void clearForm() {
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepDish.setSelected(false);

        sizeComboBox.setSelectedIndex(0);

        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }

        orderTextArea.setText("");
    }
    private JPanel createTitledBorderPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setLayout(new GridLayout(0,1));
        return panel;
    }
}
