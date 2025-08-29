import java.time.*;


public class Task {
    private PriorityGroup prioritygroup;
    private LocalDate dateEntered;
    private LocalDate dateFinished;
    private String taskNotes;
    private Boolean isCompleted;

    public Task(PriorityGroup level,LocalDate dateentered, String notes,Boolean isCompleted) {
        this.prioritygroup = level;
        this.dateEntered = LocalDate.now();
        this.dateFinished = null;
        this.taskNotes = notes;
        this.isCompleted = false;
    }

    //setters these will allow us to set the date finished and update the notes and completed boolean//

    //implement the dateFinished setter//
    public void setDateFinished() {
        this.dateFinished = LocalDate.now();
    }
    //implement the taskNotes updater//
    public void setTaskNotes(String notes) {
        this.taskNotes = notes;
    }
    //add an appender method for taskNotes//
    public void updateTaskNotes(String notes){
        String temp = this.taskNotes.concat(notes);
        this.taskNotes = temp;
    }
    //switch the state of isCompleted//
    public void switchCompleted(){
        this.isCompleted = !this.isCompleted;
    }
    //we need getters to enable us to compare//
    //private PriorityGroup pioritygroup;
    //    private LocalDate dateEntered;
    //    private LocalDate dateFinished;
    //    private String taskNotes;
    //    private Boolean isCompleted;
    public PriorityGroup getPriorityGroup() {
        return prioritygroup;
    }
    //    private LocalDate dateEntered;
    public LocalDate getDateEntered() {
        return dateEntered;
    }
    //    private LocalDate dateFinished;
    public LocalDate getDateFinished() {
        return dateFinished;
    }
    //    private String taskNotes;
    public String getTaskNotes() {
        return taskNotes;
    }
    //    private Boolean isCompleted;
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    //compareto first by priority group then by level//
    @Override
    public int compareTo(Task other) {
        int result = Integer.compare(this.prioritygroup.getLevel(), other.prioritygroup.getLevel());
        if (result != 0) return result;
        return this.dateEntered.compareTo(other.dateEntered);
    }
}
