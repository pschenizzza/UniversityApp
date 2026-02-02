package university.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import university.entity.StudentGroup;
import university.repository.StudentGroupRepository;
import university.service.StudentGroupService;
import university.web.dto.CreateGroupRequest;
import university.web.dto.GroupRowView;

@Controller
@RequiredArgsConstructor
public class GroupsController {

  private static final int PAGE_SIZE = 50;

  private final StudentGroupRepository groupRepository;
  private final StudentGroupService groupService;

  @GetMapping("/")
  public String home() {
    return "redirect:/groups";
  }

  @GetMapping("/groups")
  public String groupsPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

    if (page < 0) {
      page = 0;
    }

    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
    Page<GroupRowView> groupsPage = groupRepository.findGroupRowsOrdered(pageable);

    model.addAttribute("groupsPage", groupsPage);
    model.addAttribute("createGroup", new CreateGroupRequest());
    return "groups";
  }

  @PostMapping("/groups")
  public String createGroup(
      @Valid @ModelAttribute("createGroup") CreateGroupRequest req,
      BindingResult bindingResult,
      @RequestParam(name = "page", defaultValue = "0") int page,
      Model model) {

    if (bindingResult.hasErrors()) {
      return reloadGroupsPage(page, model, req);
    }

    try {
      StudentGroup createdGroup = groupService.createGroup(req.getNumber());
      return "redirect:/groups/" + createdGroup.getId();
    } catch (IllegalArgumentException ex) {
      bindingResult.rejectValue("number", "duplicate", ex.getMessage());
      return reloadGroupsPage(page, model, req);
    }
  }

  private String reloadGroupsPage(int page, Model model, CreateGroupRequest req) {
    if (page < 0) {
      page = 0;
    }
    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
    Page<GroupRowView> groupsPage = groupRepository.findGroupRowsOrdered(pageable);

    model.addAttribute("groupsPage", groupsPage);
    model.addAttribute("createGroup", req);
    return "groups";
  }
}
