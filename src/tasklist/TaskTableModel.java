package tasklist;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class TaskTableModel extends AbstractTableModel {
    private final List<Task> tasks;
    private final boolean showCompletedDate;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");


    private final String[] columnNamesWithCompleted = {"Priority", "Date Entered", "Date Completed", "Notes"};
    private final String[] columnNamesWithoutCompleted = {"Priority", "Date Entered", "Notes"};

    public TaskTableModel(List<Task> tasks, boolean showCompletedDate) {
        this.tasks = tasks;
        this.showCompletedDate = showCompletedDate;
    }
    public Task getTaskAt(int row) {
        return tasks.get(row); // assuming you have a List<Task> tasks field
    }


    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return showCompletedDate ? 4 : 3;
    }

    @Override
    public String getColumnName(int column) {
        return showCompletedDate ? columnNamesWithCompleted[column] : columnNamesWithoutCompleted[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = tasks.get(rowIndex);
        if (showCompletedDate) {
            switch (columnIndex) {
                case 0: return task.getPriorityGroup();
                case 1: return formatDate(task.getDateEntered());
                case 2: return formatDate(task.getDateFinished());
                case 3: return task.getTaskNotes();
            }
        } else {
            switch (columnIndex) {
                case 0: return task.getPriorityGroup();
                case 1: return formatDate(task.getDateEntered());
                case 2: return task.getTaskNotes();
            }
        }
        return null;
    }
    private String formatDate(LocalDate date) {
        return (date != null) ? date.format(DATE_FORMATTER) : "";
    }
}

