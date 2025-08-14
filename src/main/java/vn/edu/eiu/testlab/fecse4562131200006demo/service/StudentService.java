package vn.edu.eiu.testlab.fecse4562131200006demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Student;
import vn.edu.eiu.testlab.fecse4562131200006demo.repository.StudentRepo;

import java.util.List;

@Service
public class StudentService {
    //Tiêm dependency repo để thao tác db
    @Autowired
    private StudentRepo studentRepo;

    //Hàm lưu sv xuống db
    public void saveStudent(Student student) {
        studentRepo.save(student);
    }

    //Hàm lấy tất cả sv (phjc vụ cho trang student-list)
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    //Hàm lấy sv bằng id phục vụ cho việc edit trên view
    public Student getStudentById(String id) {
        return studentRepo.findById(id).orElseThrow(); //hàm tự sinh
    }

    public void deleteStudentById(String id) {
        studentRepo.deleteById(id);
    }

    public List<Student> searchStudentByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return studentRepo.findAll();
        }
        return studentRepo.searchAllByNameContainingIgnoreCase(keyword);
    }

    //Hàm phục vụ kiểm tra trùng pk
    public Boolean checkExistsById(String id) {
        return studentRepo.existsById(id);
    }
}
