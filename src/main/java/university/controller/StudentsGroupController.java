package university.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import university.entity.Student;
import university.entity.StudentGroup;
import university.repository.StudentRepository;
import university.service.StudentGroupService;
import university.service.StudentService;
import university.web.dto.CreateStudentRequest;

@Controller
@RequiredArgsConstructor
public class StudentsGroupController {

  private final StudentService studentService;
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
    model.addAttribute("students", studentRepository.findByGroupIdOrderByFullNameAsc(groupId));
    model.addAttribute("groupId", groupId);
    return "students :: table";
  }

  @PostMapping(
      value = "/groups/{groupId}/students",
      consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String addStudentFromPage(
      @PathVariable Long groupId,
      @Valid @ModelAttribute("createStudent") CreateStudentRequest req,
      BindingResult bindingResult,
      Model model) {
    StudentGroup group = groupService.getOrThrow(groupId);
    model.addAttribute("group", group);
    model.addAttribute("students", studentRepository.findByGroupIdOrderByFullNameAsc(groupId));
    if (bindingResult.hasErrors()) {
      return "group";
    }
    studentService.addStudent(groupId, req.getFullName(), req.getAdmissionDate());
    return "redirect:/groups/" + groupId;
  }
}
