package vn.edu.eiu.testlab.fecse4562131200006demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Major;
import vn.edu.eiu.testlab.fecse4562131200006demo.repository.MajorRepo;

import java.util.List;

@Service //Có quyền dùng @Component
public class MajorService {
    //DI: dùng dependency injection. Có 3 cách: field, constructor, setter
    @Autowired
    private MajorRepo majorRepo;

    //Hàm phục vụ lưu major xuống db
    public void saveMajor(Major major) {
        majorRepo.save(major);
    }

    public List<Major> getAllMajors() {
        return majorRepo.findAll();
    }
}
