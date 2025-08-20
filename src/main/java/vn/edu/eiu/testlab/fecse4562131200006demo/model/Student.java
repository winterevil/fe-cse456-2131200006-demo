package vn.edu.eiu.testlab.fecse4562131200006demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_Student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) //tự động ghi nhật ký
public class Student {
    @Id
    @Column(name = "ID", columnDefinition = "CHAR(5)")
    @Size(min=5, max=5, message="Length of ID must be exactly 5 characters")
    @NotBlank(message = "ID must not be left blank")
    private String id;

    @Column(name = "Name", columnDefinition = "NVARCHAR(100)", nullable = false)
    @NotNull(message = "Name must not be null")
    @Size(min=5, max=100, message="Length of name must be between 5 and 100")
    @Pattern(regexp="^(\\p{Lu}\\p{Ll}+)(\\s\\p{Lu}\\p{Ll}+)*$",
            message="Each word must start with an uppercase letter")
    private String name;

    @Column(name = "Year of Birth", nullable = false)
    @Min(value = 2000, message = "Yob must be from 2000")
    @Max(value = 2007,message = "Yob must be to 2007")
    @NotNull(message = "Yob must not be null")
    private int yob;

    @Column(name = "Gpa", nullable = false)
    @Min(value = 0, message = "Gpa must be greater than or equal 0")
    @Max(value = 100, message = "Gpa must be less than or equal 100")
    private double gpa;

    @ManyToOne
    @JoinColumn(name = "majorID") //Để báo là tui muốn đặt tên cột khóa ngoại
    private Major major; //Cái này sẽ gắn vào mappedBy bên Major

    //thuộc tính ghi nhận ngày giờ tạo
    @CreatedDate //tự động ghi ngày tạo sv
    private LocalDateTime createdDate;

    @LastModifiedDate //ghi nhận thời gian chỉnh sửa lần cuối
    private LocalDateTime modifiedDate;

//    @CreatedBy //ghi nhận user tạo
//    private String createdBy;
//    @LastModifiedBy //ghi nhận người chỉnh sửa cuối cùng
//    private String modifiedBy;

    //Bổ sung thêm constructor không có major, vì lúc thêm mới sv chưa có thông tin major
    public Student(String id, String name, int yob, double gpa) {
        this.id = id;
        this.name = name;
        this.yob = yob;
        this.gpa = gpa;
    }
}
