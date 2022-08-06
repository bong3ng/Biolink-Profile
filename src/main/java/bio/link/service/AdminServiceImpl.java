package bio.link.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.link.model.dto.AdminDto;
import bio.link.model.dto.DataDto;
import bio.link.model.entity.ProfileEntity;
import bio.link.model.entity.UserEntity;
import bio.link.repository.ProfileRepository;
import bio.link.repository.UserRepository;
import bio.link.security.payload.Status;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
    private ModelMapper mapper;
	@Override
	public DataDto<AdminDto> getAllUser() {
		List<UserEntity> listUser = userRepository.findAll();
		List<AdminDto> list = new ArrayList<AdminDto>();
		
		for(UserEntity user : listUser) {
			ProfileEntity profile = profileRepository.findByUserId(user.getId());
			AdminDto adminDto = mapper.map(user, AdminDto.class);
			if(profile != null) {
				adminDto.setName(profile.getName());
				adminDto.setBio(profile.getBio());
			}
			list.add(adminDto);
			
		}
		
		
		return new DataDto(true,"Thanh Cong", list);
	}

	@Override
	public Status updateUser(AdminDto admin) {
		Long idAdmin = admin.getId();
		Optional<UserEntity> opt = userRepository.findById(idAdmin);
		ProfileEntity profile = profileRepository.findByUserId(idAdmin);
		if(opt.isPresent()) {
			UserEntity user = opt.get();
			user.setEmail(admin.getEmail());
			user.setEnabled(admin.getEnabled());
			user.setRole(admin.getRole());
			userRepository.save(user);
		}
		if(profile!= null) {
			profile.setBio(admin.getBio());
			profile.setName(admin.getName());
			profileRepository.save(profile);
		}
		
		return new Status(true,"Cap nhat thanh cong");
	}

	@Override
	public Status deleteUser(Long id) {
		Optional<UserEntity> opt = userRepository.findById(id);
		if(opt.isPresent()) {
			UserEntity user = opt.get();
			user.setStatus(false);
			userRepository.save(user);
		}
		return new Status(true,"Cap nhat thanh cong");
	}

	@Override
	public Status activeUser(Long id) {
		Optional<UserEntity> opt = userRepository.findById(id);
		if(opt.isPresent()) {
			UserEntity user = opt.get();
			user.setStatus(true);
			userRepository.save(user);
		}
		return new Status(true,"Cap nhat thanh cong");
	}
	
}
