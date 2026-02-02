package university.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.entity.StudentGroup;
import university.repository.StudentGroupRepository;

@Service
@RequiredArgsConstructor
public class StudentGroupService {

    private final StudentGroupRepository groupRepository;

    @Transactional(readOnly = true)
    public StudentGroup getOrThrow(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Группа не найдена: " + id));
    }

    @Transactional
    public StudentGroup createGroup(String number) {
        String normalized = number == null ? "" : number.trim();
        if (normalized.isBlank()) {
            throw new IllegalArgumentException("Номер группы обязателен");
        }
        if (groupRepository.existsByNumber(normalized)) {
            throw new IllegalArgumentException("Группа с таким номером уже существует");
        }
        StudentGroup group = new StudentGroup();
        group.setNumber(normalized);
        return groupRepository.save(group);
    }
}
