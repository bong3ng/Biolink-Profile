package bio.link.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "design")
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
