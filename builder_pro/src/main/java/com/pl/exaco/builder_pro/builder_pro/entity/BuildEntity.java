package entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "Build")
public class BuildEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name="Id")
    private Integer id;
    @Column(name="BuildType")
    private String buildType;
    @ManyToOne
    @JoinColumn(name="Id")
    private String projectId;

}
