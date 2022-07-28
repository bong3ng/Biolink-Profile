package bio.link.service;

import bio.link.model.dto.AdminDto;
import bio.link.model.dto.DataDto;
import bio.link.security.payload.Status;

public interface AdminService {
	DataDto<AdminDto> getAllUser();
	
	Status updateUser(AdminDto admin);
	
	Status deleteUser(Long id);
	Status activeUser(Long id);
}
