package vn.edu.eiu.testlab.fecse4562131200006demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Major;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Student;
import vn.edu.eiu.testlab.fecse4562131200006demo.service.MajorService;
import vn.edu.eiu.testlab.fecse4562131200006demo.service.StudentService;

import java.util.ArrayList;
import java.util.List;

/* UI -- Controller (Đang ở đây) -- Service -- Repository -- [(JDBC) -- DB (Mysql)]
Mỗi một url thì cần phải có một hàm xử l
Đối với form thì dùng @PostMapping
 */
@Controller
public class StudentController {
    //url localhost:8080/students ---> trả về trang student-list.html
    @Autowired //tiêm bằng field
    private StudentService studentSer;
    @Autowired
    private MajorService majorSer;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /*
    link này có 2 cách request
    1. Gõ trực tiếp
    - Khi gõ trực tiếp link /students nếu hàm xử lý có @RequestParam th sẽ bị lỗi Null (NPE),
    nên phải xử lý bằng 1 trong 2 cách: gán default value cho @RequestParam hoặc thêm
    thuộc tính required = false
    2. Thông qua nút bấm search
     */
    @GetMapping("students")
    public String students(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        //lấy danh sách sv tự do
        List<Student> studentList = new ArrayList<>();
        if (keyword == null || keyword.isBlank()) {
            studentList = studentSer.getAllStudents();
        } else {
            studentList = studentSer.searchStudentByName(keyword);
        }
        //Gửi qua view cho thymeleaf mix với html tag
        model.addAttribute("studentList", studentList);
        model.addAttribute("keyword", keyword);
        return "student-list"; //student-list.html
    }

    //Hàm xử lý url: localhost:8080/student/edit/{id}
    @GetMapping("student/edit/{id}")
    public String editStudent(@PathVariable("id") String id, Model model) {
        //Lấy sv từ db có mã là id
        Student s = studentSer.getStudentById(id);

        //Gửi qua cho form chỉnh sửa
        model.addAttribute("student", s);
        //Gửi thêm ds major để làm select (comboBox)
        List<Major> majorList = majorSer.getAllMajors();
        model.addAttribute("majorList", majorList);

        //Gửi kèm một attribute để báo là edit student
        model.addAttribute("formMode", "edit");
        return "student-form"; //Trả về trang form chỉnh sửa thông tin sinh viên
    }

    //Hàm xử lý cho url /student/form, khi người dùng bấm save trên form bằng Post method
    @PostMapping("/student/form")
    public String saveStudent(@Valid @ModelAttribute("student") Student s, BindingResult result,
                              Model model, @RequestParam("formMode") String formMode) {
        //Lấy thông tin gửi từ form xuống, Nếu có ỗi thì sẽ quay lại trang form kèm theo các message
        if (result.hasErrors()) {
            model.addAttribute("formMode", formMode);
            model.addAttribute("majorList", majorSer.getAllMajors());
            return "student-form";
        }
        //Nếu thêm mới mà trùng key thì báo lỗi và trả lại trang form kèm lỗi
        /*
        Lấy key đem dò trong db xem đã có hay chưa (cần service?)
        Nếu có thì quay lại form gửi kèm thông báo trùng key
         */
        if (formMode.equals("add")) {
            if (studentSer.checkExistsById(s.getId())) {
                model.addAttribute("formMode", formMode);
                model.addAttribute("majorList", majorSer.getAllMajors());
                model.addAttribute("duplicateId", "Id is already in use");
                return "student-form";
            }
        }
        studentSer.saveStudent(s);
        //Validate coi thông tin có hợp lệ không
        //Lưu xuống database
        //Trả về trang student-list bằng redirect
        return "redirect:/students";
    }

    //Hàm xử lý link thêm mới sv
    @GetMapping("student/new")
    public String addStudent(Model model) {
        //Lấy toàn bộ major để gửi qua combo box của form
        //Note: attribute name phải giống với hàm edit
        List<Major> majorList = majorSer.getAllMajors();
        model.addAttribute("majorList", majorList);

        //Tạo mới và gửi mt sv chưa có thông tin để gửi qua form
        //attribute name phải giống với hàm edit
        Student s = new Student();
        model.addAttribute("student", s);

        //Gửi kèm một attribute để báo là thêm student
        model.addAttribute("formMode", "add");
        return "student-form";
    }

    //Hàm xử lý link xóa một sv student/delete
    @GetMapping("student/delete/{id}")
    public String deleteStudent(@PathVariable("id") String id) {
        //Thực hiện xóa sv
        studentSer.deleteStudentById(id);
        //Trả về trang student
        return "redirect:/students";
    }
}
