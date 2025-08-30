package lumi.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link TaskList} class.
 * These tests verify that valid tasks are added to the list.
 */
public class TaskListTest {
    /**
     * Tests that valid {@link Todo}, {@link Deadline} and {@link Event} tasks are added to the {@link TaskList}.
     */
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

    /**
     * Tests if invalid commands will not be added.
     */
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
