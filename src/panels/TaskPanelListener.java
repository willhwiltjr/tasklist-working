package panels;

import tasklist.Task;

public interface TaskPanelListener {
    void onTaskSubmitted(Task task);
    void onTaskCancelled();
}

