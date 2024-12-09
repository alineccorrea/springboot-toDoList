package br.com.alineccorrea.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        //setando o idUser capturado e adicionado na request no momento da validação do filter
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        //Validando datas e horas
        var currentDate = LocalDateTime.now();
        
        //Se a data atual é maior que a data de inicio da task OU
        //Se a data atual é maior que a data final da task
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A data de inicio/data de término da task deve ser maior do que a data atual.");
            //Exemplo NÃO PERMITE
            /* currentDate 10/12/2024 startAt 10/10/2024 OU
                currentDate 10/12/2024 endAt 10/10/2024 */
        }

        //Validando se a data de inicio da task é maior que a data de fim da task
        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A data de inicio deve ser menor que a data de término da task.");
            /* Exemplo NÃO PERMITE - startAt 10/10/2024 7h endAt 10/05/2024 10h */
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser); 
        return tasks;
    }
}
