package bio.link.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bio.link.model.dto.AdminDto;
import bio.link.model.dto.DataDto;
import bio.link.security.payload.Status;
import bio.link.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminManageController {
	@Autowired
	AdminService adminService;
	
	@GetMapping
	public DataDto<AdminDto> getAll() {
		return adminService.getAllUser();
	}
	
	@PutMapping
	public Status update(@RequestBody AdminDto admin) {
		return adminService.updateUser(admin);
	}
	@DeleteMapping("/{id}")
	public Status delete(@PathVariable("id") Long id) {
		return adminService.deleteUser(id);
	}
	@PostMapping("/{id}")
	public Status active(@PathVariable("id") Long id) {
		return adminService.activeUser(id);
	}
	
}
