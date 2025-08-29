package lumi.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    /** Test if todo, deadline and event tasks can be added to the list */
    @Test
    public void addTaskTest() {
        TaskList tasks = new TaskList();
        tasks.add("todo homework"); // tests if todo task has been added
        assertEquals(1, tasks.getList().size());
        tasks.add("deadline test /by 12/10/2025 16:00"); // tests if deadline task has been added
        assertEquals(2, tasks.getList().size());
        tasks.add("event party /from 12/01/2025 15:00 /to 13/01/2025 15:00"); // tests if event task has been added
        assertEquals(3, tasks.getList().size());
    }

    /** Tests if invalid task entries will be ignored */
    @Test
    public void invalidTaskTest() {
        TaskList tasks = new TaskList();
        tasks.add("todo   "); // tests invalid task
        assertEquals(0, tasks.getList().size());
        tasks.add("deadline test /by 12/100/2005 17:00"); // tests invalid date
        assertEquals(0, tasks.getList().size());
        tasks.add(" "); // test empty input
        assertEquals(0, tasks.getList().size());
    }
}
