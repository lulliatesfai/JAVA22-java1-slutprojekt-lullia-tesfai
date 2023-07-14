import java.awt.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalendarApp::new);
    }
}

class CalendarApp {
    private JFrame frame;
    private JPanel mainPanel;
    private Map<LocalDate, JTextArea> activityMap;
    private DateTimeFormatter dateFormatter;

    public CalendarApp() {
        activityMap = new HashMap<>();
        dateFormatter = DateTimeFormatter.ofPattern("dd MMMM");

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;

        LocalDate currentDate = LocalDate.now();
        LocalDate monday = currentDate.with(DayOfWeek.MONDAY);
        LocalDate sunday = currentDate.with(DayOfWeek.SUNDAY);

        for (LocalDate date = monday; date.isBefore(sunday.plusDays(1)); date = date.plusDays(1)) {
            JTextArea activityTextArea = new JTextArea(2, 15);
            activityMap.put(date, activityTextArea);

            JPanel dayPanel = new JPanel(new BorderLayout());
            JLabel dateLabel = new JLabel(date.format(dateFormatter));
            if (date.equals(currentDate)) {
                dateLabel.setForeground(Color.RED);
            }
            dayPanel.add(dateLabel, BorderLayout.NORTH);
            dayPanel.add(new JScrollPane(activityTextArea), BorderLayout.CENTER);

            mainPanel.add(dayPanel, constraints);

            constraints.gridy++;
        }

        JButton saveButton = new JButton("Spara");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveActivities();
            }
        });
        constraints.gridy++;
        mainPanel.add(saveButton, constraints);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void saveActivities() {
        for (LocalDate date : activityMap.keySet()) {
            JTextArea activityTextArea = activityMap.get(date);
            String activity = activityTextArea.getText();
            if (!activity.isEmpty()) {
                // Spara aktiviteten för datumet i en datastruktur eller utför annan lämplig hantering
                System.out.println(date.format(dateFormatter) + ": " + activity);
            }
        }
    }
}
