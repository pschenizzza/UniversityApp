package university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import university.entity.Student;
import university.entity.StudentGroup;
import university.repository.StudentRepository;
import university.service.StudentGroupService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentsGroupController {

    private final StudentGroupService groupService;
    private final StudentRepository studentRepository;

    @GetMapping("/groups/{groupId}")
    public String groupPage(@PathVariable Long groupId, Model model) {
        StudentGroup group = groupService.getOrThrow(groupId);
        List<Student> students = studentRepository.findByGroupIdOrderByFullNameAsc(groupId);

        model.addAttribute("group", group);
        model.addAttribute("students", students);
        return "group";
    }

    @GetMapping("/groups/{groupId}/students")
    public String studentsFragment(@PathVariable Long groupId, Model model) {
        List<Student> students = studentRepository.findByGroupIdOrderByFullNameAsc(groupId);

        model.addAttribute("groupId", groupId);
        model.addAttribute("students", students);
        return "students :: table";
    }

}
