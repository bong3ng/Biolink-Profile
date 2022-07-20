package bio.link.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "designs")
public class DesignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "backgroud_img")
    private String backgroundImg;

    private String background;

    @Column(name = "btn_radius")
    private String btnRadius;

    @Column(name = "btn_bg")
    private String btnBg;

    @Column(name = "btn_border")
    private String btnBorder;

    @Column(name = "box_shadoe")
    private String boxShadow;

    @Column(name = "color_header")
    private String colorHeader;

    @Column(name = "color_link")
    private String colorLink;
}
