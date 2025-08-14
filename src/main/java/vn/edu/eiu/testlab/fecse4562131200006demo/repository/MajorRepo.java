package vn.edu.eiu.testlab.fecse4562131200006demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.Major;
@Repository //Có quyền dùng @Component
/*JpaRepository là interface trong spring data jpa. Chứa tất cả các hàm abstract xử lý
Lúc kế thừa sẽ truyền vào tên class entity và kiểu dữ liệu của khóa chính
Trong JpaRepository đã xây dựng sẵn các phương thức abstract tự sinh hầu như là phục vụ
được các truy xuất cơ bản xuống db
 */
public interface MajorRepo extends JpaRepository<Major,Long> {
    //Không cần code các hàm đã có sẵn
}
