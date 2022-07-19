package bio.link.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String bio;

    @Lob
    private String image;

    @Column(name = "design_id")
    private Long activeDesign;

    @Column(name = "show_logo")
    private Boolean showLogo;

    @Column(name = "show_nsfw")
    private Boolean showNSFW;

    @Column(name = "user_id")
    private Long userId;
}