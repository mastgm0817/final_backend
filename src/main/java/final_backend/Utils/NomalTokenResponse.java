package final_backend.Utils;

import lombok.Data;
@Data
public class NomalTokenResponse {
        private String token;

        public NomalTokenResponse(String token, String profileImage) {
            this.token = token;
            this.token = profileImage;
        }

}
