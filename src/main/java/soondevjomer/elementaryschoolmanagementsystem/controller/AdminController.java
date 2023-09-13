package soondevjomer.elementaryschoolmanagementsystem.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soondevjomer.elementaryschoolmanagementsystem.dto.AdminDto;
import soondevjomer.elementaryschoolmanagementsystem.service.AdminService;

@RestController
@RequestMapping("api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;
    
    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<AdminDto> addAdmin(@RequestBody AdminDto adminDto) {
        
        return new ResponseEntity<>(adminService.addAdmin(adminDto), HttpStatus.CREATED);
    }
    
    @GetMapping("/{adminId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable Integer adminId) {
        
        return ResponseEntity.ok(adminService.getAdmin(adminId));
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Page<AdminDto>> getAdmins(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {

        return ResponseEntity.ok(adminService.getAdmins(page, size, sortField, sortOrder));
    }

    @PutMapping("/{adminId}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<AdminDto> updateAdmin(
        @PathVariable Integer adminId,
        @RequestBody AdminDto adminDto) {

        return ResponseEntity.ok(adminService.updateAdmin(adminId, adminDto));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer adminId) {

        return ResponseEntity.ok(adminService.deleteAdmin(adminId));
    }
}
