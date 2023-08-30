package final_backend.Comments.model;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class CommentRequest {
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public String getUid() {
        return uid;
    }

    private String uid;

}
