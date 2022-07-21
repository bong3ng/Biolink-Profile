package bio.link.model.entity;

import lombok.*;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "click_profiles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickProfileEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "click_count")
    private Long clickCount;

    private LocalDate date;

    @Column(name = "profile_id")
    private Long profileId;
}
