package br.com.apirest.todolist.tasks;


import br.com.apirest.todolist.utils.Util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private ITasksRepository tasksRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TasksModel task, HttpServletRequest request){
        var userID = request.getAttribute("user_ID"); // "Filter" what's seated
        task.setUserID((UUID) userID);
        var validDate = LocalDateTime.now();
        if(validDate.isAfter(task.getStartAt()) || validDate.isAfter(task.getEndAt())){
            ResponseEntity.status(401).body("Date invalid, is before or length at date actually !");
        }
        //Verify if date of start is after date end
        if(task.getStartAt().isAfter(task.getEndAt())){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date actually is after the of date end !");
        }
        var taskCreated = this.tasksRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskCreated);
    }
    @GetMapping("/")
    public List<TasksModel> list(HttpServletRequest request){
        var user_id = request.getAttribute("user_id");
        var tasks = this.tasksRepository.findByuserID((UUID) user_id);
        return tasks;
    }
    @PutMapping("/{id_task}")
    public ResponseEntity update(@RequestBody TasksModel tasksModel, @PathVariable UUID id_task, HttpServletRequest request){
        var tasks = this.tasksRepository.findById(id_task).orElse(null);

        if(tasks == null) return ResponseEntity.status(401).body("Task no exists !");
        var userCorrect = request.getAttribute("user_id");
        if(!userCorrect.equals(tasks.getUserID())) return ResponseEntity.status(401).body("You don't authorize to update this task");

        // Verify if properties (attributes) be null
        Util.copyNonNUllPropeties(tasksModel, tasks);

        var tasksUpdate = this.tasksRepository.save(tasks);
        return ResponseEntity.status(HttpStatus.OK).body(tasksUpdate);
    }
}
