import javax.swing.*;
import java.awt.*;

public class PokemonShinySIDCalculatorGUI {

    private final JFrame frame;
    private final JTextField tidField;
    private final JTextField pidField;
    private final JTextArea resultArea;

    public PokemonShinySIDCalculatorGUI() {
        frame = new JFrame("宝可梦闪光SID计算器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 280);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        pidField = new JTextField(10);
        tidField = new JTextField(10);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        panel.add(new JLabel("请输入PID:"));
        panel.add(pidField);
        panel.add(new JLabel("请输入TID:"));
        panel.add(tidField);
        panel.add(createButton("三代后 < 8为闪", 8, false));
        panel.add(createButton("六代后 < 16 为闪", 16, false));
        panel.add(createButton("八代 == 0 为方闪", 0, true));
        panel.add(createButton("八代大冒险 == 1 为闪", 1, true));
        panel.add(new JScrollPane(resultArea));

        frame.getContentPane().add(panel);
    }

    private JButton createButton(String text, int condition, boolean isEquals) {
        JButton button = new JButton(text);
        button.addActionListener(e -> calculate(condition, isEquals));
        return button;
    }

    public void show() {
        frame.setVisible(true);
    }

    private void calculate(int condition, boolean isEquals) {
        try {
            int tId = Integer.parseInt(tidField.getText());
            long pId = Long.parseLong(pidField.getText().substring(2), 16);

            int p1 = (int)(pId >>> 16);
            int p2 = (int)(pId & 0xFFFF);
            int p1_xor_p2 = p1 ^ p2;

            resultArea.setText("SID范围：\n");
            for (int i = 0; i <= 65536; i++) {
                int xorResult = (tId ^ i) ^ p1_xor_p2;
                if (isEquals ? xorResult == condition : xorResult < condition) {
                    resultArea.append(i + "\n");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "输入的PID和TID必须是有效的数值", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PokemonShinySIDCalculatorGUI().show());
    }
}
