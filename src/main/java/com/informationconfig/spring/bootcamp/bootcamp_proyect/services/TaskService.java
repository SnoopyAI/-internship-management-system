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

    // Validar que la tarea no exista (solo crear, no actualizar)
    if (dto.getId() != null && taskRepository.existsById(dto.getId())) {
        throw new RuntimeException("Ya existe una tarea con el ID: " + dto.getId());
    }

    // Validar internos - AGREGANDO LOGS PARA DEBUG
    List<String> internIds = dto.getInternsId();
    System.out.println("DEBUG: Lista de internIds recibida: " + internIds);
    List<Intern> interns = new ArrayList<>();
    if (internIds != null && !internIds.isEmpty()) {
        System.out.println("DEBUG: Buscando " + internIds.size() + " internos...");
        interns = internRepository.findAllById(internIds);
        System.out.println("DEBUG: Encontrados " + interns.size() + " internos");
        if (interns.size() != internIds.size()) {
            throw new RuntimeException("Uno o más internos no existen.");
        }
    } else {
        System.out.println("DEBUG: No se enviaron internos para asociar");
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

    // Asociar internos - AGREGANDO LOG PARA DEBUG
    System.out.println("DEBUG: Asociando " + interns.size() + " internos a la tarea");
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

   
