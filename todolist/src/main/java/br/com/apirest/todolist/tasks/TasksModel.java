package br.com.apirest.todolist.tasks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TasksModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID tasks_ID;
    private UUID userID;

    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    public void setTitle(String title) throws Exception{
        if(title.length() > 50){
            throw new Exception("Title cannot have more the 50 characters");
        }
    }
}
