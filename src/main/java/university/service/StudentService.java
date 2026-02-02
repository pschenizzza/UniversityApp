package university.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.entity.Student;
import university.entity.StudentGroup;
import university.repository.StudentGroupRepository;
import university.repository.StudentRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentGroupRepository groupRepository;

    @Transactional
    public void addStudent(Long groupId, String fullName, LocalDate admissionDate) {
        StudentGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found: " + groupId));

        Student student = new Student();
        student.setGroup(group);
        student.setFullName(fullName.trim());
        student.setAdmissionDate(admissionDate);

        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new EntityNotFoundException("Student not found: " + studentId);
        }
        studentRepository.deleteById(studentId);
    }
}
