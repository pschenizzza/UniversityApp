package university.controller;

import university.web.dto.CreateStudentRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import university.service.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/groups/{groupId}/students")
    public ResponseEntity<Void> addStudent(
            @PathVariable Long groupId,
            @Valid @RequestBody CreateStudentRequest req) {

        studentService.addStudent(groupId, req.getFullName(), req.getAdmissionDate());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
