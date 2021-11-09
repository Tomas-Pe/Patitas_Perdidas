package com.patitas_perdidas.app.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.patitas_perdidas.app.entidades.Persona;
import com.patitas_perdidas.app.excepciones.PersonaExcepcion;
import com.patitas_perdidas.app.servicios.PersonaServicio;

@Controller
@RequestMapping("/persona")
public class PersonaControlador {

	@Autowired
	private PersonaServicio personaServicio;

	@GetMapping("/lista")
	public String lista(ModelMap modelo) {

		List<Persona> todos = personaServicio.listarTodos();
		modelo.addAttribute("personas", todos);

		return "list-persona";
	}

	@GetMapping("/registro")
	public String formulario() {
		return "registro.html";
	}

	@PostMapping("/registro")
	public String guardar(RedirectAttributes redirAttrs, @RequestParam String nombre, @RequestParam Long telefono,
			@RequestParam String mail, @RequestParam String clave) {

		try {
			personaServicio.guardar(nombre, telefono, mail, clave);
		} catch (PersonaExcepcion e) {
			redirAttrs.addFlashAttribute("error", e.getMessage());
			return ("redirect:./registro");
		}
		redirAttrs.addFlashAttribute("exito", "Se ha registrado sastifactoriamente. Ahora puede ir a iniciar sesion.");
		return ("redirect:./registro");

	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/baja/id")
	public String baja(@PathVariable String id) {
		try {
			personaServicio.baja(id);
			return "redirect:/persona/lista";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@GetMapping("/alta/id")
	public String alta(@PathVariable String id) {
		try {
			personaServicio.alta(id);
			return "redirect:/persona/lista";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/perfil/{id}")
	public String perfilUsuario(HttpSession session, ModelMap modelo, @PathVariable String id) throws PersonaExcepcion {
		Persona person = (Persona) session.getAttribute("clientesession");
		if (person == null || !person.getId().equals(id)) {
			return "redirect:/inicio";
		}
		Persona usuario = personaServicio.buscaPorId(id);
		modelo.addAttribute("usuario", usuario);
		return "perfil.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("/modificar-pass/{id}")
	public String modificarPass(HttpSession session, ModelMap model, @PathVariable String id) throws PersonaExcepcion {
		Persona person = (Persona) session.getAttribute("clientesession");
		if (person == null || !person.getId().equals(id)) {
			return "redirect:/inicio";
		}

		Persona usuario = personaServicio.buscaPorId(id);
		model.addAttribute("usuario", usuario);
		model.put("pass", "pass");
		return "perfil.html";

	}
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/modificar-pass/{id}")
	public String modificarPassPost(HttpSession session, ModelMap model, @PathVariable String id, @RequestParam String clave1, @RequestParam String clave2) throws PersonaExcepcion {
		Persona person = (Persona) session.getAttribute("clientesession");
		if (person == null || !person.getId().equals(id)) {
			return "redirect:/inicio";
		}
		try
		{
			personaServicio.modificarClave(id, clave1, clave2);
			model.put("cambio", "Su contraseña a sido cambiada con exito");
			model.put("pass", "pass");
			Persona usuario = personaServicio.buscaPorId(id);
			model.addAttribute("usuario", usuario);
			model.addAttribute("exito", "La clave a sido modificada exitosamente");
			session.setAttribute("clientesession", usuario);
			return "perfil.html";
		}
		catch(PersonaExcepcion pe)
		{
			model.put("error", pe.getMessage());
			Persona usuario = personaServicio.buscaPorId(id);
			model.addAttribute("usuario", usuario);
			session.setAttribute("clientesession", usuario);
			return "perfil.html";
		}


	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/modificar/{id}")
	public String modificar(HttpSession session, RedirectAttributes redirAttrs, ModelMap model,
			@PathVariable String id, @RequestParam String nombre, @RequestParam Long telefono,
			@RequestParam String mail) {
		try {
			Persona person = (Persona) session.getAttribute("clientesession");
			if (person == null || !person.getId().equals(id)) {
				return "redirect:/inicio";
			}
			personaServicio.modificar(id, nombre, telefono, mail);
			Persona usuario = personaServicio.buscaPorId(id);
			model.addAttribute("usuario", usuario);
			session.setAttribute("clientesession", usuario);
			model.put("exito", "Su perfil ha sido modificado exitosamente");
			redirAttrs.addAttribute("id", id);
			return "perfil.html";
		} catch (Exception e) {
			model.put("error", "Falto ingresar el nombre");
			redirAttrs.addAttribute("id", id);

			return "redirect:/persona/perfil/{id}";
		}
	}
}
