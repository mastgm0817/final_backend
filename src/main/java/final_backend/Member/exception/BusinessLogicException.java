package final_backend.Member.exception;

public class BusinessLogicException extends RuntimeException {
    private ExceptionCode code;

    public BusinessLogicException(ExceptionCode code) {
        super(code.getMessage());  // getMessage()는 ExceptionCode enum에서 정의해야 함
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}

