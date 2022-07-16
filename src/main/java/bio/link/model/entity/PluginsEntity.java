package bio.link.model.entity;

<<<<<<< HEAD
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import lombok.*;

import javax.persistence.*;
import java.util.List;
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plugins")
public class PluginsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String image;
<<<<<<< HEAD
    @Column(name = "is_header")
    private Boolean isHeader;
    @Column(name = "is_plugin")
    private Boolean isPlugin;
    @Column(name = "is_hide")
    private Boolean isHide;
    @Column(name = "user_id")
    private Long userId;
=======
    private Boolean isHeader;
    private Boolean isPlugin;
    private Boolean isHide;
    private Long profileId;
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3

}
