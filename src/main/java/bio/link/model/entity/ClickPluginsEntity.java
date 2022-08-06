package bio.link.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "click_plugins")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickPluginsEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "click_count")
    private Long clickCount;

    private LocalDate date;

    @Column(name = "plugins_id")
    private Long pluginsId;

}
