package bio.link.model.entity;


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

    @Column(name = "is_header")
    private Boolean isHeader;
    @Column(name = "is_plugin")
    private Boolean isPlugin;
    @Column(name = "is_hide")
    private Boolean isHide;
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "num_location")
    private Long numLocation;

}
