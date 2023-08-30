package final_backend.Member.exception;

public enum ExceptionCode {
    PAY_CANCEL("Payment cancellation error"),
    PAY_FAILED("Payment failed error"),
    // 다른 예외 코드와 메시지
    ;

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}