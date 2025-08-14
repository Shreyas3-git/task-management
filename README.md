# Task Management API

## Features

- Create, Read, Update, Delete tasks
- Task status management (PENDING → IN_PROGRESS → COMPLETED)
- Priority levels (LOW, MEDIUM, HIGH, CRITICAL)
- Input validation with detailed error messages
- Proper exception handling
- H2 in-memory database
- MapStruct for DTO mapping
- Lombok for reduced boilerplate
- Unit and integration tests


## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/v1/tasks` | Create a new task |
| GET | `/v1/tasks/{id}` | Get task by ID |
| GET | `/v1/tasks` | Get all tasks (paginated) |
| GET | `/v1/tasks/status/{status}` | Get tasks by status |
| GET | `/v1/tasks/priority/{priority}` | Get tasks by priority |
| GET | `/v1/tasks/search?title={title}` | Search tasks by title |
| PUT | `/v1/tasks/{id}` | Update task |
| DELETE | `/v1/tasks/{id}` | Delete task |


## Validation Rules

### Task Creation
- **Title**: 3-100 characters, required
- **Description**: 5-1000 characters, required  
- **Priority**: Must be LOW, MEDIUM, HIGH, or CRITICAL
- **Due Date**: Must be present or future date

### Task Update
- All fields optional (partial update supported)
- Status transitions: PENDING → IN_PROGRESS → COMPLETED
- Same validation rules as creation for provided fields

## Error Responses

### Validation Error Example
```json
{
  "success": false,
  "message": "Validation failed",
  "errors": ["Title must be between 3 and 100 characters"],
  "data": {
    "title": "Title must be between 3 and 100 characters"
  },
  "timestamp": "2025-01-15T10:30:00"
}
```

### Business Rule Error Example
```json
{
  "success": false,
  "message": "Cannot change task status from COMPLETED to PENDING",
  "errors": ["Business rule violation"],
  "timestamp": "2025-01-15T10:30:00"
}
```


## Design Patterns Used

1. **Repository Pattern**: Data access abstraction
2. **Service Layer Pattern**: Business logic separation
3. **DTO Pattern**: Data transfer objects for API
4. **Builder Pattern**: Lombok @Builder for object creation
5. **Strategy Pattern**: Ready for complex business rules
6. **MVC Pattern**: Controller-Service-Repository architecture

## SOLID Principles Applied

- **S**ingle Responsibility: Each class has one clear purpose
- **O**pen/Closed: Easily extensible through interfaces
- **L**iskov Substitution: Proper inheritance and interface implementation
- **I**nterface Segregation: Focused, cohesive interfaces
- **D**ependency Inversion: Dependencies injected via constructor


