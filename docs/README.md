# Babe Chatbot User Guide

![Babe Chatbot Interface](Ui.png)

Babe is an intuitive task management chatbot that helps you organize your deadlines, events, and todos with priority levels. With a resizable chat window and graceful error handling, Babe makes task management a breeze.

## Adding Tasks

Babe supports three types of tasks:
- Deadlines
- Events
- Todos

### Task Priority Levels

All tasks can be assigned a priority level from 1 to 3:
- Priority 1: Most important/urgent
- Priority 2: Medium importance
- Priority 3: Lowest importance

### Command Format

- `<task_type> "<task_description>" /p <priority_number>`

### Examples

1. Adding a deadline:

   deadline "Submit project proposal" /p 1 /date 2025-03-01

2. Creating an event:

   event "Team meeting" /p 2 /from 2025-02-22-14:00 /to 2025-02-22-15:30

3. Adding a todo:

   todo "Buy groceries" /p 3

## Chat Window Features

### Resizable Interface
- Click and drag the window edges to resize the chat interface
- Supports minimum and maximum size limits for optimal viewing
- Automatically adjusts content layout to fit the new dimensions

### Error Handling

Babe handles various error scenarios gracefully:

- Invalid priority levels
- Missing required arguments
- Invalid date formats

## Additional Features

- Task modification and deletion
- Task completion tracking
- Storing list between conversations
- Task list viewing and filtering by keyword search

