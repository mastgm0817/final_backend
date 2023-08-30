package final_backend.Wishlist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import final_backend.Member.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Course {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long wid;

        @JsonProperty("사업장명")
        private String businessName;

        @JsonProperty("소재지전체주소")
        private String fullAddress;

        @JsonProperty("업태구분명")
        private String businessType;

        @JsonProperty("맛")
        private String taste;

        @JsonProperty("서비스")
        private String service;

        @JsonProperty("분위기")
        private String atmosphere;

        @JsonProperty("친절도")
        private String kindness;

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "uid")
        private User userWish;
}
