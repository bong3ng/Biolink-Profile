package bio.link.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
