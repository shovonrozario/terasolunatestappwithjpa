package com.sample.app.todo;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import com.sample.app.todo.TodoForm.TodoCreate;
import com.sample.app.todo.TodoForm.TodoDelete;
import com.sample.app.todo.TodoForm.TodoFinish;
import com.sample.domain.model.Todo;
import com.sample.domain.service.todo.TodoService;

@Controller
@RequestMapping("todo")
public class TodoController {
	@Inject
	TodoService todoService;

	@Inject
	Mapper beanMapper;

	@ModelAttribute
	public TodoForm setUpForm() {
		TodoForm form = new TodoForm();
		return form;
	}

	@RequestMapping(value = "list")
	public String list(Model model) {
		Collection<Todo> todos = todoService.findAll();
		model.addAttribute("todos", todos);
		return "todo/list";
	}

	@RequestMapping(value = "list2")
	public String list(@PageableDefault(page = 0, size = 5, direction = Direction.DESC,	sort = "todoTitle") Pageable pageable, Model model) {
		Page<Todo> page = todoService.findAll(pageable);
		model.addAttribute("page", page);
		return "todo/listpageable";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Validated({ Default.class, TodoCreate.class }) TodoForm todoForm, BindingResult bindingResult,
			Model model, RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			return list(model);
		}

		Todo todo = beanMapper.map(todoForm, Todo.class);

		try {
			todoService.create(todo);
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		attributes.addFlashAttribute(ResultMessages.success().add(ResultMessage.fromText("Created successfully!")));
		return "redirect:/todo/list";
	}

	@RequestMapping(value = "finish", method = RequestMethod.POST)
	public String finish(@Validated({ Default.class, TodoFinish.class }) TodoForm form, BindingResult bindingResult,
			Model model, RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.finish(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		attributes.addFlashAttribute(ResultMessages.success().add(ResultMessage.fromText("Finished successfully!")));
		return "redirect:/todo/list";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST) // (1)
	public String delete(@Validated({ Default.class, TodoDelete.class }) TodoForm form, BindingResult bindingResult,
			Model model, RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			return list(model);
		}

		try {
			todoService.delete(form.getTodoId());
		} catch (BusinessException e) {
			model.addAttribute(e.getResultMessages());
			return list(model);
		}

		attributes.addFlashAttribute(ResultMessages.success().add(ResultMessage.fromText("Deleted successfully!")));
		return "redirect:/todo/list";
	}

}