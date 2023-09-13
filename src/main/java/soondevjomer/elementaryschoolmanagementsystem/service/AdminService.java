package soondevjomer.elementaryschoolmanagementsystem.service;

import org.springframework.data.domain.Page;
import soondevjomer.elementaryschoolmanagementsystem.dto.AdminDto;

public interface AdminService {

    AdminDto addAdmin(AdminDto adminDto);

    AdminDto getAdmin(Integer adminId);

    Page<AdminDto> getAdmins(Integer page, Integer size, String sortField, String sortOrder);

    AdminDto updateAdmin(Integer adminId, AdminDto adminDto);

    String deleteAdmin(Integer adminId);
}
