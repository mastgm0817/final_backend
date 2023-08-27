package final_backend.Calendar.exception;

public class ScheduleContentEmptyException extends RuntimeException {
    public ScheduleContentEmptyException(String message) {
        super(message);
    }
}
