package vn.edu.eiu.testlab.fecse4562131200006demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Major;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Student;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.User;
import vn.edu.eiu.testlab.fecse4562131200006demo.service.MajorService;
import vn.edu.eiu.testlab.fecse4562131200006demo.service.UserService;

/*
Khi thêm data chú ý là phải thêm bảng 1 trước, thêm bảng nhiều sau
 */
@Component
public class DataInitializer implements CommandLineRunner {
    //Tiêm service để thao tác dữ liệu
    @Autowired //tiêm bằng field (có 3 cách)
    MajorService majorService;
    @Autowired
    UserService userService;
    @Override
    public void run(String... args) throws Exception {
        Major m1 = new Major("CSE - Kỹ Thuật Phần Mềm", "Ngành kỹ thuật phần mềm");
        Major m2 = new Major("CSW - Mạng và Truyền Thông Dữ Liệu", "Ngành Mạng và truyền thông dữ liệu");
        Student s1m1 = new Student("st1m1","Hồ Ngọc Hà", 2000, 85);
        Student s2m1 = new Student("st2m1","Lệ Quyên", 2000, 90);
        Student s3m1 = new Student("st3m1","Xuân Lan", 2001, 85);
        Student s1m2 = new Student("st1m2","Hà Anh", 2002, 95);
        Student s2m2 = new Student("st2m2","Thanh Hằng", 2001, 75);

        //Thêm student vào major
        m1.addStudent(s1m1);
        m1.addStudent(s2m1);
        m1.addStudent(s3m1);
        m2.addStudent(s1m2);
        m2.addStudent(s2m2);

        //Gọi service lưu db
        majorService.saveMajor(m1);
        majorService.saveMajor(m2);

        //Thêm mới user
        User admin = new User("admin","admin",1);
        User staff = new User("staff","staff",2);
        User student = new User("student","student",3);

        userService.save(admin);
        userService.save(staff);
        userService.save(student);
    }
}
