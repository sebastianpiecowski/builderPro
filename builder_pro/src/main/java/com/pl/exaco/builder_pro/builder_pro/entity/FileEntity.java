package entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "File")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "FileName")
    private String fileName;
    @Column(name= "UploadDate")
    private Timestamp uploadDate;
    @ManyToOne
    @JoinColumn(name = "BuildId")
    private Integer buildId;

}
