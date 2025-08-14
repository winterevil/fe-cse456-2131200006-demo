package vn.edu.eiu.testlab.fecse4562131200006demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_Major")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "Desciption", columnDefinition = "TEXT")
    private String description;

    //Kết nối với bảng sv (bidirectional)
    //mappedBy ="tên thuộc tính obj của Major đã đặt bên Student;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "major")
    private List<Student> studentList = new ArrayList<>();

    //Bổ sung 2 phương thức thêm sv và xóa sv
    //Thêm sv cho major
    public void addStudent(Student student) {
        //Bổ sung student cho major
        studentList.add(student);
        //Bổ sung major cho student
        student.setMajor(this);
    }

    //Xóa sv ra khỏi major
    public void removeStudent(Student student) {
        studentList.remove(student);
        student.setMajor(null);
    }
    //Nếu ID tự tăng thì phải tự thêm 1 constructor bỏ ID
    public Major(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
