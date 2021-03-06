package br.com.fiap.EpicTask.controller;

import br.com.fiap.EpicTask.model.Task;
import br.com.fiap.EpicTask.model.Task;
import br.com.fiap.EpicTask.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private MessageSource messageSource;

	@GetMapping()
	public ModelAndView task() {
		List<Task> tasks = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("tasks");
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}

	@RequestMapping("new")
	public String formNew(Task task){
		return "task_new";
	}

	@PostMapping()
	public String save(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) return "task_new";
		repository.save(task);
		redirect.addFlashAttribute("message", getMessage("message.newtask.success"));
		return "redirect:/task";
	}

	@GetMapping("/delete/{id}")
	public String deleteTask(@PathVariable Long id, RedirectAttributes redirect) {
		repository.deleteById(id);
		redirect.addFlashAttribute("message", getMessage("message.deletetask.success"));
		return "redirect:/task";
	}

	@GetMapping("/{id}")
	public ModelAndView editTaskForm(@PathVariable Long id) {
		Optional<Task> task = repository.findById(id);
		ModelAndView modelAndView = new ModelAndView("task_edit");
		modelAndView.addObject("task", task);
		return modelAndView;
	}

	@PostMapping("/update")
	public String updateTask(@Valid Task task, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) return "task_edit";
		repository.save(task);
		redirect.addFlashAttribute("message", getMessage("message.edittask.success"));
		return "redirect:/task";
	}

	private String getMessage(String code) {
		return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}



}
