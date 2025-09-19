# Lumi User Guide

![Screenshot of the Lumi app](../../../../../var/folders/kd/7083q6f97gs59ryw90v8_wkw0000gn/T/TemporaryItems/NSIRD_screencaptureui_nOpQxh/Screenshot%202025-09-19%20at%2010.52.33%E2%80%AFAM.png)

LUMI (˶ˆᗜˆ˵) is a one-of-a-kind chatbot to help you manage your tasks.

It supports **todo**, **event** and **deadline** tasks, plus find, mark/unmark and delete!

## Viewing list
Prints out the list of tasks.

**Command**

`list`

**Example & Expected Output**

Returns the list of tasks in the order they were added.  
Each task is shown with its type, status, and details.

Expected output:
```
1.[T][ ] buy milk
2.[D][ ] CS2101 presentation|By: 15 10 2025, 10:00
3.[E][X] CS2100 lab|From: 15 10 2025, 15:00|To: 15 10 2025, 17:00
```


## Adding deadlines
Creates a deadline task.

**Command**

`deadline <task description> /by <date and time>`

**Supported DateTime Formats**
1. dd MM yyyy HH:mm
2. dd/MM/yyyy HH:mm

**Example & Expected Output**

If the task is added successfully, it will appear in the task list
as an unmarked deadline task, and a success message will be returned.


Example: `deadline homework /by 01/01/2026 16:00`

Expected output:
```
Task Added: [D][ ] homework|By: 01 01 2026 16:00
```

## Adding events
Creates an event task.

**Command**

`event <task description> /from <date and time> /to <date and time>`

**Supported DateTime Formats**
1. dd MM yyyy HH:mm
2. dd/MM/yyyy HH:mm

**Example & Expected Output**

If the task is added successfully, it will appear in the task list
as an unmarked event task, and a success message will be returned.

Example: `event party /from 01/01/2026 16:00 /to 01/01/2026 17:00`

Expected output:
```
Task Added: [E][ ] party|From: 01 01 2026 16:00|To: 01 01 2026 17:00
```

## Adding todo
Creates a todo task.

**Command**

`todo <description>`

**Example & Expected Output**

If the task is added successfully, it will appear in the task list
as an unmarked todo task, and a success message will be returned.


Example: `todo homework`

Expected output:
```
Task Added: [T][ ] homework
```


## Deleting tasks
Deletes the task at the given index.

**Command**

`delete <index>`

**Example & Expected Output**

If the deletion is successful, the task will no longer appear in the task list
and a success message will be returned.

Example: `delete 1`

Expected output:
```
This task has been deleted: <task description>
```

## Finding tasks
Finds a task containing the given keyword.

**Command**

`find <keyword>`

**Example & Expected Output**


Tasks containing the given keyword will be printed. Otherwise, the message
`I couldn't find any matching tasks :<` will be returned.

Example: `find i`

Expected output:
```
Here's the tasks I found ><
[T][ ] assignment
```

## Marking tasks

Marks the task at the given index as done.

**Command**
`mark <index>`

**Example & Expected Output**

If the task is not already marked as done, a success message will be returned and
the task will be marked as done. Otherwise, the message `This task has already been
marked done` will be returned.

Example: `mark 1`

Expected output:
```
Yay! I've marked your task as done: [T][X] homework
```

## Unmarking tasks

Unmarks the task at the given index.

**Command**
`unmark <index>`

**Example & Expected Output**

If the task is marked as done, a success message will be returned and
the task will be unmarked. Otherwise, the message `This task has already been
marked undone` will be returned.

Example: `unmark 1`

Expected output:
```
Oki, I've marked your task undone: [T][ ] homework
```


## Getting help

Returns a list of command descriptions.

**Command**

`help`

# Resources
**Pink Jellyfish Icon:**
https://pin.it/1mbvEZdY8

**Blue Jellyfish Icon:**
https://pin.it/1mbvEZdY8

**Beach Background Image:**
https://pin.it/4hGIHKhPt


