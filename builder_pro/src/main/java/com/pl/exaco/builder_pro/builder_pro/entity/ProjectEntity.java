package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Project")
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private Integer id;
    @Column(name="Name")
    private String name;
    @Column(name="Thumbnail")
    private String thumbnail;
    @Column(name="LastBuildFileName")
    private String lastBuildFileName;

}
