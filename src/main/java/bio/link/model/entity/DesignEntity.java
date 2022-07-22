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

    @Column
    private String name;

    @Column(name = "background_img")
    private String backgroundImg;

    @Column
    private String background;

    @Column(name = "btn_radius")
    private String btnRadius;

    @Column(name = "btn_bg")
    private String btnBg;

    @Column(name = "btn_width")
    private String btnBdWidth;

    @Column(name = "btn_color")
    private String btnBdColor;

    @Column(name = "btn_style")
    private String btnBdStyle;

    @Column(name = "box_shadow")
    private String boxShadow;

    @Column(name = "color_header")
    private String colorHeader;

    @Column(name = "color_link")
    private String colorLink;

    @Column(name = "font_family")
    private String fontFamily;

    @Column(name = "user_id")
    private Long userId;
}
