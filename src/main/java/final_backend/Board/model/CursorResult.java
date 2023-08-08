package final_backend.Board.model;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
@CrossOrigin
public class CursorResult<T> {
    private List<T> values;
    private Boolean hasNext;
    public CursorResult(List<T> values, Boolean hasNext){
        this.values=values;
        this.hasNext=hasNext;
    }
    public List<T> getValues() {
        return values;
    }
    public Boolean getHasNext() {
        return hasNext;
    }
}