package com.informationconfig.spring.bootcamp.bootcamp_proyect.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.TaskRequestDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Intern;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.models.Task;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.ListsRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.TaskRepository;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.AcademyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.CompanyTutorRepository;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.repository.InternRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    private ListsRepository listsRepository;

    @Autowired
    private AcademyTutorRepository academyTutorRepository;

    @Autowired
    private CompanyTutorRepository companyTutorRepository;

    @Autowired
    private InternRepository internRepository;

    // Crear tarea
public Task addTask(TaskRequestDTO dto, String tutorId) {
    boolean isAcademyTutor = academyTutorRepository.existsById(tutorId);
    boolean isCompanyTutor = companyTutorRepository.existsById(tutorId);

    if (!isAcademyTutor && !isCompanyTutor) {
        throw new RuntimeException("Solo los tutores pueden crear tareas.");
    }

    // Validar internos
    List<String> internIds = dto.getInternsId();
    List<Intern> interns = new ArrayList<>();
    if (internIds != null && !internIds.isEmpty()) {
        interns = internRepository.findAllById(internIds);
        if (interns.size() != internIds.size()) {
            throw new RuntimeException("Uno o más internos no existen.");
        }
    }

    Task newTask = new Task();
    newTask.setTaskId(dto.getId());
    newTask.setTitle(dto.getTitle());
    newTask.setDescription(dto.getDescription());
    newTask.setDueDate(dto.getDueDate());
    newTask.setStatus("Pending");
    newTask.setAssingTo(dto.getAssignTo());
    newTask.setCreatedByTutorId(tutorId);

    if (dto.getListId() != null) {
        listsRepository.findById(dto.getListId())
            .ifPresent(newTask::setList);
    }

    // Asociar internos
    newTask.setInterns(interns);

    return taskRepository.save(newTask);
}

    // Obtener todas las tareas
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Obtener una tarea por ID
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    // Actualizar una tarea
    public Task updateTask(String id, Task updatedTask) {
        if (taskRepository.existsById(id)) {
            updatedTask.setTaskId(id);
            return taskRepository.save(updatedTask);
        }
        return null;
    }

    // Eliminar una tarea
    public boolean deleteTask(String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

   
