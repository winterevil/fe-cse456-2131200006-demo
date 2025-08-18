package vn.edu.eiu.testlab.fecse4562131200006demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Major;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Student;
import vn.edu.eiu.testlab.fecse4562131200006demo.service.MajorService;
import vn.edu.eiu.testlab.fecse4562131200006demo.service.StudentService;

import java.util.ArrayList;
import java.util.List;

/** UI -- Controller(Đang ở đây) -- Service -- Repository -- [(JDBC) -- DB(Mysql)]
 * mỗi một url thì cần phải có một hàm xử lý
 * Đối với form thì dùng @PostMapping*/
@Controller
public class StudentController {
    //Tiêm service để truy xuất db
    @Autowired //tiêm bằng field
    private StudentService studentServ;
    @Autowired
    private MajorService majorServ;

    //url localhost:8080/students --> trả về trang student-list.html
    /*
     * link này có 2 cách request:
     * 1. Gõ trực tiếp
     *   - Khi gõ trực tiếp link /students nếu hàm xử lý có @RequestParam thì sẽ bị lỗi Null (NPE), nên phải xử lý bằng 1 trong 2 cách: gán default value cho @RequestParam hoặc thêm thuộc tính required = false
     * 2. Thông qua nút bấm search*/
    @GetMapping("students")
    public String students(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model) {
        List<Student> studentList = new ArrayList<>();
        if(keyword.isBlank()){
            //Lấy danh sách sinh viên tự db
            studentList = studentServ.getAllStudents();
        }
        else {
            studentList = studentServ.searchStudentByName(keyword);
        }
        //Gửi qua view cho thymeleaf mix với html tag
        model.addAttribute("studentList", studentList);

        return "student-list"; //student-list.html
    }

    //Hàm xử lý url: localhost:8080/student/edit/{id}
    @GetMapping("student/edit/{id}")
    public String editStudent(@PathVariable("id") String id, Model model) {
        //Lấy sinh viên viên từ db có mã là id.
        Student s = studentServ.getStudentById(id);

        //Gửi qua cho form chỉnh sửa
        model.addAttribute("student", s);

        //Gửi thêm danh sách major để làm select (comboBox)
        List<Major> majorList = majorServ.getAllMajors();
        model.addAttribute("majorList", majorList);

        //Gửi kèm một attribute để báo là edit student
        model.addAttribute("formMode","edit");

        return "student-form"; //Trả về trang form chỉnh sửa thông tin sinh viên.
    }

    //Hàm xử lí link thêm mới sinh viên
    @GetMapping("/student/add")
    public String addStudent(Model model) {
        //Lấy toàn bộ major để gửi qua comboBox(select) của form
        //Note: attributeName phải giống với hàm edit
        List<Major> majorList = majorServ.getAllMajors();
        model.addAttribute("majorList", majorList);

        //Tạo mới và gửi một sinh viên chưa có thông tin để gửi qua form
        //Note: attributeName phải giống với hàm edit
        model.addAttribute("student", new Student());

        //Gửi kèm một attribute để báo là thêm student
        model.addAttribute("formMode","add");
        return "student-form";
    }

    //Hàm xử lý cho url /student/form, khi người dùng bấm save trên form bằng Post method
    @PostMapping("/student/form")
    public String saveStudent(@Valid @ModelAttribute("student") Student s, BindingResult result, Model model, @RequestParam("formMode") String formMode) {
        //Lấy thông tin gửi từ form xuống, Nếu có lỗi thì sẽ quay lại trang form, kèm theo các message
        if(result.hasErrors()){
            model.addAttribute("formMode",formMode);
            model.addAttribute("majorList", majorServ.getAllMajors());

            return "student-form";
        }

        /*Nếu thêm mới mà trùng key thì báo lỗi và trả lại trang form kèm lỗi:
         * Lấy key đem dò trong db xem đã có hay chưa (cần service?)
         * Nếu có thì quay lại form gửi kèm thông báo trùng key.*/
        if(formMode.equals("add")){
            if(studentServ.checkExistsById(s.getId())){
                model.addAttribute("formMode",formMode);
                model.addAttribute("majorList", majorServ.getAllMajors());
                model.addAttribute("duplicateId","Id is already exists");
                return "student-form";
            }
        }

        //Nếu data ok hết thì lưu sinh viên và chuyển qua trang students
        studentServ.saveStudent(s);
        //Validate coi thông tin có hợp lệ không
        //Lưu xuống database
        //Trả về url students để hiển thị danh sách sinh viên đã cập nhật bằng redirect
        return "redirect:/students";
    }

    //Hàm xử lý link xóa môt sinh viên /student/delete
    @GetMapping("student/delete/{id}")
    public String deleteStudent(@PathVariable("id") String id) {
        //Gọi service hực hiện xóa sinh viên
        studentServ.deleteStudentById(id);
        //Trả về trang student
        return "redirect:/students";
    }
}