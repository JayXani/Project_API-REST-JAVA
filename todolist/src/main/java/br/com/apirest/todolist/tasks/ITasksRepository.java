package br.com.apirest.todolist.tasks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
public interface ITasksRepository extends JpaRepository<TasksModel, UUID> {
    List<TasksModel> findByuserID(UUID userID);
}
