package br.com.fiap.EpicTask.repository;

import br.com.fiap.EpicTask.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
