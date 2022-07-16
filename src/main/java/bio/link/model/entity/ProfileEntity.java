package bio.link.model.entity;

<<<<<<< HEAD
import javax.persistence.Column;
=======
>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
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
<<<<<<< HEAD
    @Column(name = "user_id")
=======

>>>>>>> 53508aaae065b12b148fc1b6a7cd23d605c1c8a3
    private Long userId;
}