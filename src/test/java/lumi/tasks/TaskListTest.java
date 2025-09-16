package lumi.tasks;

import lumi.exceptions.LumiException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TaskList} class.
 * These tests verify that valid tasks are added to the list.
 */
public class TaskListTest {
    /**
     * Tests that valid {@link Todo} is added to {@link TaskList}.
     */
    @Test
    public void addTodoTest() {
        TaskList tasks = new TaskList();
        String result = assertDoesNotThrow(() -> tasks.add("todo homework"));
        assertEquals("Task added: [T][ ] homework", result);
        assertInstanceOf(Todo.class, tasks.getList().get(0));
    }

    /**
     * Tests that valid {@link Event} is added to {@link TaskList}.
     */
    @Test
    public void addEventTest() {
        TaskList tasks = new TaskList();
        String result = assertDoesNotThrow(() -> tasks.add("event CS2100 Lab "
                + "/from 15/10/2025 16:00 /to 15/10/2025 18:00"));
        assertEquals("Task added: [E][ ] CS2100 Lab|From: 15 10 2025 16:00|To: 15 10 2025 18:00", result);
        assertInstanceOf(Event.class, tasks.getList().get(0));
    }

    /**
     * Tests that valid {@link Event} is added to {@link TaskList}.
     */
    @Test
    public void addDeadlineTest() {
        TaskList tasks = new TaskList();
        String result = assertDoesNotThrow(() -> tasks.add("deadline CS2100 project "
                + "/by 15/10/2025 16:00"));
        assertEquals("Task added: [D][ ] CS2100 project|By: 15 10 2025 16:00", result);
        assertInstanceOf(Deadline.class, tasks.getList().get(0));
    }

    /**
     * Tests that invalid {@link Todo} throws LumiException and is not added to {@link TaskList}.
     */
    @Test
    public void addInvalidTodoTest() {
        TaskList tasks = new TaskList();
        assertThrows(LumiException.class, () -> tasks.add("todo      "),
                "Please add a todo task in the format: todo <task> (task should not be empty :> )");
        assertEquals(0, tasks.getList().size());
    }

    /**
     * Tests that invalid {@link Event} throws LumiException and is not added to {@link TaskList}.
     */
    @Test
    public void addInvalidEventTest() {
        TaskList tasks = new TaskList();
        assertThrows(LumiException.class, () -> tasks.add("event /from 12pm /to 5pm"),
                "Please enter a date in the correct format");
        assertEquals(0, tasks.getList().size());
    }

    /**
     * Tests that invalid {@link Deadline} throws LumiException and is not added to {@link TaskList}.
     */
    @Test
    public void addInvalidDeadlineTest() {
        TaskList tasks = new TaskList();
        assertThrows(LumiException.class, () -> tasks.add("deadline /from 12pm /to 5pm"),
                "Please add the full details!");
        assertEquals(0, tasks.getList().size());
    }

}
