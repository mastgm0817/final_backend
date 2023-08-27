package final_backend.Member.exception;

public class CouponAlreadyAssignedException extends RuntimeException {
    public CouponAlreadyAssignedException(String message) {
        super(message);
    }
}
