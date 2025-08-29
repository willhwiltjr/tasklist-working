import java.time.*;


public class Task {
    private PriorityGroup prioritygroup;
    private LocalDate dateEntered;
    private LocalDate dateFinished;
    private String taskNotes;
    private Boolean isCompleted;

    public Task(PriorityGroup level, String notes) {
        this.prioritygroup = level;
        this.dateEntered = LocalDate.now();
        this.dateFinished = null;
        this.taskNotes = notes;
        this.isCompleted = false;
    }

    public Task(PriorityGroup priority, LocalDate dateEntered, LocalDate dateFinished, String notes, boolean isCompleted) {
        this.prioritygroup = priority;
        this.dateEntered = dateEntered;
        this.dateFinished = dateFinished;
        this.taskNotes = notes;
        this.isCompleted = isCompleted;
    }


    //"make a task form a csvline"
    public static Task fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", -1); // Handles empty strings

        PriorityGroup priority = PriorityGroup.valueOf(parts[0]);
        LocalDate dateEntered = LocalDate.parse(parts[1]);
        LocalDate dateFinished = parts[2].equals("null") ? null : LocalDate.parse(parts[2]);
        String notes = parts[3].replace("\"", "");
        boolean completed = Boolean.parseBoolean(parts[4]);

        return new Task(priority, dateEntered, dateFinished, notes, completed);
    }


    //"setters these will allow us to set the date finished and update the notes and completed boolean"//
    //"make a Task into a string to amend to a csv file"
    public String toCSV() {
        return String.join(",",
                prioritygroup.name(),
                dateEntered.toString(),
                dateFinished != null ? dateFinished.toString() : "null",
                "\"" + taskNotes.replace("\"", "\"\"") + "\"",  // Escape quotes
                isCompleted.toString()
        );
    }

    //"implement the dateFinished setter"//
    public void setDateFinished() {
        this.dateFinished = LocalDate.now();
    }
    //"implement the taskNotes updater"//
    public void setTaskNotes(String notes) {
        this.taskNotes = notes;
    }
    //"add an appender method for taskNotes"//
    public void updateTaskNotes(String notes){
        this.taskNotes = this.taskNotes.concat(notes);
    }
    //"switch the state of isCompleted"//
    public void switchCompleted(){
        this.isCompleted = !this.isCompleted;
    }
    //"setter for priority"
    public void setPriorityGroup(PriorityGroup newPriority) {
        this.prioritygroup = newPriority;
    }
    //"we need getters to enable us to compare"//
    //"private PriorityGroup;"
    public PriorityGroup getPriorityGroup() {
        return prioritygroup;
    }
    //    "private LocalDate dateEntered;"
    public LocalDate getDateEntered() {
        return dateEntered;
    }
    //    "private LocalDate dateFinished;"
    public LocalDate getDateFinished() {
        return dateFinished;
    }
    //    "private String taskNotes;"
    public String getTaskNotes() {
        return taskNotes;
    }
    //    "private Boolean isCompleted;"
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    //"compareTo first by priority group then by level"//
    @Override
    public int compareTo(Task other) {
        int result = Integer.compare(this.prioritygroup.getLevel(), other.prioritygroup.getLevel());
        if (result != 0) return result;
        return this.dateEntered.compareTo(other.dateEntered);
    }
}
